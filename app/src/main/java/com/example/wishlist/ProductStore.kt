package com.example.wishlist

import androidx.compose.runtime.mutableStateListOf

object ProductStore {
    val products = mutableStateListOf<Product>()

    init {
        products.addAll(
            listOf(
                Product(
                    id = 0,
                    name = "Product name",
                    address = "address",
                    photo = 1
                ),
                Product(
                    id = 1,
                    name = "Second product",
                    address = "address",
                    photo = 1
                ),
                Product(
                    id = 2,
                    name = "Third product",
                    address = "address",
                    photo = 1
                )
            )
        )
    }
}