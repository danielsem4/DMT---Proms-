package org.example.hit.heal.cdt.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectString
import core.data.model.cdt.CDTRequestBody
import core.data.model.cdt.CDTResults
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Error
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import org.example.hit.heal.cdt.data.ClockTime
import kotlin.time.ExperimentalTime

class ClockTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadCDTResultsUseCase: UploadTestResultsUseCase,
    private val api: AppApi,
    private val storage: Storage,
) : ViewModel() {

    // the clock test as we get it from the server
    private val _clockTest = MutableStateFlow<Evaluation?>(null)
    val clockTest: StateFlow<Evaluation?> = _clockTest.asStateFlow()

    private val _savedClockDrawing = MutableStateFlow<ImageBitmap?>(null)
    val savedClockDrawing: StateFlow<ImageBitmap?> = _savedClockDrawing.asStateFlow()

    private val _currentClockSetTime = MutableStateFlow(ClockTime(12, 0))
    private val _isSecondStep: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _drawnPaths: MutableStateFlow<List<Path>> = MutableStateFlow(emptyList())

    private val _circlePerfection = MutableStateFlow("")
    val circlePerfection: StateFlow<String> = _circlePerfection.asStateFlow()

    private val _numbersSequence = MutableStateFlow("")
    val numbersSequence: StateFlow<String> = _numbersSequence.asStateFlow()

    private val _handsPosition = MutableStateFlow("")
    val handsPosition: StateFlow<String> = _handsPosition.asStateFlow()

    // loading state for UI feedback
    private val _isSendingData = MutableStateFlow(false)
    val isSendingData: StateFlow<Boolean> = _isSendingData.asStateFlow()

    private var cdtResults = CDTResults()
    private var clockDrawing: ImageBitmap = ImageBitmap(1, 1)
    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    // Time tracking
    private var startTime: Long = 0

    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private fun MeasureObjectString.setWithDate(newValue: String) {
        value = newValue
        dateTime = getCurrentFormattedDateTime()
    }

    fun setCirclePerfection(selection: String) {
        _circlePerfection.value = selection
        cdtResults.circle_perfection.setWithDate(selection)
    }

    fun setNumbersSequence(selection: String) {
        _numbersSequence.value = selection
        cdtResults.numbers_sequence.setWithDate(selection)
    }

    fun setHandsPosition(selection: String) {
        _handsPosition.value = selection
        cdtResults.hands_position.setWithDate(selection)
    }

    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId: Int = storage.get(PrefKeys.clinicId) ?: return@launch
            val patientId: Int = storage.get(PrefKeys.userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _clockTest.value = fetched
                    println("fetched evaluation: $fetched")
                }
                .onError { error ->
                    println("Error fetching evaluation: $error")
                }
        }
    }

    /** Sends the clock drawing and results to the server */
    fun sendToServer(onSuccess: (() -> Unit)?, onFailure: ((message: Error) -> Unit)?) {
        if (clockDrawing.width == 1 || clockDrawing.height == 1) {
            println("Failed to convert image: clockDrawing is not set. " +
                    "Size: ${clockDrawing.width}x${clockDrawing.height}")
            onFailure?.invoke(DataError.Local.EMPTY_FILE)
            return
        }
        val imageByteArray = clockDrawing.toByteArray()

        val version = 1
        val measurement = clockTest.value?.id ?: 17
        val date = getCurrentFormattedDateTime()
        val imgName = "clock_image.png"

        uploadScope.launch {
            _isSendingData.value = true
            try {
                val userId = storage.get(PrefKeys.userId)!!               // expected String
                val clinicId = storage.get(PrefKeys.clinicId)!!           // expected Int

                val imagePath =
                    "clinics/$clinicId/patients/$userId/measurements/$measurement/$date/$version/$imgName"

                // First, upload the image
                uploadImageUseCase.execute(imagePath, imageByteArray, clinicId, userId)
                    .onSuccess {
                        println("Successfully uploaded Image")
                        cdtResults.imageUrl = MeasureObjectString(
                            measureObject = 186, value = imagePath, dateTime = date
                        )

                        val body = CDTRequestBody(
                            measurement = measurement,
                            patientId = userId,
                            date = date,
                            clinicId = clinicId,
                            test = cdtResults
                        )

                        // Then upload CDT results
                        uploadCDTResultsUseCase.execute(body, CDTRequestBody.serializer())
                            .onSuccess {
                                println("Successfully uploaded CDT results")
                                withContext(Dispatchers.Main) { onSuccess?.invoke() }
                            }
                            .onError { error ->
                                println("Upload failed after sending CDT: $error")
                                withContext(Dispatchers.Main) { onFailure?.invoke(error) }
                            }
                    }
                    .onError { error ->
                        println("Upload failed before sending CDT: $error")
                        withContext(Dispatchers.Main) { onFailure?.invoke(error) }
                    }
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                withContext(Dispatchers.Main) { onFailure?.invoke(DataError.Remote.UNKNOWN) }
            } finally {
                _isSendingData.value = false
            }
        }
    }

    /** Saves the drawn clock as an ImageBitmap */
    fun saveBitmap(image: ImageBitmap) {
        this.clockDrawing = image
        _savedClockDrawing.value = image
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
    @OptIn(ExperimentalTime::class)
    fun startDrawingTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the drawing timer and updates the total time spent */
    @OptIn(ExperimentalTime::class)
    fun stopDrawingTimer() {
        timeSpentDrawing += (Clock.System.now().epochSeconds - startTime)
    }

    /** Starts the timer for setting the first clock */
    @OptIn(ExperimentalTime::class)
    fun startSettingFirstClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the timer for setting the first clock */
    @OptIn(ExperimentalTime::class)
    fun stopSettingFirstClockTimer() {
        timeSpentSettingFirstClock += (Clock.System.now().epochSeconds - startTime)
    }

    /** Starts the timer for setting the second clock */
    @OptIn(ExperimentalTime::class)
    fun startSettingSecondClockTimer() {
        startTime = Clock.System.now().epochSeconds
    }

    /** Stops the timer for setting the second clock */
    @OptIn(ExperimentalTime::class)
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
