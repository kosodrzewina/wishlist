package com.example.wishlist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.wishlist.MapViewModel
import com.example.wishlist.Product
import com.example.wishlist.ProductStore
import com.example.wishlist.composables.AddressPicker

@Composable
fun ProductDetailScreen(product: Product, navController: NavController) {
    var isAddressPicker by remember {
        mutableStateOf(false)
    }

    if (isAddressPicker) {
        AddressPicker(viewModel = MapViewModel(), product = product)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = product.name) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            ProductStore.products.remove(product)
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Column {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(product.productIdImagePath),
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
                            start = 16.dp,
                            top = 16.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        )
                    )
                    Text(
                        text = product.address,
                        modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
                IconButton(onClick = { isAddressPicker = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }
        }
    }
}
