package com.example.first_project.ui.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM productsDb WHERE brand = :brand")
    fun getProductByBrand(brand: String): ProductEntity?

    @Query("SELECT * FROM productsDb")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("DELETE FROM productsDb WHERE brand = :brand")
    suspend fun deleteProduct(brand: String)

    @Query("DELETE FROM productsDb")
    suspend fun deleteAllProducts()

}