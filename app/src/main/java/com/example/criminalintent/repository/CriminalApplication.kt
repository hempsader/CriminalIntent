package com.example.criminalintent.repository

import android.app.Application
import com.example.criminalintent.repository.CrimeRepository

class CriminalApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}