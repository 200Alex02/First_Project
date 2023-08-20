package com.example.first_project.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.first_project.products
import com.example.first_project.ui.database.AppDatabase
import com.example.first_project.ui.database.ProductEntity
import com.example.first_project.ui.database.ProductMapper
import com.example.first_project.ui.database.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    val getAllProducts: LiveData<List<ProductEntity>>
    private val repository: ProductRepository

    init {
        val productDao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        getAllProducts = repository.getAllProducts
    }

    val onDeleteClick: (ProductEntity) -> Unit = {
        deleteProduct(it)
    }

    fun deleteProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = repository.getProductById(productEntity.brand)
            repository.deleteProduct(productEntity.brand)
            val productNorm = product?.let { ProductMapper.fromProductEntity(it) }
            for (prod in products) {
                if (prod.brand == productNorm?.brand) {
                    prod.likeElement = false
                }
            }
        }
    }
}

