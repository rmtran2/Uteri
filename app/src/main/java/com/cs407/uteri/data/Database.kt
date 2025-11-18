package com.cs407.uteri.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [LogEntry::class, UserMedication::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LocalDateConverter::class,
    MoodListConverter::class,
    MedicationListConverter::class
)

abstract class Database : RoomDatabase() {
    abstract fun logEntryDao(): LogEntryDao
    abstract fun userMedicationDao(): UserMedicationDao
}
