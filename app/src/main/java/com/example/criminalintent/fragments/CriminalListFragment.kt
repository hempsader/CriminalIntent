package com.example.criminalintent.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.viewmodels.CrimeViewModel
import com.example.criminalintent.view.CrimeViewController
import java.util.*

class CriminalListFragment : Fragment(), CrimeViewController.Callbacks {
    private lateinit var firstCrimeButton: Button
    interface CrimeCallback{
        fun crime(uuid: UUID)
    }
    var crimeCallback: CrimeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        crimeCallback = context as CrimeCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        val firstCrimeButton = view.findViewById<Button>(R.id.button_add_first_crime)
        recyclerView = view.findViewById(R.id.recycler_crimes)
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        listViewModel.listOfCrimes.observe(viewLifecycleOwner, {
                recyclerView?.adapter = CrimeViewController(it,this)
                if(it.isEmpty()) firstCrimeButton.visibility = View.VISIBLE else
                    firstCrimeButton.visibility = View.INVISIBLE
            })

        firstCrimeButton.setOnClickListener {
            val crime= Crime()
            listViewModel.addCrime(crime)
            crimeCallback?.crime(crime.id)
        }

        return view
    }
    override fun onCrimeClick(uuid: UUID) {
        crimeCallback?.crime(uuid)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_crime -> {
                val crime= Crime()
                listViewModel.addCrime(crime)
                crimeCallback?.crime(crime.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}