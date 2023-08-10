package com.example.first_project.ui.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository

    val allProducts: LiveData<List<ProductDb>>

    init {
        val productDao = AppDatabase.getInstance(application).productDao()
        repository = ProductRepositoryImpl(productDao)
        allProducts = repository.getAllProducts()
    }

    fun updateProduct(productDb: ProductDb) {
        viewModelScope.launch {
            repository.updateProduct(productDb)
        }
    }

}