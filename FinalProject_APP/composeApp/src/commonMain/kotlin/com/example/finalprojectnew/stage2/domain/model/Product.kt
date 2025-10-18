// It is the basic business structure of a product
package com.example.finalprojectnew.stage2.domain.model

data class Product(
    val id: String,
    val name: String,
    val categoryId: String,
    val imageKey: String
)
