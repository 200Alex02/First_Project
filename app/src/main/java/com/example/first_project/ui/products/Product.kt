package com.example.first_project.ui.products

data class Product(
    val brand: String,
    val description: String,
    val picture: String,
    var likeElement: Boolean = false
)
