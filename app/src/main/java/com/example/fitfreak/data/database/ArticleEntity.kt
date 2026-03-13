package com.example.fitfreak.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val uid: String, // From Firebase
    val name: String,
    val currentFitScore: Int,
    val rank: String, // FREAK, ELITE, etc.
    val bioAge: Int
)


@Entity(tableName = "assessment_history")
data class AssessmentHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long, // System.currentTimeMillis()
    val score: Int,
    val pushups: Int,
    val plankSec: Int,
    val runTimeMin: Float
)


