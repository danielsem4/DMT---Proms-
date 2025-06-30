package org.example.hit.heal.home

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
            storage.get(PrefKeys.clinicId)?.let { id ->
                api.getModules(clinicId = id)
                    .onSuccess {
                        _features.value = it
                        println("Features fetched:\n ${it.map { m -> m.toString() + "\n" }}")
                    }.onError {
                        _features.value = emptyList()
                        println("Error getting features: $it")
                    }
            } ?: run {
                _features.value = emptyList()
                println("Error getting clinicId")
            }
        }
    }

    // logout and clear the prefs data
    suspend fun logout() {
        storage.clearValue(PrefKeys.clinicId)
        storage.clearValue(PrefKeys.serverUrl)
        storage.clearValue(PrefKeys.token)
        storage.clearValue(PrefKeys.userId)
        println(
            "cleaned prefs:${storage.get(PrefKeys.clinicId)} ${storage.get(PrefKeys.serverUrl)}  ${
                storage.get(PrefKeys.token)
            } ${storage.get(PrefKeys.userId)}"
        )
    }
}