package org.example.hit.heal.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.SuccessfulLoginResponse
import core.data.storage.Storage
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.LoginUseCase
import core.util.PrefKeys
import core.utils.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val storage: Storage
) : ViewModel(), KoinComponent {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    fun login(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailed: (message: StringResource) -> Unit
    ) {
        // 1) validation
        if (!isValidEmail(email)) {
            onLoginFailed(Resources.String.invalid_email)
            return
        }
        if (password.isBlank()) {
            onLoginFailed(Resources.String.empty_password)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            loginUseCase.execute(email, password)
                .onSuccess { response ->
                    if (response.user?.status == "Success") {
                        saveLoginInfo(response)

                        _isLoggedIn.value = true
                        onLoginSuccess()
                    } else {
                        onLoginFailed(Resources.String.localUnknown)
                        _isLoggedIn.value = false
                    }
                }
                .onError { error ->
                    println("Login error: $error")
                    onLoginFailed(error.toUiText())
                    _isLoggedIn.value = false
                }

            // 4) only clear loading here
            _isLoading.value = false
        }
    }

    private suspend fun saveLoginInfo(response: SuccessfulLoginResponse) {
        storage.writeValue(PrefKeys.clinicId, response.user!!.clinicId)
        storage.writeValue(PrefKeys.serverUrl, response.user!!.server_url)
        storage.writeValue(PrefKeys.token, response.token)
        storage.writeValue(PrefKeys.userId, response.user!!.id.toString())
    }

    private fun isValidEmail(email: String): Boolean =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
            .matches(email)
}