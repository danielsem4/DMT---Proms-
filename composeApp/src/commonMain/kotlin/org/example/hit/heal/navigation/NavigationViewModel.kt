package org.example.hit.heal.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import core.data.storage.Storage
import core.util.PrefKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NavigationViewModel(
    storage: Storage
) : ViewModel() {
    private val _currentRoute = mutableStateOf(Screen.Login.route)
    val currentRoute: State<String> = _currentRoute

    val isLoggedInFlow: Flow<Boolean> =
        storage.getAsFlow(PrefKeys.clinicId).map { it != null }

    fun navigateTo(route: String) {
        _currentRoute.value = route
    }
} 