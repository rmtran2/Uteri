package com.cs407.uteri.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface LogEntryDao {
    @Query("SELECT * FROM LogEntry ORDER BY date DESC")
    fun getAllEntries(): Flow<List<LogEntry>>

    @Query("SELECT * FROM LogEntry WHERE id = :id")
    suspend fun getEntryById(id: Int): LogEntry?

    @Query("SELECT * FROM LogEntry WHERE date = :date")
    suspend fun getEntryByDate(date: LocalDate): LogEntry?

    @Query("SELECT * FROM LogEntry WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getEntriesByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<LogEntry>>

    @Query("SELECT * FROM LogEntry WHERE flow = 1 ORDER BY date DESC")
    fun getEntriesWithFlow(): Flow<List<LogEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: LogEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntries(entries: List<LogEntry>)

    @Update
    suspend fun updateEntry(entry: LogEntry)

    @Delete
    suspend fun deleteEntry(entry: LogEntry)

    @Query("DELETE FROM LogEntry WHERE id = :id")
    suspend fun deleteEntryById(id: Int)

    @Query("DELETE FROM LogEntry WHERE date = :date")
    suspend fun deleteEntryByDate(date: LocalDate)

    @Query("DELETE FROM LogEntry")
    suspend fun deleteAllEntries()
}

