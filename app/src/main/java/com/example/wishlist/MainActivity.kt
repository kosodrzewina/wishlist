package com.example.wishlist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wishlist.database.ProductViewModel
import com.example.wishlist.navigation.NavGraph
import com.example.wishlist.ui.theme.WishlistTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var productViewModel: ProductViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestForegroundPermission()
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        Notifications.createChannel(this)

        locationRequest = LocationRequest.create()
        locationRequest.apply {
            interval = 5000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        GlobalScope.launch {
            productViewModel.selectAllProducts().collect { products ->
                products.forEach { product ->
                    if (!ProductStore.products.any {
                            it.productIdImagePath == product.productIdImagePath
                        }) {
                        ProductStore.products.add(product)
                        product.createGeofence(this@MainActivity)
                    }
                }
                updateGps()
            }
        }

        setContent {
            WishlistTheme {
                navHostController = rememberNavController()
                NavGraph(navHostController = navHostController, productViewModel, this)
            }
        }
    }

    private fun requestForegroundPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.FOREGROUND_SERVICE
            ) != PackageManager.PERMISSION_GRANTED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun updateGps() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                Log.d(TAG, "${it.latitude} ${it.longitude}")
            }
        }
    }
}
