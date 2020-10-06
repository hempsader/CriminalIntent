package com.example.criminalintent.data

import java.util.*

data class Crime(val requiresPolice: Int = 0,var title: String = "", val id: UUID = UUID.randomUUID(), val date: Date = Date(), val solved: Boolean = false)