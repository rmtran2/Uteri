package com.cs407.uteri.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

@Entity(
    indices = [Index(value = ["date"], unique = true)]
)
data class LogEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: LocalDate,
    val flow: Boolean,
    val moods: List<Mood>?,         // list of moods
    val birthControl: Boolean,
    val medications: List<Int>?     // list of medication IDs
)

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString() // ISO-8601 format: "2025-03-11"
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}

class MoodListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(value: List<Mood>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<Mood> {
        val type = object : TypeToken<List<Mood>>() {}.type
        return gson.fromJson(value, type)
    }
}

class MedicationListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<Int>?): String {
        return gson.toJson(list ?: emptyList<Int>())
    }

    @TypeConverter
    fun toList(value: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }
}
