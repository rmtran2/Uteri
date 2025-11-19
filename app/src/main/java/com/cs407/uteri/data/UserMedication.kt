package com.cs407.uteri.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserMedication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String
)