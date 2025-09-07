package org.example.hit.heal.oriantation.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable



class OrientationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadResultsUseCase: UploadTestResultsUseCase,
    private val playAudioUseCase: PlayAudioUseCase,
    private val api: AppApi,
    private val storage: Storage,
): ViewModel() {
    private var trialData by mutableStateOf(TrialData())
    var drawnShape: ByteArray? = null
    var triangleOffset by mutableStateOf<Offset?>(null)
    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val isPlayingAudio = playAudioUseCase.isPlaying

    var droppedShapeId: Int? = null
        private set

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
        val measurement = 20
        val date = getCurrentFormattedDateTime()
        val imgName = "orientation_shape.png"

        uploadScope.launch {
            try {
                val userId: String = storage.get(PrefKeys.userId)!!  // String
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

                        uploadResultsUseCase.execute(trialData, TrialData.serializer())
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
                println("🚨Unexpected error: ${e.message}")
                withContext(Dispatchers.Main) { onFailure?.invoke(DataError.Remote.UNKNOWN) }
            }
        }
    }
}