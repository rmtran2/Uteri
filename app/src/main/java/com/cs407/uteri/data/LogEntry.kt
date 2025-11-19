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
    val flow: Boolean = false,
    val moods: List<Mood>?,
    val birthControl: Boolean = false,
    val medications: List<Int>?
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
    fun fromList(value: List<Mood>?): String? {
        return if (value == null) null else gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<Mood>? {
        if (value == null) return null
        val type = object : TypeToken<List<Mood>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}

class MedicationListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        if (value == null) return null
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}
