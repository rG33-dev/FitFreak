package com.example.fitfreak.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FitFreakDao {
    // Profile Queries
    @Query("SELECT * FROM user_profile WHERE uid = :userId")
    suspend fun getUserProfile(userId: String): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: UserProfileEntity)

    // History Queries (For your Graphs!)
    @Query("SELECT * FROM assessment_history ORDER BY date DESC")
    fun getAllHistory(): Flow<List<AssessmentHistoryEntity>>

    @Insert
    suspend fun insertAssessment(assessment: AssessmentHistoryEntity)
}