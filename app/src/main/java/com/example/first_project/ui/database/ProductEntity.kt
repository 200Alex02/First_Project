package com.example.first_project.ui.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.first_project.ui.products.Product

@Entity(tableName = "productsDb")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val brand: String,
    val description: String,
    val picture: String,
    val cost: String,
    var likeElement: Boolean = false
)

object ProductMapper {
    fun toProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            0,
            brand = product.brand,
            description = product.description,
            picture = product.picture,
            cost = product.cost,
            likeElement = product.likeElement
        )
    }

    fun fromProductEntity(productEntity: ProductEntity): Product {
        return Product(
            brand = productEntity.brand,
            description = productEntity.description,
            picture = productEntity.description,
            cost = productEntity.cost,
            likeElement = productEntity.likeElement
        )
    }
}