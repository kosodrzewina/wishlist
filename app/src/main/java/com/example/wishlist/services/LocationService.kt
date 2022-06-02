package com.example.wishlist.services

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.wishlist.Notifications
import com.example.wishlist.ProductStore
import com.google.android.gms.location.GeofencingEvent

private const val NOTIFICATION_ID = 1
private const val TAG = "LocationService"

class LocationService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val geofence = intent?.let { GeofencingEvent.fromIntent(it) }
        val id = geofence?.triggeringGeofences?.firstOrNull()?.requestId

        if (id != null) {
            val product = ProductStore.products.first { it.productIdImagePath == id }

            Log.d(TAG, "Entered location")

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.FOREGROUND_SERVICE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startForeground(
                    NOTIFICATION_ID,
                    Notifications.createNotification(this, product)
                )
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
}