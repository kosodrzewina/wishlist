package com.example.wishlist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist.Product
import com.example.wishlist.navigation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ProductList(products: List<Product>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = products) {
            ProductListItem(product = it, onClick = {
                navController.navigate(
                    route = Screen.ProductDetailScreen.routeWithArgs(
                        URLEncoder.encode(it.productIdImagePath, StandardCharsets.UTF_8.toString())
                    )
                )
            })
        }
    }
}