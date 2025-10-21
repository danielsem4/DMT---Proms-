// is a pure UI model that represents how we display a product on the screen
// the UI works with this object to draw product cards
package com.example.finalprojectnew.stage2.presentation.category.generic

import org.jetbrains.compose.resources.DrawableResource

data class ProductUi(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val iconRes: DrawableResource,
    val outOfStock: Boolean = false
)
