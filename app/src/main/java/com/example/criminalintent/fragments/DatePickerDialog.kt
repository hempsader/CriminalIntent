package com.example.criminalintent.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

class DatePickerDialog : DialogFragment() {
    val dialogCallback : DialogCallback ? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val pickerCall = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                targetFragment.let {
                    (it as DialogCallback)
                }
            }

        }
        val datePickerDialog = DatePickerDialog(requireContext(),pickerCall,year,month,day)

        return datePickerDialog
    }

    companion object{
        fun newInstance(date: Date):com.example.criminalintent.fragments.DatePickerDialog{
            val args = Bundle().apply {
                putSerializable(ARG_DATE,date)
            }
            return DatePickerDialog().apply {
                arguments = args
            }
        }
    }

    interface DialogCallback{
        fun onDateSet(date: Date)
    }

}