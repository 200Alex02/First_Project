package com.example.first_project.ui.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    val getAllProducts: LiveData<List<ProductEntity>>
    private val repository: ProductRepository

    init {
        val productDao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        getAllProducts = repository.getAllProducts
    }


    fun addProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(productEntity)
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(productId)
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllProducts()
        }
    }

}