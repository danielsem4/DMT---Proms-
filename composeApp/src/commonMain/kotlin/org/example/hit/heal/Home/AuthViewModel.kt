package org.example.hit.heal.Home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.hit.heal.core.domain.AuthService

class AuthViewModel {
    private val authService = AuthService()
    private val _loginState = MutableStateFlow<SuccessfulLoginResponse?>(null)
    val loginState: StateFlow<SuccessfulLoginResponse?> = _loginState

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authService.login(username, password)
            _loginState.value = response
        }
    }
}
