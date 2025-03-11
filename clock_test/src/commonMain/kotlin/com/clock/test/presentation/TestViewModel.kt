package com.clock.test.presentation

import androidx.compose.ui.graphics.Path
import com.clock.test.presentation.components.ClockTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestViewModel {
    // Clock test data
    private val _currentTime = MutableStateFlow(ClockTime(12, 0))
    val currentTime: StateFlow<ClockTime> = _currentTime.asStateFlow()

    private val _isSecondStep = MutableStateFlow(false)
    val isSecondStep: StateFlow<Boolean> = _isSecondStep.asStateFlow()

    // Draw clock test data
    private val _drawnPaths = MutableStateFlow<List<Path>>(emptyList())

    // Functions to update clock test data
    fun updateTime(newTime: ClockTime) {
        _currentTime.value = newTime
    }

    fun setSecondStep(isSecond: Boolean) {
        _isSecondStep.value = isSecond
    }

    // Functions to update draw clock test data
    fun updateDrawnPaths(newPaths: List<Path>) {
        _drawnPaths.value = newPaths
    }

    fun clearDrawnPaths() {
        _drawnPaths.value = emptyList()
    }

    // Reset all data
    fun resetAll() {
        _currentTime.value = ClockTime(12, 0)
        _isSecondStep.value = false
        _drawnPaths.value = emptyList()
    }
} 