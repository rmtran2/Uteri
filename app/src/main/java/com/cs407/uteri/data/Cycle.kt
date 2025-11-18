package com.cs407.uteri.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity()
data class Cycle (
    @PrimaryKey(autoGenerate = true) val cycleId: Int = 0,
    val startDate: LocalDate
)