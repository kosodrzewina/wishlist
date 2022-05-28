package com.example.wishlist.screens

import android.content.Intent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.wishlist.CameraActivity
import com.example.wishlist.Product
import com.example.wishlist.composables.ProductList

@Composable
fun HomeScreen(products: List<Product>, navController: NavController) {
    val context = LocalContext.current

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
                onClick = {
                    context.startActivity(Intent(context, CameraActivity::class.java))
                }
            )
        }
    ) {
        ProductList(products = products, navController = navController)
    }
}