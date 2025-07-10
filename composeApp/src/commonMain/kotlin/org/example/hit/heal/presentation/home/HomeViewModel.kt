package org.example.hit.heal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.ModulesResponse
import core.data.storage.Storage
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.util.PrefKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * HomeViewModel handle logout, features and set user data in server
 */

class HomeViewModel(
    private val storage: Storage,
    private val api: AppApi
) : ViewModel(), KoinComponent {

    private val _features = MutableStateFlow<List<ModulesResponse>>(emptyList())
    val features: StateFlow<List<ModulesResponse>> = _features

    fun loadFeatures() {
        viewModelScope.launch {
            val clinicId = storage.get(PrefKeys.clinicId)
            if (clinicId == null) {
                println("clinicId is null")
                return@launch
            }
            api.getModules(clinicId)
                .onSuccess { result ->
                    val updated = result + ModulesResponse("Clock", 16, true)
                    _features.value = updated
                    println("Features fetched: $updated")
                }
                .onError {
                    _features.value = emptyList()
                    println("Error getting features: $it")
                }
        }
    }

    suspend fun logout() {
        storage.clearValue(PrefKeys.clinicId)
        storage.clearValue(PrefKeys.serverUrl)
        storage.clearValue(PrefKeys.token)
        storage.clearValue(PrefKeys.userId)
    }
}