package com.example.wishlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wishlist.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun selectAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("Delete From product WHERE productIdImagePath = :productIdImagePath")
    suspend fun delete(productIdImagePath: String)

    @Query("UPDATE product SET address = :address WHERE productIdImagePath = :productIdImagePath")
    suspend fun updateProductAddress(productIdImagePath: String, address: String)
}