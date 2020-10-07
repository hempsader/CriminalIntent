package com.example.criminalintent.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "crimes_table")
data class Crime(var title: String = "", @PrimaryKey val id: UUID = UUID.randomUUID(), val date: Date = Date(), val solved: Boolean = false)