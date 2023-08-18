package com.example.first_project.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.first_project.ui.database.AppDatabase
import com.example.first_project.ui.database.ProductEntity
import com.example.first_project.ui.database.ProductRepository
import com.example.first_project.ui.products.Product
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

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product.brand)
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllProducts()
        }
    }

}