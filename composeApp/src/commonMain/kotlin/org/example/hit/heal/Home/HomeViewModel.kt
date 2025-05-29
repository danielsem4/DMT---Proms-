package org.example.hit.heal.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.storage.Storage
import core.domain.use_case.LoginUseCase
import core.util.PrefKeys
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * HomeViewModel handle logout, features and set user data in server
 */

class HomeViewModel(
    private val storage: Storage
) : ViewModel(), KoinComponent {

//    private val _features = mutableStateListOf<Feature>()
//    val features: List<St> = _features


    fun getFeatures() {

    }

    // logout and clear the prefs data
    suspend fun logout() {
        storage.clearValue(PrefKeys.clinicId)
        storage.clearValue(PrefKeys.serverUrl)
        storage.clearValue(PrefKeys.token)
        storage.clearValue(PrefKeys.userId)
        println(
            "cleaned prefs:${storage.get(PrefKeys.clinicId)} ${storage.get(PrefKeys.serverUrl)}  ${
                storage.get(
                    PrefKeys.token
                )
            } ${storage.get(PrefKeys.userId)}"
        )
    }
}