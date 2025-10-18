package com.example.finalprojectnew.stage2.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.finalprojectnew.stage2.data.catalog.CategoryCatalog
import com.example.finalprojectnew.stage2.data.catalog.CatalogItem
import com.example.finalprojectnew.stage2.data.catalog.search

class SearchViewModel {
    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<CatalogItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onQueryChange(newValue: String) {
        query = newValue
        results = if (newValue.isBlank()) emptyList() else CategoryCatalog.search(newValue)
    }

    fun clear() {
        query = ""
        results = emptyList()
    }
}
