package org.example.hit.heal.oriantation.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.evaluation.Evaluation
import core.data.model.evaluation.EvaluationObject
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Error
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.PlayAudioUseCase
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys
import core.util.PrefKeys.clinicId
import core.util.PrefKeys.userId
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * ViewModel for the Orientation Test.
 * Manages state and handles business logic for the Orientation Test feature.
 */

class OrientationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadResultsUseCase: UploadTestResultsUseCase,
    private val playAudioUseCase: PlayAudioUseCase,
    private val api: AppApi,
    private val storage: Storage,
) : ViewModel() {

    private val _trialTest = MutableStateFlow<Evaluation?>(null)
    val trialTest: StateFlow<Evaluation?> = _trialTest.asStateFlow()
    private var trialData by mutableStateOf(TrialData())
    var drawnShape: ByteArray? = null
    var triangleOffset by mutableStateOf<Offset?>(null)
    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val isPlayingAudio = playAudioUseCase.isPlaying

    var droppedShapeId: Int? = null
        private set

    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(clinicId) ?: return@launch
            val patientId = storage.get(userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _trialTest.value = fetched
                    setDynamicIds(fetched.measurement_objects)
                }
                .onError { error ->
                    println("Error fetching evaluation: $error")
                }
        }
    }

    /**
     * Updates the measureObject IDs in trialData based on the fetched evaluation data.
     */
    private fun setDynamicIds(measurementObjects: List<EvaluationObject>) {
        val idMap = measurementObjects.associateBy { it.object_label }

        trialData.response.selectedNumber.measureObject = idMap["selected_number"]?.id ?: 0
        trialData.response.selectedSeasons.measureObject = idMap["selected_seasons"]?.id ?: 0
        trialData.response.isTriangleDragged.measureObject = idMap["isTriangleDragged"]?.id ?: 0
        trialData.response.isTriangleSizeChanged.measureObject = idMap["isTriangleSizeChanged"]?.id ?: 0
        trialData.response.xDrawing.measureObject = idMap["xDrawing"]?.id ?: 0
        trialData.response.imageUrl.measureObject = idMap["imageUrl"]?.id ?: 0
        trialData.response.healthLevel.measureObject = idMap["healthLevel"]?.id ?: 0
    }

    fun setDroppedShapeId(id: Int?) {
        droppedShapeId = id
    }

    fun onPlayAudio(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }

    fun updateNumber(number: Int) {
        trialData.response.selectedNumber.value = number.toString()
    }

    fun updateSeasons(seasons: List<String>) {
        trialData.response.selectedSeasons.value = seasons
    }

    fun updateDrawnShape(bitmap: ImageBitmap) {
        drawnShape = bitmap.toByteArray()
    }

    fun updateShapeResize(value: Boolean) {
        trialData.response.isTriangleSizeChanged.value = value
    }

    fun updateShapesDrag(result: Boolean) {
        trialData.response.isTriangleDragged.value = result
    }

    fun updateFeelingRate(rate: Int) {
        trialData.response.healthLevel.value = rate.toString()
    }

    fun getTestResults() = trialData.also {
        println("Test results: $it")
    }

    fun setTriangleOffset(offset: Offset) {
        triangleOffset = offset
    }

    fun sendToServer(
        onSuccess: (() -> Unit)? = null,
        onFailure: ((message: Error) -> Unit)? = null
    ) {
        if (drawnShape == null) {
            println("No drawn shape to upload.")
            onFailure?.invoke(DataError.Local.EMPTY_FILE)
            return
        }

        val version = 1
        val measurement = _trialTest.value?.id  ?: 20
        val date = getCurrentFormattedDateTime()
        val imgName = "orientation_shape.png"

        uploadScope.launch {
            try {
                val userId: String = storage.get(PrefKeys.userId)!!
                val clinicId: Int = when (val raw = storage.get(PrefKeys.clinicId)) {
                    is Int -> raw
                    is String -> raw.toIntOrNull() ?: 0
                    else -> 0
                }

                val imagePath = "clinics/$clinicId/patients/${userId}/measurements/$measurement/$date/$version/$imgName"

                uploadImageUseCase.execute(imagePath, drawnShape!!, clinicId, userId)
                    .onSuccess {
                        println("Successfully uploaded Image")

                        // Fill trial data
                        trialData.measurement = measurement
                        trialData.patientId = userId.toIntOrNull() ?: 0
                        trialData.date = date
                        trialData.clinicId = clinicId
                        trialData.response.imageUrl.value = imagePath

                        uploadResultsUseCase.execute(trialData)
                            .onSuccess {
                                withContext(Dispatchers.Main) { onSuccess?.invoke() }
                            }
                            .onError { error ->
                                withContext(Dispatchers.Main) { onFailure?.invoke(error) }
                            }
                    }
                    .onError { error ->
                        println("Upload failed before sending orientation results: $error")
                        withContext(Dispatchers.Main) { onFailure?.invoke(error) }
                    }
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                withContext(Dispatchers.Main) { onFailure?.invoke(DataError.Remote.UNKNOWN) }
            }
        }
    }
}