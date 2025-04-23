package org.example.hit.heal.cdt.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import org.example.hit.heal.cdt.data.network.CDTRequestBody
import org.example.hit.heal.cdt.data.network.CDTResults
import org.example.hit.heal.cdt.data.network.FileUploadReqBody
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.presentation.components.ClockTime
import org.example.hit.heal.cdt.utils.getCurrentFormattedDateTime
import org.example.hit.heal.cdt.utils.network.onError
import org.example.hit.heal.cdt.utils.network.onSuccess
import org.example.hit.heal.cdt.utils.toByteArray
import org.koin.core.component.KoinComponent

class ClockTestViewModel(
    private val repo: CDTRepository
) : ViewModel(),
    KoinComponent {
    // Clock test data
    private val cdtResults = MutableStateFlow(CDTResults())

    private val _drawTime = MutableStateFlow(ClockTime(12, 0))

    private val _isSecondStep = MutableStateFlow(false)

    // Draw clock test data
    private val _drawnPaths = MutableStateFlow<List<Path>>(emptyList())

    private var clockDrawing by mutableStateOf<ImageBitmap?>(null)

    // Time tracking
    private var startTime: Long = 0

    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    // Use asStateFlow to expose the clock times
    val drawTime = _drawTime.asStateFlow()

    val isSecondStep: StateFlow<Boolean> = _isSecondStep.asStateFlow()

    init {
        with(cdtResults.value) {
            hourChange1.value = 12
            minuteChange1.value = 0
            timeChange1.value = "12:00"

            hourChange2.value = 12
            minuteChangeUrl2.value = 0
            timeChange2.value = "12:00"

            imageUrl.value = "initialUrl.png"
        }
    }

    // Function to get CDT results
    fun getResults(): StateFlow<CDTResults> = cdtResults.asStateFlow()

    fun saveBitmap(image: ImageBitmap) {
        clockDrawing = image
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

    fun sendToServer() {
        println("sendToServer called") // A

        viewModelScope.launch(Dispatchers.IO) {
            println("Coroutine started") // B

            val imageBytes = clockDrawing?.toByteArray() ?: byteArrayOf()
            val clinicId = 1
            val userId = 1
            val measurement = 1234
            val date = getCurrentFormattedDateTime().replace(":", "-")
                .replace(" ", "-")

            if (imageBytes.isEmpty()) {
                println("ImageBitmap byte array is empty") // D
                return@launch
            }

            val imagePath = "clinics/$clinicId/patients/$userId/measurements/" +
                    "$measurement/$date/1/image.png"

            println("Calling uploadFile...") // E

            val uploadFileRes = repo.uploadFile(
                request = FileUploadReqBody(
                    file = imageBytes,
                    fileName = "file_name.png",
                    clinicId = clinicId,
                    userId = userId,
                    imageUrl = imagePath,
                    metaData = CDTRequestBody(
                        measurement = measurement,
                        patientId = userId,
                        date = date,
                        clinicId = clinicId,
                        test = cdtResults.value
                    )
                ),
                progressListener = { bytesSentTotal, contentLength ->
                    println("Progress: $bytesSentTotal / $contentLength")
                }
            )

            println("UploadFile returned") // F

            uploadFileRes
                .onSuccess { url ->
                    cdtResults.value.imageUrl.value = url

                    println("File uploaded successfully")
                    println("CDT-Results:")
                    val results = getResults().value
                    println("ImageUrl: ${results.imageUrl}")
                    println("timeChange1: ${results.timeChange1}")
                    println("hourChange1: ${results.hourChange1}")
                    println("minuteChange1: ${results.minuteChange1}")
                    println("timeChange2: ${results.timeChange2}")
                    println("hourChange2: ${results.hourChange2}")
                    println("minuteChangeUrl2: ${results.minuteChangeUrl2}")
                }
                .onError {
                    println("File upload failed")
                }
        }
    }


}
