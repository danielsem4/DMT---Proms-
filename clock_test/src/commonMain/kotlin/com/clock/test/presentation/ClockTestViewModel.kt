package com.clock.test.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import com.clock.test.data.CDTResults
import com.clock.test.presentation.components.ClockTime
import com.clock.test.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock

class ClockTestViewModel : ViewModel() {
    // Clock test data
    private val cdtResults = MutableStateFlow(CDTResults())

    private val _drawTime = MutableStateFlow(ClockTime(12, 0))

    private val _isSecondStep = MutableStateFlow(false)

    // Draw clock test data
    private val _drawnPaths = MutableStateFlow<List<Path>>(emptyList())

    private var clockDrawing by mutableStateOf<ImageBitmap?>(null)

    init {
        with(cdtResults.value) {
            hourChange1.value = 12
            minuteChange1.value = 0
            timeChange1.value = "12:00"

            hourChange2.value = 12
            minuteChangeUrl2.value = 0
            timeChange2.value = "12:00"
        }
    }

    // Time tracking
    private var startTime: Long = 0

    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    // Use asStateFlow to expose the clock times
    val drawTime = _drawTime.asStateFlow()

    val isSecondStep: StateFlow<Boolean> = _isSecondStep.asStateFlow()

    // Function to get CDT results
    fun getResults(): StateFlow<CDTResults> = cdtResults.asStateFlow()

    fun saveBitmap(image: ImageBitmap) {
        clockDrawing = image
        //upload to server
        val url = "www.example.com/image.png"
        cdtResults.value.imageUrl.value = url
        cdtResults.value.imageUrl.dateTime = getCurrentFormattedDateTime()
    }

    // Functions to update clock test data
    fun updateFirstTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        val value = cdtResults.value
        value.timeChange1.dateTime = formattedDateTime
        value.timeChange1.value = newTime.toString()

        value.hourChange1.value = newTime.hours
        value.hourChange1.dateTime = formattedDateTime

        value.minuteChange1.value = newTime.minutes
        value.minuteChange1.dateTime = formattedDateTime
    }

    fun updateSecondTime(newTime: ClockTime) {
        val value = cdtResults.value
        val formattedDateTime = getCurrentFormattedDateTime()

        value.timeChange2.dateTime = formattedDateTime
        value.timeChange2.value = newTime.toString()

        value.hourChange2.value = newTime.hours
        value.hourChange2.dateTime = formattedDateTime

        value.minuteChangeUrl2.value = newTime.minutes
        value.minuteChange1.dateTime = formattedDateTime
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
        timeSpentDrawing += (Clock.System.now().epochSeconds - startTime)
    }

    // Start timing for setting the first clock
    fun startSettingFirstClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    // Stop timing for setting the first clock and save the time spent
    fun stopSettingFirstClockTimer() {
        timeSpentSettingFirstClock += (Clock.System.now().epochSeconds - startTime)
    }

    // Start timing for setting the second clock
    fun startSettingSecondClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    // Stop timing for setting the second clock and save the time spent
    fun stopSettingSecondClockTimer() {
        timeSpentSettingSecondClock += (Clock.System.now().epochSeconds - startTime)
    }

    // Get time spent in each activity
    fun getTimeSpentDrawing(): Long = timeSpentDrawing
    fun getTimeSpentSettingFirstClock(): Long = timeSpentSettingFirstClock
    fun getTimeSpentSettingSecondClock(): Long = timeSpentSettingSecondClock


}
