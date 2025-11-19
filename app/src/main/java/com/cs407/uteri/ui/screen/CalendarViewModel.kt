package com.cs407.uteri.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cs407.uteri.data.Database
import com.cs407.uteri.data.LogEntry
import com.cs407.uteri.data.Mood
import com.cs407.uteri.data.UserMedication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val database: Database
) : ViewModel() {
    private val logEntryDao = database.logEntryDao()
    private val medicationDao = database.userMedicationDao()

    private val _medications = MutableStateFlow<List<UserMedication>>(emptyList())
    val medications: StateFlow<List<UserMedication>> = _medications.asStateFlow()

    val allEntries: StateFlow<List<LogEntry>> = logEntryDao.getAllEntries()
        .stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadMedications()
    }

    private fun loadMedications() {
        viewModelScope.launch {
            medicationDao.getAllMedications().collect { meds ->
                _medications.value = meds
            }
        }
    }

    suspend fun saveLogEntry(
        date: LocalDate,
        flow: Boolean,
        moods: List<Mood>,
        birthControl: Boolean,
        medicationIds: List<Int>
    ) {
        viewModelScope.launch {
            val existingEntry = logEntryDao.getEntryByDate(date)
            val entry = LogEntry(
                id = existingEntry?.id ?: 0,
                date = date,
                flow = flow,
                moods = moods.ifEmpty { null },
                birthControl = birthControl,
                medications = medicationIds.ifEmpty { null }
            )
            logEntryDao.insertEntry(entry)
        }
    }

    suspend fun addMedication(name: String, type: String): Long {
        val medication = UserMedication(
            name = name,
            type = type
        )
        return medicationDao.insertMedication(medication)
    }

    suspend fun getEntryForDate(date: LocalDate): LogEntry? {
        return logEntryDao.getEntryByDate(date)
    }
}

class CalendarViewModelFactory(
    private val database: Database
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(database) as T
    }
}

