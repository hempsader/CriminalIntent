package com.example.criminalintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.criminalintent.repository.CrimeRepository
import com.example.criminalintent.data.Crime
import java.util.*

class CrimeDetailViewModel: ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIDLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<Crime> =
        Transformations.switchMap(crimeIDLiveData){
            crimeRepository.getCrime(it)
    }

    fun loadCrime(crimeID: UUID) {
        crimeIDLiveData.value = crimeID
    }

    fun saveCrime(crime: Crime){
        crimeRepository.updateCrime(crime)
    }
}