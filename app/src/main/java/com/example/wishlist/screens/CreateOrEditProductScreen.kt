package com.example.wishlist.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.wishlist.Product

@Composable
fun CreateOrEditProductScreen(product: Product, navController: NavController) {
    var nameValue by remember {
        mutableStateOf(product.name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        OutlinedTextField(
            value = product.name,
            label = { Text(text = "Name") },
            onValueChange = { nameValue = it }
        )
    }
}