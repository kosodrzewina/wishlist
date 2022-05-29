package com.example.wishlist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.wishlist.composables.AddressPicker
import com.example.wishlist.composables.CameraView
import com.example.wishlist.ui.theme.WishlistTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var photoUri: Uri
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var isCamer = mutableStateOf(false)
    private var isPhoto = mutableStateOf(false)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                isCamer.value = true
            } else {
                this.startActivity(Intent(this, MainActivity::class.java))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WishlistTheme {
                if (isCamer.value) {
                    CameraView(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = ::handleImageCapture,
                        onError = {}
                    )
                }

                if (isPhoto.value) {
                    CreateProductScreen()
                }
            }
        }

        requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            isCamer.value = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

//    private fun requestLocationPermission() {
//        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        ) {
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }

    private fun handleImageCapture(uri: Uri) {
        isCamer.value = false
        photoUri = uri
        isPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    @Composable
    fun CreateProductScreen() {
        val newProduct by remember {
            mutableStateOf(Product("", "", ""))
        }
        var nameValue by remember {
            mutableStateOf("")
        }
        var isAddressPicker by remember {
            mutableStateOf(false)
        }

        if (isAddressPicker) {
            AddressPicker(newProduct)
        } else {
            Column {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(photoUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            top = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Text(
                        text = "address",
                        modifier = Modifier.padding(
                            top = 8.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = nameValue,
                        label = { Text(text = "Name") },
                        onValueChange = { nameValue = it }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isPhoto.value = false
                            isCamer.value = true
                        },
                        modifier = Modifier.padding(all = 16.dp)
                    ) {
                        Text(text = "Decline")
                    }
                    Button(
                        onClick = {
//                        requestLocationPermission()
//
//                        if (
//                            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.ACCESS_FINE_LOCATION
//                            )
//                        ) {
//                            fusedLocationClient
//                                .lastLocation
//                                .addOnSuccessListener { lastLocation: Location? ->
//                                    if (lastLocation != null) {
//                                        location = lastLocation
//                                    }
//                                }
//                        }

                            newProduct.apply {
                                productIdImagePath = photoUri.toString()
                                name = nameValue
                            }
                            ProductStore.products.add(newProduct)
                            isAddressPicker = true
                        },
                        modifier = Modifier.padding(all = 16.dp)
                    ) {
                        Text(text = "Accept")
                    }
                }
            }
        }
    }
}