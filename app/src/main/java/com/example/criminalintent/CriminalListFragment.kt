package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import com.example.criminalintent.controller.CrimeViewModel
import com.example.criminalintent.data.Crime

class CriminalListFragment : ListFragment() {
    private val listViewModel : CrimeViewModel by lazy {
        ViewModelProvider(this).get(CrimeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,listViewModel.listOfCrimes)
        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    companion object{
        fun newInstance() = CriminalListFragment()
    }

}