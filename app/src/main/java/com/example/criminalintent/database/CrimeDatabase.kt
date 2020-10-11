package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.criminalintent.data.Crime

@Database(entities = [Crime::class],version = 11,exportSchema = false)
@TypeConverters(CrimeTypeConverter::class)
abstract  class CrimeDatabase : RoomDatabase(){
    abstract fun crimeDao(): CrimeDao
}
