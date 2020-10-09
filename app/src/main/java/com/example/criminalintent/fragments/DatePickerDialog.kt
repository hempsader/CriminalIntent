package com.example.criminalintent.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
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
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var getHour = 0
        var getMinute = 0
        var getYear = 0
        var getMonth = 0
        var getDay = 0

        val pickerDate = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            getYear = p1
            getMonth = p2
            getDay = p3
            val pickTime = TimePickerDialog.OnTimeSetListener{ timePicker: TimePicker, i: Int, i1: Int ->
                getHour = i
                getMinute = i1
                targetFragment.let {
                    val calendar = GregorianCalendar(p1,p2,p3,getHour,getMinute)
                    (it as DialogCallback).onDateSet(calendar.time)
                }
            }
            TimePickerDialog(requireContext(),pickTime,hour,minute,true).show()


        }
        val datePickerDialog = DatePickerDialog(requireContext(),pickerDate,year,month,day)
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