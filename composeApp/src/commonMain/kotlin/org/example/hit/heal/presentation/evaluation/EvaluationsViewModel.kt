package org.example.hit.heal.presentation.evaluation

import androidx.lifecycle.ViewModel
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.api.AppApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

/**
 * EvaluationsViewModel is responsible for managing the state and logic related to evaluations.
 */

class EvaluationsViewModel(
    private val storage: Storage,
    private val api: AppApi
) : ViewModel(), KoinComponent {

    private val _items = MutableStateFlow<List<Evaluation>>(emptyList())
    val items: StateFlow<List<Evaluation>> = _items.asStateFlow()

    init {
        _items.value = listOf(

        )
    }

}