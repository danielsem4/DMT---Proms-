// stage2/presentation/category/generic/CategoryViewModel.kt
package com.example.finalprojectnew.stage2.presentation.category.generic

import com.example.finalprojectnew.stage2.data.catalog.CategoryCatalog
import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel {

    private val _state = MutableStateFlow<CategoryUiState?>(null)
    val state: StateFlow<CategoryUiState?> = _state.asStateFlow()

    fun select(key: CategoryKey) {
        _state.value = CategoryUiState(
            key   = key,
            title = CategoryCatalog.titleOf(key),
            items = CategoryCatalog.itemsOf(key)
        )
    }
}
