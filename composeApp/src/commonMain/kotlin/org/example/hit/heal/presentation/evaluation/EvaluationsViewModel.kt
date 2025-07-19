package org.example.hit.heal.presentation.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.util.PrefKeys
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * EvaluationsViewModel is responsible for managing the state and logic related to evaluations.
 */

class EvaluationsViewModel(
    private val storage: Storage,
    private val api: AppApi
) : ViewModel(), KoinComponent {

    private val _evalItems = MutableStateFlow<List<Evaluation>>(emptyList())
    val evalItems: StateFlow<List<Evaluation>> = _evalItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getAllEvaluations() {
        viewModelScope.launch {
            try {
                getAllEvaluationsSuspend()
            } catch (e: SocketTimeoutException) {
                _evalItems.value = emptyList()
                _errorMessage.value = "Request timed out"
            }
        }
    }

    private suspend fun getAllEvaluationsSuspend() {
        _isLoading.value = true

        val clinicId = storage.get(PrefKeys.clinicId)!!
        val patientId = storage.get(PrefKeys.userId)!!.toInt()

        val result = api.getPatientMeasureReport(clinicId, patientId)

        result.onSuccess { list ->
            _evalItems.value = list
            println("Eval items")
            println(list.flatMap { it.measurement_objects }.joinToString(separator = "\n"))
            _errorMessage.value = null
        }.onError { error ->
            _evalItems.value = emptyList()
            _errorMessage.value = when (error) {
                DataError.Remote.REQUEST_TIMEOUT -> "Request timed out"
                DataError.Remote.NO_INTERNET -> "No internet connection"
                else -> "Failed to load evaluations"
            }
        }
        _isLoading.value = false
    }

}