package com.example.fitfreak.data.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserProfileEntity::class, AssessmentHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FitFreakDatabase : RoomDatabase() {

    abstract fun fitFreakDao(): FitFreakDao

    companion object {
        @Volatile
        private var INSTANCE: FitFreakDatabase? = null

        fun getDatabase(context: Context): FitFreakDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitFreakDatabase::class.java,
                    "fitfreak_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}