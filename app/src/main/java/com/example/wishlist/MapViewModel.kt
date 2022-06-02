package com.example.wishlist

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class MapViewModel : ViewModel() {
    val location = MutableStateFlow(getInitialLocation())
    val addressText = mutableStateOf("")

    private fun getInitialLocation(): Location {
        val initialLocation = Location("")

        initialLocation.latitude = 52.2297
        initialLocation.longitude = 21.0122

        return initialLocation
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        if (latitude != location.value.latitude) {
            val location = Location("")

            location.latitude = latitude
            location.longitude = longitude
            setLocation(location)
        }
    }

    private fun setLocation(loc: Location) {
        location.value = loc
    }

    fun getAddressFromLocation(context: Context): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>? = null
        val addressText: String

        try {
            addresses =
                geocoder.getFromLocation(location.value.latitude, location.value.longitude, 1)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val address: Address? = addresses?.get(0)

        addressText = address?.getAddressLine(0) ?: "Address not found"

        return addressText
    }
}