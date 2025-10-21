// Manages the category screen mode
package com.example.finalprojectnew.stage2.presentation.category.generic

import com.example.finalprojectnew.stage2.data.catalog.CategoryCatalog
import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel {
    private val _state = MutableStateFlow<CategoryUiState?>(null) // the internal state
    val state: StateFlow<CategoryUiState?> =
        _state.asStateFlow() // the public state. asStateFlow()=the UI cant change the state

    fun select(key: CategoryKey) { //The entry point from the UI when a user selects a category
        _state.value = CategoryUiState(
            key = key,
            title = CategoryCatalog.titleOf(key),
            items = CategoryCatalog.itemsOf(key)
        )
    }
}
