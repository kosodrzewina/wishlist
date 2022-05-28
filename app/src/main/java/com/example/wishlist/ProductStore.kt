package com.example.wishlist

import androidx.compose.runtime.mutableStateListOf

object ProductStore {
    val products = mutableStateListOf<Product>()

    init {
        products.addAll(
            listOf(
                Product(
                    productIdImagePath = "0",
                    name = "Product name",
                    address = "address",
                ),
                Product(
                    productIdImagePath = "1",
                    name = "Second product",
                    address = "address",
                ),
                Product(
                    productIdImagePath = "2",
                    name = "Third product",
                    address = "address",
                )
            )
        )
    }
}