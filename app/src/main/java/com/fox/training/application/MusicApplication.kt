package com.fox.training.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.fox.training.R
import com.fox.training.util.AppConstants

class MusicApplication : Application() {
    private lateinit var notificationManager: NotificationManager
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AppConstants.CHANNEL_ID,
                getString(R.string.channel_service),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(null, null)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        } else {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
    }
}
