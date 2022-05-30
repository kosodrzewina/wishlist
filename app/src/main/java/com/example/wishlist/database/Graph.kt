package com.example.wishlist.database

import android.content.Context

object Graph {
    lateinit var database: ProductDatabase
        private set
    val productRepo by lazy {
        ProductRepository(database.productDao())
    }

    fun provide(context: Context) {
        database = ProductDatabase.getDatabase(context)
    }
}