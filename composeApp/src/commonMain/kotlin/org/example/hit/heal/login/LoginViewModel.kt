package org.example.hit.heal.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.network.AuthApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), KoinComponent {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val authApi: AuthApi by inject()

    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _message.value = null

                val result = authApi.login(email, password)
                result.fold(
                    onSuccess = { response ->
                        if (response.status == "Success") {
                            _isLoggedIn.value = true
                            _message.value = "Login successful"
                            onLoginSuccess()
                        } else {
                            _isLoggedIn.value = false
                            _message.value = response.status ?: "Login failed"
                        }
                    },
                    onFailure = { exception ->
                        _isLoggedIn.value = false
                        _message.value = exception.message ?: "Login failed"
                    }
                )
            } catch (e: Exception) {
                _isLoggedIn.value = false
                _message.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setMessage(message: String?) {
        _message.value = message
    }

    fun clearMessage() {
        _message.value = null
    }
}