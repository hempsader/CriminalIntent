package com.example.criminalintent

import android.content.Context
import androidx.room.Room
import com.example.criminalintent.data.Crime
import com.example.criminalintent.database.CrimeDao
import com.example.criminalintent.database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.*

class CrimeRepository private constructor(context: Context){

    private val database = Room.databaseBuilder(context.applicationContext,CrimeDatabase::class.java,"crimes_table")
        .build()

    private val crimeDao = database.crimeDao()

    fun getCrimes() = crimeDao.getCrimes()
    fun getCrime(uuid: UUID) = crimeDao.getCrime(uuid)
    fun insertCrime(crime: Crime) {crimeDao.insertCrime(crime)}

    companion object{
        private var INSTANCE: CrimeRepository? = null
        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("dasd")
        }
    }
}
