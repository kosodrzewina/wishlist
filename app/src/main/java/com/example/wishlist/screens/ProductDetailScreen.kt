package com.example.wishlist.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wishlist.Product

@Composable
fun ProductDetailScreen(product: Product) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = product.name) }
            )
        }
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)
            )
            Text(
                text = product.address,
                modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}
