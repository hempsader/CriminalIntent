package com.example.criminalintent

import android.app.Application

class CriminalApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}