package com.cs407.uteri.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserMedicationDao {
    @Query("SELECT * FROM UserMedication ORDER BY name ASC")
    fun getAllMedications(): Flow<List<UserMedication>>

    @Query("SELECT * FROM UserMedication WHERE id = :id")
    suspend fun getMedicationById(id: Int): UserMedication?

    @Query("SELECT * FROM UserMedication WHERE name = :name")
    suspend fun getMedicationByName(name: String): UserMedication?

    @Query("SELECT * FROM UserMedication WHERE type = :type ORDER BY name ASC")
    fun getMedicationsByType(type: String): Flow<List<UserMedication>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: UserMedication): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedications(medications: List<UserMedication>)

    @Update
    suspend fun updateMedication(medication: UserMedication)

    @Delete
    suspend fun deleteMedication(medication: UserMedication)

    @Query("DELETE FROM UserMedication WHERE id = :id")
    suspend fun deleteMedicationById(id: Int)

    @Query("DELETE FROM UserMedication")
    suspend fun deleteAllMedications()
}

