package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val fragmentSupport = supportFragmentManager.findFragmentById(R.id.fragment_container)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CrimeFragment()).addToBackStack(null).commit()
    }
}