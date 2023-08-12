package com.example.first_project.ui.database

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val getAllProducts: LiveData<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun addProduct(productEntity: ProductEntity) {
        val existingProduct = productDao.getProductById(productEntity.id)
        if (existingProduct == null) {
            productDao.addProduct(productEntity)
        }
    }

    suspend fun deleteProduct(productId: Long) {
        productDao.deleteProduct(productId)
    }

    suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }
}