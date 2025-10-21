//CategoryUiState is a single, immutable data structure that describes everything a “Category” screen needs:
//Which category is displayed, what title it has, and which product items (ProductUi) to display.
package com.example.finalprojectnew.stage2.presentation.category.generic

import com.example.finalprojectnew.stage2.domain.model.CategoryKey

data class CategoryUiState(
    val key: CategoryKey,
    val title: String,
    val items: List<ProductUi>
)
