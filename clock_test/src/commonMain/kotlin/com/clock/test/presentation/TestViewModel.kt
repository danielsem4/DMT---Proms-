package com.clock.test.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import com.clock.test.presentation.components.ClockTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock

class TestViewModel {
    // Clock test data
    private val firstClockTime = MutableStateFlow(ClockTime(12, 0))
    private val secondClockTime = MutableStateFlow(ClockTime(12, 0))
    private val _drawTime = MutableStateFlow(ClockTime(12, 0))

    private val _isSecondStep = MutableStateFlow(false)
    // Draw clock test data
    private val _drawnPaths = MutableStateFlow<List<Path>>(emptyList())

    var clockDrawing by mutableStateOf<ImageBitmap?>(null)
        private set

    fun saveBitmap(image: ImageBitmap) {
        clockDrawing = image
    }

    // Time tracking
    private var startTime: Long = 0
    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

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

    // Start timing for drawing
    fun startDrawingTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    // Stop timing for drawing and save the time spent
    fun stopDrawingTimer() {
        timeSpentDrawing += (Clock.System.now().epochSeconds- startTime)
    }

    // Start timing for setting the first clock
    fun startSettingFirstClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    // Stop timing for setting the first clock and save the time spent
    fun stopSettingFirstClockTimer() {
        timeSpentSettingFirstClock += (Clock.System.now().epochSeconds- startTime)
    }

    // Start timing for setting the second clock
    fun startSettingSecondClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    // Stop timing for setting the second clock and save the time spent
    fun stopSettingSecondClockTimer() {
        timeSpentSettingSecondClock += (Clock.System.now().epochSeconds- startTime)
    }

    // Get time spent in each activity
    fun getTimeSpentDrawing(): Long = timeSpentDrawing
    fun getTimeSpentSettingFirstClock(): Long = timeSpentSettingFirstClock
    fun getTimeSpentSettingSecondClock(): Long = timeSpentSettingSecondClock
}