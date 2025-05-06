package org.example.hit.heal.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.local.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(), KoinComponent {

    // Expose login state
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // Optionally expose image URL (if you want to use it)
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    init {
        viewModelScope.launch {
            dataStoreRepository.readToken().collect { token ->
                _isLoggedIn.value = !token.isNullOrEmpty()
            }
        }
    }
}