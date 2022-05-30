package com.example.wishlist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wishlist.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val selectAll: LiveData<List<Product>>
    private val repository: ProductRepository

    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao = productDao)
        selectAll = repository.selectAll
    }

    fun selectAllProducts(): List<Product>? {
        return repository.selectAll.value
//        var products: List<Product>?
//
//        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
//            products = repository.selectAll.value
//        }
//
//        return products
    }

    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product = product)
        }
    }

    fun updateProductAddress(productIdImagePath: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductAddress(productIdImagePath, address)
        }
    }

    fun deleteSample(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product = product)
        }
    }
}

//class ProductViewModelFactory(
//    private val application: Application
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
//            return ProductViewModel(application) as T
//        }
//
//        throw IllegalArgumentException()
//    }
//}