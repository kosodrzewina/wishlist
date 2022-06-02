package com.example.wishlist

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.wishlist.services.LocationService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

private const val REQUEST_CODE = 2
private const val TAG = "Geofencing"

object Geofencing {
    @SuppressLint("MissingPermission")
    fun createGeofence(context: Context, product: Product) {
        val geofence = Geofence
            .Builder()
            .setCircularRegion(product.latitude, product.longitude, 200f)
            .setRequestId(product.productIdImagePath)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
        val request = GeofencingRequest
            .Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            ).let {
                context.startActivity(it)
            }
        } else {
            LocationServices
                .getGeofencingClient(context)
                .addGeofences(request, makePendingIntent(context))
                .addOnFailureListener { Log.d(TAG, it.toString()) }
                .addOnSuccessListener {
                    Log.d(TAG, "Geofence for ${product.name} successfully added")
                }
        }
    }

    private fun makePendingIntent(context: Context): PendingIntent =
        PendingIntent.getForegroundService(
            context,
            REQUEST_CODE,
            Intent(context, LocationService::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}