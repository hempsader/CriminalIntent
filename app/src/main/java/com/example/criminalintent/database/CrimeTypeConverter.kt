package com.example.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millisecond: Long?) :Date? = millisecond?.let { Date(it) }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid.toString()

    @TypeConverter
    fun toUUID(uuidString: String?): UUID? = UUID.fromString(uuidString)
}