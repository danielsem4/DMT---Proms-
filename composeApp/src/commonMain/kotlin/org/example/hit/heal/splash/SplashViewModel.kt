package org.example.hit.heal.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.core.Network.session.SessionManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel : ViewModel(), KoinComponent {
    private val sessionManager: SessionManager by inject()

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        viewModelScope.launch {
            _isLoggedIn.value = sessionManager.isLoggedIn()
            if (_isLoggedIn.value) {
                val session = sessionManager.getUserSession()
                _imageUrl.value = session?.image
            }
        }
    }
}