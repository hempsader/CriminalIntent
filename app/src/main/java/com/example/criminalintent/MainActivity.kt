package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
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
}