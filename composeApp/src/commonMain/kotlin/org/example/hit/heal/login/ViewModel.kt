package org.example.hit.heal.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                authRepository.login(email, password)
                _loginState.value = LoginState.Success
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            _userData.value = userRepository.getUserData()
        }
    }
}
