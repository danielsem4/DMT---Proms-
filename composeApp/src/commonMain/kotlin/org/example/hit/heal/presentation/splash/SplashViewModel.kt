package org.example.hit.heal.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.storage.Storage
import core.util.PrefKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel(
    private val storage: Storage
) : ViewModel(), KoinComponent {

    // Optionally expose image URL (if you want to use it)
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isCheckingLogin = MutableStateFlow(true)
    val isCheckingLogin: StateFlow<Boolean> = _isCheckingLogin

    init {
        viewModelScope.launch {
            val token = storage.get(PrefKeys.token)
            _isLoggedIn.value = !token.isNullOrEmpty()
            _isCheckingLogin.value = false
        }
    }
}