package com.example.wishlist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wishlist.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun selectAll(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("Delete From product WHERE productIdImagePath = :productIdImagePath")
    suspend fun delete(productIdImagePath: String)

    @Query("UPDATE product SET address = :address WHERE productIdImagePath = :productIdImagePath")
    suspend fun updateProductAddress(productIdImagePath: String, address: String)

    @Query(
        """
       UPDATE product
       SET address = :address, latitude = :latitude, longitude = :longitude 
       WHERE productIdImagePath = :productIdImagePath 
    """
    )
    suspend fun updateProductLocation(
        productIdImagePath: String,
        address: String,
        latitude: Double,
        longitude: Double
    )
}