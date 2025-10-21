// Manages the search screen mode
package com.example.finalprojectnew.stage2.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.finalprojectnew.stage2.data.catalog.CategoryCatalog
import com.example.finalprojectnew.stage2.data.catalog.CatalogItem
import com.example.finalprojectnew.stage2.data.catalog.search

class SearchViewModel {
    var query by mutableStateOf("") //query= the text that the user type
        private set
    var results by mutableStateOf<List<CatalogItem>>(emptyList()) // results= list of results for display
        private set
    var isLoading by mutableStateOf(false) //for the future work with the server
        private set

    fun onQueryChange(newValue: String) { // function that receive from the UI every type
        query = newValue // update the query
        results =
            if (newValue.isBlank()) emptyList() else CategoryCatalog.search(newValue) //If empty/spaces – deletes results, else- update results
    }

    fun clear() { // reset screen: clears the text and results (for example by pressing X).
        query = ""
        results = emptyList()
    }
}
