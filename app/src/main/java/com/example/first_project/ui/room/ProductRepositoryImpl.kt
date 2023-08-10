package com.example.first_project.ui.room

import androidx.lifecycle.LiveData

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {

    override fun getAllProducts(): LiveData<List<ProductDb>> {
        return productDao.getAllProducts()
    }

    override suspend fun insertProduct(productDb: ProductDb) {
        return productDao.insertProduct(productDb)
    }

    override suspend fun deleteProducts(id: Long) {
        return productDao.deleteProduct(id)
    }

    override suspend fun updateProduct(productDb: ProductDb) {
        return productDao.updateProduct(productDb)
    }

}