package com.example.wishlist.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wishlist.Product

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = product.name, modifier = Modifier.padding(all = 16.dp))
    }
}