package com.example.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.controller.CrimeViewModel
import com.example.criminalintent.data.Crime
import com.example.criminalintent.view.CrimeViewController
import java.util.*
import javax.security.auth.callback.Callback

class CriminalListFragment : Fragment(), CrimeViewController.Callbacks {
    interface CrimeCallback{
        fun crime(uuid: UUID)
    }
    var crimeCallback: CrimeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        crimeCallback = context as CrimeCallback
    }

    override fun onDetach() {
        super.onDetach()
        crimeCallback = null
    }

    private val listViewModel : CrimeViewModel by lazy {
        ViewModelProvider(this).get(CrimeViewModel::class.java)
    }
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list,container,false)
        recyclerView = view.findViewById(R.id.recycler_crimes)
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        listViewModel.listOfCrimes.observe(viewLifecycleOwner, {
                recyclerView?.adapter = CrimeViewController(it,this)

            })
        return view
    }
    companion object{
        fun newInstance() = CriminalListFragment()
    }

    override fun onCrimeClick(uuid: UUID) {
        crimeCallback?.crime(uuid)
    }
}