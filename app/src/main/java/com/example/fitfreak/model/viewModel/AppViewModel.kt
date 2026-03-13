package com.example.fitfreak.model.viewModel

import android.app.Application
import androidx.activity.result.launch
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfreak.data.AuthViewModel
import com.example.fitfreak.data.database.AssessmentHistoryEntity
import com.example.fitfreak.data.database.FitFreakDatabase
import com.example.fitfreak.data.repository.appRepo.FitFreakRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class FitnessViewModel2(application: Application) : AndroidViewModel(application) {
    private val repository: FitFreakRepository

    // This Flow updates your UI automatically when data changes
    val history = FitFreakDatabase.getDatabase(application).fitFreakDao().getAllHistory()

    init {
        val dao = FitFreakDatabase.getDatabase(application).fitFreakDao()
        repository = FitFreakRepository(dao)
    }

    fun saveResult(score: Int, pushups: Int, plank: Int, run: Float) {
        viewModelScope.launch {
            val newEntry = AssessmentHistoryEntity(
                date = System.currentTimeMillis(),
                score = score,
                pushups = pushups,
                plankSec = plank,
                runTimeMin = run
            )
            repository.saveAssessment(newEntry)
        }
    }
}
