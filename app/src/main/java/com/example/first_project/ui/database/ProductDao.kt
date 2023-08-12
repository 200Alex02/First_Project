package com.example.first_project.ui.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM productsDb WHERE id = :productId")
    fun getProductById(productId: Long): ProductEntity?

    @Query("SELECT * FROM productsDb")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("DELETE FROM productsDb WHERE id = :productId")
    suspend fun deleteProduct(productId: Long)

    @Query("DELETE FROM productsDb")
    suspend fun deleteAllProducts()

}