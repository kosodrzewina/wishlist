package com.example.wishlist

import androidx.compose.runtime.mutableStateListOf

object ProductStore {
    val products = mutableStateListOf<Product>()

    init {
        products.addAll(
            listOf(
                Product(
                    name = "Product name",
                    address = "address",
                    photo = 1
                ),
                Product(
                    name = "Second product",
                    address = "address",
                    photo = 1
                ),
                Product(
                    name = "Third product",
                    address = "address",
                    photo = 1
                )
            )
        )
    }
}