package org.example.hit.heal.oriantation.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
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
import dmt_proms.oriantation.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
data class OrientationResults(
    val xDrawing: String = "",
    val selected_number: String = "",
    val selected_seasons: String = "",
    val isTriangleSizeChanged: Boolean = false,
    val isTriangleDragged: Boolean = false,
    val healthLevel: String = ""
)

@Serializable
data class OrientationRequestBody(
    val measurement: Int,
    val patient_id: String,
    val date: String,
    val clinicId: Int,
    val test: OrientationResults
)

class OrientationTestViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadCDTResultsUseCase: UploadTestResultsUseCase,
    private val api: AppApi,
    private val storage: Storage,
): ViewModel() {
    var trialData by mutableStateOf(TrialData())
        private set
    var drawnShape: ByteArray? = null
    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun updateNumber(number: Int) {
        trialData.response.selectedNumber.value = number.toString()
    }

    fun updateSeason(season: String) {
        trialData.response.selectedSeasons.value = listOf(season)
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
        val measurement = 20 // <-- use your measurement ID
        val date = getCurrentFormattedDateTime()
        val imgName = "orientation_shape.png"

        uploadScope.launch {
            try {
                val userId = storage.get(PrefKeys.userId)!!
                val clinicId = storage.get(PrefKeys.clinicId)!!

                val imagePath = "clinics/$clinicId/patients/$userId/measurements/$measurement/" +
                        "$date/$version/$imgName"

                uploadImageUseCase.execute(imagePath, drawnShape!!, clinicId, userId)
                    .onSuccess {
                        println("Successfully uploaded Image")

                        val results = OrientationResults(
                            xDrawing = imagePath,
                            selected_number = trialData.response.selectedNumber.value ,
                            selected_seasons = trialData.response.selectedSeasons.value.firstOrNull() ?: "",
                            isTriangleSizeChanged = trialData.response.isTriangleSizeChanged.value,
                            isTriangleDragged = trialData.response.isTriangleDragged.value,
                            healthLevel = trialData.response.healthLevel.value
                        )

                        val body = OrientationRequestBody(
                            measurement = measurement,
                            patient_id = userId,
                            date = date,
                            clinicId = clinicId,
                            test = results
                        )

                        uploadCDTResultsUseCase.execute(body, OrientationRequestBody.serializer())
                            .onSuccess {
                                withContext(Dispatchers.Main) {
                                    onSuccess?.invoke()
                                }
                            }
                            .onError { error ->
                                withContext(Dispatchers.Main) {
                                    onFailure?.invoke(error)
                                }
                            }
                    }
                    .onError { error ->
                        println("Upload failed before sending orientation results: $error")
                        onFailure?.invoke(error)
                    }
            } catch (e: Exception) {
                println("🚨Unexpected error: ${e.message}")
                onFailure?.invoke(DataError.Remote.UNKNOWN)
            }
        }
    }
}