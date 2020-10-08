package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.criminalintent.fragments.CrimeFragment
import com.example.criminalintent.fragments.CriminalListFragment
import java.util.*

class MainActivity : AppCompatActivity(), CriminalListFragment.CrimeCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentSupport = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(fragmentSupport == null)
        {
            val fragment = CriminalListFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit()
        }

    }

    override fun crime(uuid: UUID) {
        val bundle: Bundle = Bundle().apply {
            putString(CRIME_UUID,uuid.toString())
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CrimeFragment.getInstance(uuid.toString())).addToBackStack(null).commit()
    }

    companion object{
        const val CRIME_UUID = "crime_uuid"
    }
}