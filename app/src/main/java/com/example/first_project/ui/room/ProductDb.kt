package com.example.first_project.ui.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val brand: String,
    val description: String,
    val picture: String,
    val cost: String,
    var likeElement: Boolean = false
)
