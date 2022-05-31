package com.example.wishlist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlist.Product
import com.example.wishlist.ProductStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val selectAll: Flow<List<Product>>
    private val repository: ProductRepository

    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao = productDao)
        selectAll = repository.selectAll
    }

    fun selectAllProducts(): Flow<List<Product>> {
        return repository.selectAll
    }

    fun addProduct(product: Product) {
        ProductStore.products.add(product)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product = product)
        }
    }

    fun updateProductLocation(
        productIdImagePath: String,
        address: String,
        latitude: Double,
        longitude: Double
    ) {
        val product = ProductStore.products.first { it.productIdImagePath == productIdImagePath }

        product.apply {
            this.address = address
            this.latitude = latitude
            this.longitude = longitude
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductLocation(productIdImagePath, address, latitude, longitude)
        }
    }

    fun deleteProduct(product: Product) {
        ProductStore.products.remove(product)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product = product)
        }
    }
}
