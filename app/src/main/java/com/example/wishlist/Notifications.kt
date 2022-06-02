package com.example.wishlist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

private const val CHANNEL_ID = "id_channel_default"

object Notifications {
    fun createChannel(context: Context) {
        val channel =
            NotificationChannel(
                CHANNEL_ID, "Locations", NotificationManager.IMPORTANCE_DEFAULT
            )

        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    fun createNotification(context: Context) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_outline_location_on_24)
        .setContentTitle("Test notification")
        .build()
}