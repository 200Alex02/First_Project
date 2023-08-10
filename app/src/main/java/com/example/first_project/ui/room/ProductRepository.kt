package com.example.first_project.ui.room

import androidx.lifecycle.LiveData

interface ProductRepository {

    suspend fun insertProduct(productDb: ProductDb)

    fun getAllProducts(): LiveData<List<ProductDb>>

    suspend fun deleteProducts(id: Long)

    suspend fun updateProduct(productDb: ProductDb)
}