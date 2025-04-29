package org.example.hit.heal.cdt.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.hit.heal.cdt.data.network.CDTResults
import org.example.hit.heal.cdt.data.network.MeasureObjectInt
import org.example.hit.heal.cdt.data.network.MeasureObjectString
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.presentation.components.ClockTime
import org.example.hit.heal.cdt.utils.getCurrentFormattedDateTime
import org.example.hit.heal.cdt.utils.network.onError
import org.example.hit.heal.cdt.utils.network.onSuccess
import org.koin.core.component.KoinComponent

class ClockTestViewModel(
    private val repo: CDTRepository
) : ViewModel(), KoinComponent {

    private val _currentClockSetTime= MutableStateFlow(ClockTime(12,0))
    private val _isSecondStep: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _drawnPaths: MutableStateFlow<List<Path>> = MutableStateFlow(emptyList())

    // Upload state
    private val _uploadState = MutableStateFlow<String?>(null)
    val uploadState: StateFlow<String?> = _uploadState.asStateFlow()

    // Time tracking properties
    private var startTime: Long = 0
    private var timeSpentDrawing
        get() = repo.getTimeSpentDrawing()
        set(value) = repo.setTimeSpentDrawing(value)
    private var timeSpentSettingFirstClock
        get() = repo.getTimeSpentSettingFirstClock()
        set(value) = repo.setTimeSpentSettingFirstClock(value)
    private var timeSpentSettingSecondClock
        get() = repo.getTimeSpentSettingSecondClock()
        set(value) = repo.setTimeSpentSettingSecondClock(value)

    /** Returns the current test results as a StateFlow */
    fun getResults(): StateFlow<CDTResults> = MutableStateFlow(repo.getCDTResults())

    /** Checks if a clock drawing exists */
    fun hasClockDrawing(): Boolean = repo.getClockDrawing() != null

    /** Saves the drawn clock as an ImageBitmap */
    fun saveBitmap(image: ImageBitmap) = repo.setClockDrawing(image)

    /** Updates the first clock time */
    fun updateFirstTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
         val newRes = getResults().value.copy(
            timeChange1 = MeasureObjectString(189, newTime.toString(), formattedDateTime),
            hourChange1 = MeasureObjectInt(190, newTime.hours, formattedDateTime),
            minuteChange1 = MeasureObjectInt(193, newTime.minutes, formattedDateTime)
        )
        repo.setCDTResults(newRes)
    }

    /** Updates the second clock time */
    fun updateSecondTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        val newRes= getResults().value.copy(
            timeChange2 = MeasureObjectString(191, newTime.toString(), formattedDateTime),
            hourChange2 = MeasureObjectInt(192, newTime.hours, formattedDateTime),
            minuteChangeUrl2 = MeasureObjectInt(194, newTime.minutes, formattedDateTime)
        )
        repo.setCDTResults(newRes)
    }

    /** Sets whether the current step is the second step */
    fun setSecondStep(isSecond: Boolean) {
        _isSecondStep.value = isSecond
    }

    fun getSecondStep(): StateFlow<Boolean> = _isSecondStep

    /** Updates the list of drawn paths */
    fun updateDrawnPaths(newPaths: List<Path>) {
        _drawnPaths.value = newPaths
    }

    /** Clears all drawn paths */
    fun clearDrawnPaths() {
        _drawnPaths.value = emptyList()
    }

    /** Starts the drawing timer */
    fun startDrawingTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the drawing timer and updates the total time spent */
    fun stopDrawingTimer() {
        timeSpentDrawing += (Clock.System.now().epochSeconds - startTime)
    }

    /** Starts the timer for setting the first clock */
    fun startSettingFirstClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the timer for setting the first clock */
    fun stopSettingFirstClockTimer() {
        timeSpentSettingFirstClock += (Clock.System.now().epochSeconds - startTime)
    }

    /** Starts the timer for setting the second clock */
    fun startSettingSecondClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the timer for setting the second clock */
    fun stopSettingSecondClockTimer() {
        timeSpentSettingSecondClock += (Clock.System.now().epochSeconds - startTime)
    }

    fun getCurrentClockSetTime() = _currentClockSetTime

    /** Sends the clock drawing and results to the server */
    fun sendToServer() {
        println("CDT Results: ${getResults().value}")
        _uploadState.value = "Uploading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.sendCDTRequest()
                    .onSuccess { response ->
                        _uploadState.value = "Success: $response"
                        println("Successfully sent results to server: $response")
                    }
                    .onError { error ->
                        _uploadState.value = "Failed: $error"
                        println("Failed to send results to server: $error")
                    }
            } catch (e: Exception) {
                _uploadState.value = "Failed: ${e.message}"
                println("Sending results to server has failed due to exception: ${e.message}")
            }
        }
    }
}