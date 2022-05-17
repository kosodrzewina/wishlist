package com.example.wishlist.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.Composable
import com.example.wishlist.Product
import com.example.wishlist.composables.ProductList

@Composable
fun HomeScreen(products: List<Product>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Wishlist") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add Item") },
                icon = { Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = null) },
                onClick = { /*TODO*/ }
            )
        }
    ) {
        ProductList(products = products)
    }
}