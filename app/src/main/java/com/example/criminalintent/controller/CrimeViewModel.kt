package com.example.criminalintent.controller

import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.criminalintent.CrimeRepository
import com.example.criminalintent.data.Crime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CrimeViewModel : ViewModel(){
    val listOfCrimes = CrimeRepository.get().getCrimes()
    init {
        GlobalScope.launch() {
            CrimeRepository.get().insertCrime(Crime("Test"))
        }
    }
}