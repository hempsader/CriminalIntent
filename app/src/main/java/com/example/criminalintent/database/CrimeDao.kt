package com.example.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.criminalintent.data.Crime
import java.util.*


@Dao
interface CrimeDao{

    @Query("select * from crimes_table")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("select * from crimes_table where id = (:uuid)")
    fun getCrime(uuid: UUID) : LiveData<Crime>

    @Insert
    fun insertCrime(crime: Crime)
}