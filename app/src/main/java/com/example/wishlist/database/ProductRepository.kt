package com.example.wishlist.database

import com.example.wishlist.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val selectAll: Flow<List<Product>> = productDao.selectAll()

    suspend fun insertProduct(product: Product) {
        Dispatchers.IO.apply {
            productDao.insert(product)
        }
    }

    suspend fun deleteProduct(product: Product) {
        Dispatchers.IO.apply {
            productDao.delete(product.productIdImagePath)
        }
    }

    suspend fun updateProductAddress(productIdImagePath: String, address: String) {
        Dispatchers.IO.apply {
            productDao.updateProductAddress(productIdImagePath, address)
        }
    }

    suspend fun updateProductLocation(
        productIdImagePath: String,
        address: String,
        latitude: Double,
        longitude: Double
    ) {
        Dispatchers.IO.apply {
            productDao.updateProductLocation(productIdImagePath, address, latitude, longitude)
        }
    }
}