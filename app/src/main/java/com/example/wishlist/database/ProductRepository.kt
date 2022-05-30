package com.example.wishlist.database

import androidx.lifecycle.LiveData
import com.example.wishlist.Product
import kotlinx.coroutines.Dispatchers

class ProductRepository(private val productDao: ProductDao) {
    val selectAll: LiveData<List<Product>> = productDao.selectAll()

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
}