package com.example.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import com.example.criminalintent.data.Crime
import java.util.*


@Dao
interface CrimeDao{

    @Query("select * from crimes_table")
    fun getCrimes(): List<Crime>

    @Query("select * from crimes_table where id = (:uuid)")
    fun getCrime(uuid: UUID) : Crime
}