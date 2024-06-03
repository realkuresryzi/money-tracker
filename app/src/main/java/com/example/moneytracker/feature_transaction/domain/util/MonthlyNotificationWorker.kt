package com.example.moneytracker.feature_transaction.domain.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moneytracker.R
import java.time.LocalDateTime

class MonthlyNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "monthly")
            .setContentText("Content text ${LocalDateTime.now()}")
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.chart_box)
            .build()
        notificationManager.notify(1, notification)
    }
}