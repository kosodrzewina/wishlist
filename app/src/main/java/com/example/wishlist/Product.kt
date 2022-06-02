package com.example.wishlist

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

fun Product.createGeofence(context: Context) {
    Geofencing.createGeofence(context, LatLng(this.latitude, this.longitude), this.address)
}

@Entity
data class Product(
    @PrimaryKey
    var productIdImagePath: String,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double
)
