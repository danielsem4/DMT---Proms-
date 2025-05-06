package org.example.hit.heal.cdt.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.DataError
import core.domain.Error
import core.domain.onError
import core.domain.onSuccess
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.hit.heal.cdt.data.network.CDTResults
import org.example.hit.heal.cdt.data.network.MeasureObjectString
import org.example.hit.heal.cdt.domain.UploadCDTResultsUseCase
import org.example.hit.heal.cdt.domain.UploadImageUseCase
import org.example.hit.heal.cdt.presentation.components.ClockTime


class ClockTestViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadCDTResultsUseCase: UploadCDTResultsUseCase,
) : ViewModel() {

    private val _currentClockSetTime = MutableStateFlow(ClockTime(12, 0))
    private val _isSecondStep: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _drawnPaths: MutableStateFlow<List<Path>> = MutableStateFlow(emptyList())

    private var cdtResults = CDTResults()
    private var clockDrawing: ImageBitmap = ImageBitmap(1,1)
    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    // Time tracking properties
    private var startTime: Long = 0

    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /** Sends the clock drawing and results to the server */
    fun sendToServer(onSuccess: (() -> Unit)?, onFailure: ((message:Error) -> Unit)?) {
        if (clockDrawing.width == 1 && clockDrawing.height == 1) {
            println("❌ Failed to convert image: clockDrawing is not set.")
            onFailure?.invoke(DataError.Local.EMPTY_FILE)
            return
        }
        val imageByteArray = clockDrawing.toByteArray()
        println("image: $clockDrawing")

        val job = viewModelScope.coroutineContext[Job]
        println("🕵 scope job: $job, active=${job?.isActive}")

        val measurement = 21
        val patientId = 168
        val clinicId = 6
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "clock_image.png"
        val imagePath = "clinics/$clinicId/patients/$patientId/measurements/$measurement/" +
                "$date/$version/$imgName"

        println("✅ viewModelScope: $viewModelScope")

        uploadScope.launch  {
            println("Entering viewmodel scope - to upload results")
            // this will run even if viewModelScope is gone
            try {
                uploadImageUseCase.execute(imagePath, imageByteArray)
                    .onSuccess { uploadedImageUrl ->
                        println("Successfully uploaded Image: $uploadedImageUrl")
                        cdtResults.imageUrl = MeasureObjectString(
                            measureObject = 186,
                            value = uploadedImageUrl,
                            dateTime = getCurrentFormattedDateTime()
                        )
                        uploadCDTResultsUseCase.execute(cdtResults)
                            .onSuccess {
                                withContext(Dispatchers.Main){
                                    onSuccess?.invoke()
                                }
                            }
                            .onError { error ->
                                withContext(Dispatchers.Main){
                                    onFailure?.invoke(error)
                                }
                            }
                    }.onError { error ->
                        println("Upload failed before sending CDT: $error")
                        onFailure?.invoke(error)
                    }
            } catch (e: Exception) {
                println("🚨 Unexpected error: ${e.message}")
                onFailure?.invoke(DataError.Remote.UNKNOWN)
            }
        }
    }

    /** Saves the drawn clock as an ImageBitmap */
    fun saveBitmap(image: ImageBitmap){
        this.clockDrawing = image
    }

    /** Updates the first clock time */
    fun updateFirstTime(newTime: ClockTime) = updateFirstClockTime(newTime)

    /** Updates the second clock time */
    fun updateSecondTime(newTime: ClockTime) = updateSecondClockTime(newTime)

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

    fun updateFirstClockTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        with(cdtResults) {
            timeChange1.value = newTime.toString()
            timeChange1.dateTime = formattedDateTime

            hourChange1.value = newTime.hours
            hourChange1.dateTime = formattedDateTime

            minuteChange1.value = newTime.minutes
            minuteChange1.dateTime = formattedDateTime
        }
    }

    fun updateSecondClockTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        with(cdtResults) {
            timeChange2.value = newTime.toString()
            timeChange2.dateTime = formattedDateTime

            hourChange2.value = newTime.hours
            hourChange2.dateTime = formattedDateTime

            minuteChangeUrl2.value = newTime.minutes
            minuteChangeUrl2.dateTime = formattedDateTime
        }
    }
}