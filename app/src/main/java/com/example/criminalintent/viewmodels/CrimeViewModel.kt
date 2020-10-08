package com.example.criminalintent.viewmodels

import androidx.lifecycle.ViewModel
import com.example.criminalintent.repository.CrimeRepository
import com.example.criminalintent.data.Crime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CrimeViewModel : ViewModel(){
    val listOfCrimes = CrimeRepository.get().getCrimes()

    init {
        GlobalScope.launch() {
            CrimeRepository.get().insertCrime(Crime("Test xxx"))
        }
    }
}