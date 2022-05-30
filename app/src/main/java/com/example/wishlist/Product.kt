package com.example.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    var productIdImagePath: String,
    var name: String,
    var address: String
)
