package com.example.wishlist.composables

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.wishlist.MainActivity
import com.example.wishlist.MapViewModel
import com.example.wishlist.Product
import com.example.wishlist.database.ProductViewModel
import com.example.wishlist.rememberMapViewWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@Composable
fun AddressPicker(viewModel: MapViewModel, product: Product, productViewModel: ProductViewModel) {
    Surface(color = MaterialTheme.colors.background) {
        val mapView = rememberMapViewWithLifecycle()
        val currentLocation = viewModel.location.collectAsState()
        var address by remember { viewModel.addressText }
        val context = LocalContext.current

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = address,
                modifier = Modifier.padding(all = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(modifier = Modifier.height(500.dp)) {
                currentLocation.value.let {
                    address = viewModel.getAddressFromLocation(context)
                }

                MapViewContainer(mapView, viewModel)
                MapPinOverlay()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    shape = RoundedCornerShape(100.dp),
                    onClick = {
                        productViewModel.updateProductLocation(
                            product.productIdImagePath,
                            address,
                            viewModel.location.value.latitude,
                            viewModel.location.value.longitude
                        )
                        context.startActivity(Intent(context, MainActivity::class.java))
                    },
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
fun MapPinOverlay() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
        }
        Box(Modifier.weight(1f))
    }
}

@Composable
private fun MapViewContainer(
    mapView: MapView,
    viewModel: MapViewModel
) {
    AndroidView(
        factory = { mapView }
    ) {

        mapView.getMapAsync {
            it.uiSettings.setAllGesturesEnabled(true)

            val location = viewModel.location.value
            val position = LatLng(location.latitude, location.longitude)

            it.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
            it.setOnCameraIdleListener {
                val cameraPosition = it.cameraPosition

                viewModel.updateLocation(
                    cameraPosition.target.latitude,
                    cameraPosition.target.longitude
                )
            }
        }
    }
}
