package org.example.hit.heal.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.core.Network.AuthApi
import org.example.hit.heal.core.Network.LoginRequest
import org.example.hit.heal.core.Network.NetworkResult
import org.example.hit.heal.network.LoginRequest

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class AuthViewModel(private val authApi: AuthApi) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val loginRequest = LoginRequest(email, password)
            when (val result = authApi.login(loginRequest)) {
                is NetworkResult.Success -> {
                    val response = result.data
                    _loginState.value = LoginState.Success(response.token)
                }

                is NetworkResult.Error -> {
                    _loginState.value = LoginState.Error(result.message)
                }

                else -> {
                    _loginState.value = LoginState.Error("Unknown error")
                }
            }
        }
    }
}