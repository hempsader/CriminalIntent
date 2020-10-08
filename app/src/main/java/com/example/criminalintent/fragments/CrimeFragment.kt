package com.example.criminalintent.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.criminalintent.MainActivity
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.repository.CrimeRepository
import com.example.criminalintent.viewmodels.CrimeDetailViewModel
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.*

class CrimeFragment : Fragment(){
    private lateinit var crime: Crime
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDetail: EditText
    private lateinit var checkboxSolved: CheckBox
    private lateinit var titleWatcher: TextWatcher
    private lateinit var dateButton: Button
    private lateinit var titleText: TextView
    private var crimeUUID: String? = null
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
        titleWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
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
        editTextTitle.addTextChangedListener(titleWatcher)
        titleText = view.findViewById(R.id.textView_title)
        crimeDetailViewModel.crimeLiveData.observe(viewLifecycleOwner, {
            crimeUI(it)
        })

        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        checkboxSolved.apply {
               isChecked = crime.solved
        }
    }
    private fun crimeUI(crime: Crime){
        titleText.text = crime.title
        button_add.text = crime.date.toString()
        checkboxSolved.isChecked = crime.solved
    }





}