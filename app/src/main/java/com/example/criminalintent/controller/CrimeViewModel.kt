package com.example.criminalintent.controller

import androidx.lifecycle.ViewModel
import com.example.criminalintent.data.Crime

class CrimeViewModel : ViewModel(){
    val listOfCrimes = mutableListOf<Crime>()

    init {
        (1..100).forEach {
            listOfCrimes += Crime("test$it")
        }
    }
}