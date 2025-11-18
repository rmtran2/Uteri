package com.cs407.uteri.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow


@Dao
interface CycleDao {
    @Query("SELECT * FROM cycle ORDER BY startDate DESC")
    fun getCycles() : Flow<List<Cycle>>

    @Query("SELECT * FROM cycle ORDER BY startDate DESC LIMIT 1")
    fun getLastCycle(): Cycle



}
@Database(
    entities = [
        Cycle::class
    ],
    version = 1
)
abstract class PeriodTrackerDatabase : RoomDatabase() {

    abstract fun cycleDao() : CycleDao

    companion object {
        @Volatile
        private var INSTANCE: PeriodTrackerDatabase? = null

        fun getInstance(context: Context): PeriodTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeriodTrackerDatabase::class.java,
                    "period_tracking_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}