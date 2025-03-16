package com.clock.test.presentation

import androidx.compose.ui.graphics.Path
import com.clock.test.presentation.components.ClockTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestViewModel {
    // Clock test data
    private val firstClockTime = MutableStateFlow(ClockTime(12, 0))
    private val secondClockTime = MutableStateFlow(ClockTime(12, 0))
    private val _drawTime = MutableStateFlow(ClockTime(12, 0))

    private val _isSecondStep = MutableStateFlow(false)
    // Draw clock test data
    private val _drawnPaths = MutableStateFlow<List<Path>>(emptyList())

    // Use asStateFlow to expose the clock times
    val drawTime = _drawTime.asStateFlow()
    val firstClockTimeState: StateFlow<ClockTime> = firstClockTime.asStateFlow()
    val secondClockTimeState: StateFlow<ClockTime> = secondClockTime.asStateFlow()
    val isSecondStep: StateFlow<Boolean> = _isSecondStep.asStateFlow()
    val drawnPaths: StateFlow<List<Path>> = _drawnPaths.asStateFlow() // Expose drawn paths

    // Functions to update clock test data
    fun updateFirstTime(newTime: ClockTime) {
        firstClockTime.value = newTime
    }

    fun updateSecondTime(newTime: ClockTime) {
        secondClockTime.value = newTime
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
}