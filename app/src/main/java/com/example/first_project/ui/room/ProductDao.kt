package com.example.first_project.ui.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(productDb: ProductDb)

    @Insert
    suspend fun insertAllProducts(list: List<ProductDb>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<ProductDb>>

    @Query("DELETE FROM products WHERE id = id")
    suspend fun deleteProduct(id: Long)

    @Update
    suspend fun updateProduct(productDb: ProductDb)
}