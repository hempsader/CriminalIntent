package com.example.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.criminalintent.data.Crime
import java.util.*

class CrimeFragment : Fragment(){
    private lateinit var crime: Crime
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDetail: EditText
    private lateinit var checkboxSolved: CheckBox
    private lateinit var titleWatcher: TextWatcher
    private lateinit var dateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        checkboxSolved.apply {
            setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
                compoundButton.isChecked = crime.solved
            }
        }
    }
}