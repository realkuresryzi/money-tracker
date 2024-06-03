package com.example.moneytracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoneyTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "monthly",
            "Monthly summary",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Sends spending summary once a month"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}