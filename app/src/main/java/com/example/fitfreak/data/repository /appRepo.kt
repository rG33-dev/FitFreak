package com.example.fitfreak.data.repository.appRepo

import com.example.fitfreak.data.database.AssessmentHistoryEntity
import com.example.fitfreak.data.database.FitFreakDao
import kotlinx.coroutines.flow.Flow


class FitFreakRepository(private val dao: FitFreakDao) {
    // All history for graphs
    val allHistory: Flow<List<AssessmentHistoryEntity>> = dao.getAllHistory()

    // Save locally
    suspend fun saveAssessment(assessment: AssessmentHistoryEntity) {
        dao.insertAssessment(assessment)
    }

    // Get profile locally
    suspend fun getProfile(uid: String) = dao.getUserProfile(uid)
}