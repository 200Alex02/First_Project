package com.example.first_project.ui.database

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {

    val getAllProducts: LiveData<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun addProduct(productEntity: ProductEntity) {
        val existingProduct = productDao.getProductByBrand(productEntity.brand)
        if (existingProduct == null) {
            productDao.addProduct(productEntity)
        }
    }

    suspend fun deleteProduct(brand: String) {
        productDao.deleteProduct(brand)
    }

    suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    suspend fun getProductById(brand: String): ProductEntity? {
        return productDao.getProductByBrand(brand)
    }
}