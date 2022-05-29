package com.example.wishlist.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.wishlist.Product

@Composable
fun AddressPicker(product: Product) {
    Text(text = product.name)
    Text(text = product.productIdImagePath)
}