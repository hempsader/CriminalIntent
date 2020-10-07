package com.example.criminalintent.controller

import androidx.lifecycle.ViewModel
import com.example.criminalintent.data.Crime

class CrimeViewModel : ViewModel(){
    val listOfCrimes = mutableListOf<Crime>()

    init {
        (1..100).forEach {
            if(it % 2 == 0) listOfCrimes += Crime(false,"test$it") else listOfCrimes += Crime(true,"test$it")
        }
    }
}