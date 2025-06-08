package org.example.hit.heal.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), KoinComponent {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        // 1) validate
        if (!isValidEmail(email)) {
            _message.value = "Invalid email format"
            return
        }
        if (password.isBlank()) {
            _message.value = "Password cannot be empty"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _message.value = null
            try {
                loginUseCase.execute(email, password)
                    .onSuccess { response ->
                        if (response.status == "Success") {


                            _isLoggedIn.value = true
                            _message.value = "Login successful"
                            onLoginSuccess()
                        } else {
                            _isLoggedIn.value = false
                            _message.value = response.status
                        }
                    }
                    .onError { error ->
                        println("Login error: $error")
                        _isLoggedIn.value = false
                        _message.value = error.name
                    }
            } catch (e: Exception) {
                _isLoggedIn.value = false
                _message.value = e.message ?: "Unexpected error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setMessage(message: String?) {
        _message.value = message
    }

    private fun isValidEmail(email: String): Boolean =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
            .matches(email)

    fun clearMessage() {
        _message.value = null
    }
}