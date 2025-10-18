package com.example.finalprojectnew.stage2.presentation.category.generic

import org.jetbrains.compose.resources.DrawableResource

data class ProductUi(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val iconRes: DrawableResource,
    val outOfStock: Boolean = false
)
