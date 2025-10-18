package com.example.finalprojectnew.stage2.presentation.category.generic

import com.example.finalprojectnew.stage2.domain.model.CategoryKey

data class CategoryUiState(
    val key: CategoryKey,
    val title: String,
    val items: List<ProductUi>
)
