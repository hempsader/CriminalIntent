package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.controller.CrimeViewModel
import com.example.criminalintent.data.Crime
import com.example.criminalintent.view.CrimeViewController

class CriminalListFragment : Fragment() {
    private val listViewModel : CrimeViewModel by lazy {
        ViewModelProvider(this).get(CrimeViewModel::class.java)
    }
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list,container,false)
        recyclerView = view.findViewById(R.id.recycler_crimes)
        recyclerView?.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = CrimeViewController(listViewModel.listOfCrimes)
        }
        return view
    }
    companion object{
        fun newInstance() = CriminalListFragment()
    }

}