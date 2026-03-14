package com.example.fitfreak.model.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfreak.data.database.AssessmentHistoryEntity
import com.example.fitfreak.data.database.FitFreakDatabase
import com.example.fitfreak.data.repository.appRepo.FitFreakRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch


class FitnessViewModel2(application: Application) : AndroidViewModel(application) {
    private val repository: FitFreakRepository
    private val CHANNEL_ID = "Freak OUT!!"


    val history = FitFreakDatabase.getDatabase(application).fitFreakDao().getAllHistory()

    init {
        val dao = FitFreakDatabase.getDatabase(application).fitFreakDao()
        repository = FitFreakRepository(dao)
        createNotificationChannel()
    }

    fun saveResult(score: Int, pushups: Int, plank: Int, run: Float, squat: Int) {
        viewModelScope.launch {
            val newEntry = AssessmentHistoryEntity(
                date = System.currentTimeMillis(),
                score = score,
                pushups = pushups,
                plankSec = plank,
                runTimeMin = run
            )
            repository.saveAssessment(newEntry)

            showNotification(
                title = "Evolution",
                message = "Score recorded $score, next to beat $score"
            )
        }
    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "FitFreak Alerts"
            val descriptionText = "Notifications for workout reminders and progress"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText

                lockscreenVisibility =
                    android.app.Notification.VISIBILITY_PUBLIC // FOR LOCK SCREEN
            }
            val notificationManager: NotificationManager =
                getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun showNotification(title: String, message: String) {
        val context = getApplication<Application>()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert) // Replace with your app icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Required for Lock Screen
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // Permission check for Android 13+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (androidx.core.content.ContextCompat.checkSelfPermission(
                        context, android.Manifest.permission.POST_NOTIFICATIONS
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    notify(System.currentTimeMillis().toInt(), builder.build())
                }
            } else {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }
    }
}








