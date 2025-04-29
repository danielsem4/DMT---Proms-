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
import org.example.hit.heal.cdt.data.network.SendCDTUseCase
import org.example.hit.heal.cdt.data.network.TransactionResult
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.presentation.components.ClockTime
import org.koin.core.component.KoinComponent

class ClockTestViewModel(
    private val repo: CDTRepository,
    private val sendCDTUseCase: SendCDTUseCase
) : ViewModel(), KoinComponent {

    private val _currentClockSetTime= MutableStateFlow(ClockTime(12,0))
    private val _isSecondStep: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _drawnPaths: MutableStateFlow<List<Path>> = MutableStateFlow(emptyList())

    // Upload state
    private val _uploadState = MutableStateFlow<String?>(null)
    var uploadState: StateFlow<String?> = _uploadState.asStateFlow()

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
    fun updateFirstTime(newTime: ClockTime) = repo.updateFirstClockTime(newTime)

    /** Updates the second clock time */
    fun updateSecondTime(newTime: ClockTime) = repo.updateSecondClockTime(newTime)

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

        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val result = sendCDTUseCase()) { // Use UseCase, not repo directly
                    is TransactionResult.Success -> {
                        val msg = "Successfully uploaded CDT data: ${result.data}"
                        _uploadState.value = msg
                        println(msg)
                    }
                    is TransactionResult.UploadFailure -> {
                        _uploadState.value = "Upload failed: ${result.error}"
                        println("Upload failed before sending CDT: ${result.error}")
                    }
                    is TransactionResult.SendFailure -> {
                        _uploadState.value = "Sending CDT failed: ${result.error}"
                        println("Failed while sending CDT to server: ${result.error}")
                    }
                }
            } catch (e: Exception) {
                _uploadState.value = "Failed with exception: ${e.message}"
                println("Unexpected exception: ${e.message}")
            }
        }
    }

}