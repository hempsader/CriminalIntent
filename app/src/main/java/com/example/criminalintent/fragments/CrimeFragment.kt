package com.example.criminalintent.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.criminalintent.MainActivity
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.getScaledBitmap
import com.example.criminalintent.viewmodels.CrimeDetailViewModel
import java.io.File
import java.net.URI
import java.util.*

private const val REQUEST_DATE = 0
private const val DATE_FORMAT = "EEE, MMM, dd"
private const val REQUEST_CONTACT = 1
private const val REQUEST_CALL = 2
private const val REQUEST_PHOTO = 3

class CrimeFragment : Fragment(), DatePickerDialog.DialogCallback{
    private lateinit var crime: Crime
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDetail: EditText
    private lateinit var checkboxSolved: CheckBox
    private lateinit var titleWatcher: TextWatcher
    private lateinit var dateButton: Button
    private lateinit var titleText: TextView
    private var crimeUUID: String? = null
    private lateinit var sendReport: Button
    private lateinit var chooseSuspect: Button
    private lateinit var callSuspect: Button
    private lateinit var imageCrime: ImageView
    private lateinit var takePicture: ImageButton
    private lateinit var photoFile: File
    private lateinit var photoURI: Uri
    private val intentContact = Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI)
    private val intentCall :Intent = Intent(Intent.ACTION_DIAL)

        private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
            ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
        }

        companion object{
        fun getInstance(crimeUUID: String) =
            CrimeFragment().apply {
                arguments = Bundle().apply {
                    putString(MainActivity.CRIME_UUID,crimeUUID)
                }
            }
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                crimeUUID = it.getString(MainActivity.CRIME_UUID)
            }
            crime = Crime()
            crimeDetailViewModel.loadCrime(UUID.fromString(crimeUUID))
        }


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_crime,container,false)

            editTextTitle = view.findViewById(R.id.editText_title)
            editTextDetail = view.findViewById(R.id.editText_detail)
            checkboxSolved = view.findViewById(R.id.checkBox_solved)
            dateButton = view.findViewById(R.id.button_add)
            sendReport = view.findViewById(R.id.button_send_report)
            titleText = view.findViewById(R.id.textView_title)
            callSuspect = view.findViewById(R.id.button_call_suspect)
            chooseSuspect = view.findViewById(R.id.button_choose_suspects)
            imageCrime = view.findViewById(R.id.photo_imageview)
            takePicture = view.findViewById(R.id.take_photo)
            crimeDetailViewModel.crimeLiveData.observe(viewLifecycleOwner, {
                crimeUI(it)
                crime = it
                editTextTitle.addTextChangedListener(addWatcher(crime))
            })
            dateButton.apply {
                text = crime.date.toString()
                setOnClickListener {
                    DatePickerDialog.newInstance(crime.date).apply {
                        setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                        show(this@CrimeFragment.requireFragmentManager(),"date_dialog")
                    }
                }
            }
            return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        crimeDetailViewModel.crimeLiveData.observe(viewLifecycleOwner, {
            crime?.let {
                this.crime = crime
                photoFile = crimeDetailViewModel.getPhotoFile(crime)
                photoURI = FileProvider.getUriForFile(requireActivity(),"com.example.criminalintent.fileprovider",
                    photoFile
                )
                crimeUI(crime)
            }
        })
    }

        override fun onStart() {
            super.onStart()
            checkboxSolved.apply {
                isChecked = crime.solved
            }
            sendReport.setOnClickListener{
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,getCrimeReport())
                    putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_subject))
                }.also {
                    val chooser = Intent.createChooser(it,getString(R.string.send_report))
                    startActivity(chooser)
                }
            }
            chooseSuspect.setOnClickListener {
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        startActivityForResult(intentContact, REQUEST_CONTACT)
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    }
                    else -> {
                        requireActivity().requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),1)
                    }
                }
            }
            callSuspect.apply {
                text = crime.phone
                setOnClickListener {
                    when {
                        ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            intentCall.apply {
                                data = Uri.parse("tel:${crime.phone}")
                            }
                            startActivity(intentCall)
                        }
                        shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                        }
                        else -> {
                            requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),1)
                        }
                    }
                }
            }
            takePicture.apply {
                val packageManager = requireActivity().packageManager
                val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
             //   val resolveActivity = packageManager.resolveActivity(captureImage,PackageManager.MATCH_DEFAULT_ONLY)
             //   if(resolveActivity == null){
             //       isEnabled = false
             //   }
                setOnClickListener {
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    val cameraAcrivities = packageManager.queryIntentActivities(captureImage,PackageManager.MATCH_DEFAULT_ONLY)
                    for(cameraActivity in cameraAcrivities){
                        requireActivity().grantUriPermission(cameraActivity.activityInfo.packageName,
                        photoURI,Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }
                    startActivityForResult(captureImage,REQUEST_PHOTO)

                }
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            when (requestCode) {
                1 -> {
                    if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        startActivityForResult(intentContact, REQUEST_CONTACT)
                    } else {
                    }
                    return
                }
            }
        }
        private fun crimeUI(crime: Crime){
            titleText.text = crime.title
            dateButton.text = crime.date.toString()
            checkboxSolved.isChecked = crime.solved
            if(crime.suspect.isNotEmpty()){
                chooseSuspect.text = crime.suspect
            }
            if(crime.phone.isNotEmpty()){
                callSuspect.text = crime.phone
            }
            updatePhotoView()
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            when{
                resultCode != Activity.RESULT_OK -> return
                requestCode == REQUEST_CONTACT && data != null ->{
                    val contanctUri: Uri? = data.data
                    val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID)
                    val cursor = contanctUri?.let {
                        requireActivity().contentResolver
                            .query(it,queryFields,null,null,null)
                    }
                    cursor?.use {
                        if(it.count ==  0) return
                        it.moveToFirst()
                        val suspect = it.getString(0)
                        val id = it.getString(1)
                        val cursor = requireActivity().contentResolver
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" =?", arrayOf(id),null)
                        cursor?.moveToNext()
                        val number = cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        cursor?.close()
                        crime.suspect = suspect
                        crime.phone = number.toString()
                        crimeDetailViewModel.saveCrime(crime)
                        chooseSuspect.text = suspect
                        callSuspect.text = number.toString()
                        it.close()
                    }
                }
            }
        }

        fun addWatcher(crime: Crime): TextWatcher{
            return object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun afterTextChanged(p0: Editable?) {
                    crime.title = p0.toString()
                }
            }
        }

        override fun onStop() {
            super.onStop()
            CrimeDetailViewModel().saveCrime(crime)

        }

        override fun onDateSet(date: Date) {
            crime.date = date
            dateButton.text = date.toString()
        }

        private fun getCrimeReport(): String {
            val solvedCrime = if(crime.solved) getString(R.string.crime_report_solved) else getString(R.string.crime_report_unsolved)
            val dateString = android.text.format.DateFormat.format(DATE_FORMAT,crime.date)
            val suspect = if(crime.suspect.isBlank()) getString(R.string.crime_report_no_suspect) else getString(R.string.crime_report_suspect,crime.suspect)
            return getString(R.string.crime_report, crime.title, dateString, solvedCrime, suspect)
        }
        private fun updatePhotoView(){
            if(photoFile.exists()){
                val bitmap = getScaledBitmap(photoFile.path,requireActivity())
                imageCrime.setImageBitmap(bitmap)
            }else{
                imageCrime.setImageBitmap(null)
            }
        }
    }
