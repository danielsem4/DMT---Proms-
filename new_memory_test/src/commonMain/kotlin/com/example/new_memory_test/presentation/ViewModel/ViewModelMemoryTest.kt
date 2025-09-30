package com.example.new_memory_test.presentation.ViewModel

import ActivityPlacementJson
import ActivityPlacementValue
import MemoryData
import MemoryQuestionPart1Json
import MemoryQuestionPart1Value
import MemoryQuestionPartNJson
import MemoryQuestionPartNValue
import SimpleMeasureBoolean
import SimpleMeasureString
import WrappedActivityPlacement
import WrappedMemoryQuestionPart1
import WrappedMemoryQuestionPart2
import WrappedMemoryQuestionPart3
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_memory_test.presentation.screens.RoomScreen.data.DataItem
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.BitmapToUploadUseCase
import core.domain.use_case.PlayAudioUseCase
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.example.hit.heal.core.presentation.components.SlotState
import org.jetbrains.compose.resources.DrawableResource
import kotlin.math.absoluteValue

class ViewModelMemoryTest(
    private val playAudioUseCase: PlayAudioUseCase,
    private val bitmapToUploadUseCase: BitmapToUploadUseCase,
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val api: AppApi,
    private val storage: Storage

) : ViewModel() {
    var txtMemoryPage by mutableStateOf(1)
    private val _placedItems = mutableStateListOf<DataItem>()
    val placedItems: List<DataItem> get() = _placedItems.toList()
    private var result by mutableStateOf(MemoryData())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uploadStatus = MutableStateFlow<Result<Unit>?>(null)
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus.asStateFlow()

    private var shuffledItems: List<Triple<Int, DrawableResource, String>>? = null
    val agendaMap = mutableStateOf<Map<String, String>>(emptyMap())

    private val firstRoundItems = arrayListOf<DataItem>()

    private val _memoryTest = MutableStateFlow<Evaluation?>(null)
    val memoryTest: StateFlow<Evaluation?> = _memoryTest.asStateFlow()

    // State for images captured during the test.
    var image1 = mutableStateOf<Array<ImageBitmap?>>(emptyArray())
    var image2 = mutableStateOf<Array<ImageBitmap?>>(emptyArray())
    var image3 = mutableStateOf<Array<ImageBitmap?>>(emptyArray())
    var imageUrl = mutableStateOf<Array<ImageBitmap?>>(emptyArray())

    var timeForImage1 = mutableStateOf<String?>(null)
    var timeForImage2 = mutableStateOf<String?>(null)
    var timeForImage3 = mutableStateOf<String?>(null)
    var timeUrl = mutableStateOf<String?>(null)

    var pageNumForImage1 = mutableStateOf<Int?>(null)
    var pageNumForImage2 = mutableStateOf<Int?>(null)
    var pageNumForImage3 = mutableStateOf<Int?>(null)
    var pageNumForUrl = mutableStateOf<Int?>(null)

    private val _droppedState = MutableStateFlow<Map<String, SlotState>>(emptyMap())
    val droppedState = _droppedState.asStateFlow()
    var imagesCounter = mutableStateOf(0)
    var rawUserRating: Float? = null


    /**
     * Fetches the evaluation details from the server, which includes the all-important measurement object IDs.
     */
    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(PrefKeys.clinicId) ?: return@launch
            val patientId = storage.get(PrefKeys.userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _memoryTest.value = fetched
                    println("Evaluation data loaded successfully.")
                }
                .onError { error ->
                    println("Error fetching evaluation: $error")
                }
        }
    }

    /**
     * Finds the measurement object ID for a given label from the loaded evaluation data.
     */
    private fun findEvaluationObjectId(label: String): Int? {
        val id = _memoryTest.value?.measurement_objects?.find { it.object_label == label }?.id
        if (id == null) {
            println("Warning: Could not find dynamic ID for label: '$label'")
        }
        return id
    }

    suspend fun onPlayAudio(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }

    fun recordInactivity() {
        val id = findEvaluationObjectId("notificationsCount") ?: 165
        result.notificationsCount.add(
            SimpleMeasureString(
                dateTime = getCurrentFormattedDateTime(),
                measureObject = id,
                value = "User inactive for 1 minute"
            )
        )
    }

    fun setSuccessRateAfter(answer: String, date: String) {
        val id = findEvaluationObjectId("successRateAfter") ?: 166
        result.successRateAfter.add(
            SimpleMeasureString(
                dateTime = date,
                measureObject = id,
                value = answer
            )
        )
    }

    fun setPhoneResult(answer: Boolean) {
        val id = findEvaluationObjectId("PhoneCallResult") ?: 170
        result.PhoneCallResult.add(
            SimpleMeasureBoolean(
                dateTime = getCurrentFormattedDateTime(),
                measureObject = id,
                value = answer
            )
        )
    }

    private fun collectPlannedActivities(date: String): List<WrappedActivityPlacement> {
        val activityId = findEvaluationObjectId("activitiesPlaced") ?: 570
        return agendaMap.value.map { (slotId, activityIdValue) ->
            val parts = slotId.split("_")
            val day = parts.getOrNull(1) ?: "unknown"
            val hour = parts.getOrNull(3) ?: "unknown"
            WrappedActivityPlacement(
                ActivityPlacementJson(
                    dateTime = date,
                    measureObject = activityId,
                    value = ActivityPlacementValue(
                        activity = activityIdValue,
                        day = day,
                        time = hour
                    )
                )
            )
        }
    }

    private fun updateMemoryTestResults(item: DataItem, pageNumber: Int, date: String) {
        when (pageNumber) {
            2 -> { // Corresponds to Stage 1
                firstRoundItems.add(item)
                val measureObjectId = findEvaluationObjectId("MemoryQuestionPart1") ?: 571
                result.MemoryQuestionPart1.add(
                    WrappedMemoryQuestionPart1(
                        MemoryQuestionPart1Json(
                            dateTime = date,
                            measureObject = measureObjectId,
                            value = MemoryQuestionPart1Value(
                                item = item.name,
                                place = item.zone?.displayName ?: "unknown",
                                room = item.room?.displayName?.key ?: "unknown"
                            )
                        )
                    )
                )
            }

            4, 6 -> { // Corresponds to Stages 2 and 3
                val isStage2 = pageNumber == 4
                val label = "MemoryQuestionPart${if (isStage2) "2" else "3"}"
                val fallbackId = if (isStage2) 572 else 573
                val measureObjectId = findEvaluationObjectId(label) ?: fallbackId
                val original = firstRoundItems.find { it.id == item.id }

                val score =
                    if (original != null && item.room == original.room && item.zone == original.zone && positionsAreClose(
                            item.position,
                            original.position,
                            70f
                        )
                    ) {
                        "1"
                    } else {
                        "0"
                    }

                val value = MemoryQuestionPartNValue(
                    item = item.name,
                    place = item.zone?.displayName ?: "unknown",
                    placeGrade = score,
                    room = item.room?.displayName?.key ?: "unknown"
                )

                val json = MemoryQuestionPartNJson(
                    dateTime = date,
                    measureObject = measureObjectId,
                    value = value
                )

                if (isStage2) {
                    result.MemoryQuestionPart2.add(WrappedMemoryQuestionPart2(json))
                } else {
                    result.MemoryQuestionPart3.add(WrappedMemoryQuestionPart3(json))
                }
            }
        }
    }

    private fun positionsAreClose(pos1: Offset?, pos2: Offset?, tolerance: Float): Boolean {
        if (pos1 == null || pos2 == null) return false
        val dx = (pos1.x - pos2.x).absoluteValue
        val dy = (pos1.y - pos2.y).absoluteValue
        return dx <= tolerance && dy <= tolerance
    }

    fun getItemsForPage(
        page: Int,
        allItems: List<Triple<Int, DrawableResource, String>>
    ): List<Triple<Int, DrawableResource, String>> {
        return when (page) {
            2 -> allItems.take(7)
            4 -> {
                if (shuffledItems == null) {
                    shuffledItems = allItems.shuffled().take(12)
                }
                shuffledItems!!
            }

            6 -> shuffledItems ?: allItems.take(12)
            else -> allItems
        }
    }

    fun saveItemForRound(item: DataItem, pageNumber: Int) {
        val date = getCurrentFormattedDateTime()
        updateMemoryTestResults(item, pageNumber, date)
        saveItem(item)
    }

    private fun saveItem(item: DataItem) {
        val index = _placedItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            _placedItems[index] = item
        } else {
            _placedItems.add(item)
        }
    }

    fun removeItem(itemId: Int) {
        _placedItems.removeAll { it.id == itemId }
    }

    fun clearPlacedItems() {
        _placedItems.clear()
    }

    fun setPage(page: Int) {
        txtMemoryPage = page
    }

    fun uploadEvaluationResults() {
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val date = getCurrentFormattedDateTime()
                result.clinicId = storage.get(PrefKeys.clinicId) ?: 0
                result.patientId = storage.get(PrefKeys.userId)?.toIntOrNull() ?: 0
                result.date = date
                result.measurement = _memoryTest.value?.id ?: 20
                result.activitiesPlaced = ArrayList(collectPlannedActivities(date))

                rawUserRating?.let { setSuccessRateAfter(it.toString(), date) }

                val uploadResult = uploadTestResultsUseCase.execute(result)

                uploadResult.onSuccess {
                    _uploadStatus.value = Result.success(Unit)
                }.onError { error ->
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                }
            } catch (e: Exception) {
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveUploadedImageUrl(page: Int?, uploadedUrl: String, date: String) {
        val (label, fallbackId) = when (page) {
            2 -> "images1" to 195
            4 -> "images2" to 197
            6 -> "images3" to 199
            5 -> "imageUrl" to 153
            else -> return
        }

        val measureObjectId = findEvaluationObjectId(label) ?: fallbackId
        val imageEntry = SimpleMeasureString(date, measureObjectId, uploadedUrl)

        when (page) {
            2 -> result.images1.add(imageEntry)
            4 -> result.images2.add(imageEntry)
            6 -> result.images3.add(imageEntry)
            5 -> result.imageUrl.add(imageEntry)
        }
    }

    private suspend fun uploadImage(
        bitmap: ImageBitmap,
        date: String,
        page: Int?,
        imageNum: String
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) return

        val imageByteArray = bitmap.toByteArray()
        try {
            val userId = storage.get(PrefKeys.userId)!!
            val clinicId = storage.get(PrefKeys.clinicId)!!
            val measurement = _memoryTest.value?.id ?: 20
            val imagePath =
                bitmapToUploadUseCase.buildPath(clinicId, userId, measurement, date, imageNum)

            val uploadResult =
                uploadImageUseCase.execute(imagePath, imageByteArray, clinicId, userId)

            uploadResult.onSuccess {
                imagesCounter.value++
                saveUploadedImageUrl(page, imagePath, date)
            }.onError { error ->
                _uploadStatus.value = Result.failure(Exception(error.toString()))
            }
        } catch (e: Exception) {
            println("Exception during image upload: ${e.message}")
        }
    }

    fun uploadAllImages() {
        viewModelScope.launch(Dispatchers.IO) {
            var imageNum = 1
            supervisorScope {
                val jobs = mutableListOf<Deferred<Unit>>()
                image1.value.filterNotNull().forEach {
                    jobs.add(async {
                        uploadImage(
                            it,
                            timeForImage1.value!!,
                            pageNumForImage1.value,
                            imageNum.toString()
                        )
                    })
                    imageNum++
                }
                image2.value.filterNotNull().forEach {
                    jobs.add(async {
                        uploadImage(
                            it,
                            timeForImage2.value!!,
                            pageNumForImage2.value,
                            imageNum.toString()
                        )
                    })
                    imageNum++
                }
                image3.value.filterNotNull().forEach {
                    jobs.add(async {
                        uploadImage(
                            it,
                            timeForImage3.value!!,
                            pageNumForImage3.value,
                            imageNum.toString()
                        )
                    })
                    imageNum++
                }
                imageUrl.value.filterNotNull().forEach {
                    jobs.add(async {
                        uploadImage(
                            it,
                            timeUrl.value!!,
                            pageNumForUrl.value,
                            imageNum.toString()
                        )
                    })
                    imageNum++
                }
                jobs.awaitAll()
                uploadEvaluationResults()
            }
        }
    }

    fun reset() {
        txtMemoryPage = 1
        _placedItems.clear()
        result = MemoryData()
        _uploadStatus.value = null
        _memoryTest.value = null
        firstRoundItems.clear()
        rawUserRating = null
        agendaMap.value = emptyMap()
        image1.value = emptyArray()
        image2.value = emptyArray()
        image3.value = emptyArray()
        imageUrl.value = emptyArray()
        timeForImage1.value = null
        timeForImage2.value = null
        timeForImage3.value = null
        timeUrl.value = null
        pageNumForImage1.value = null
        pageNumForImage2.value = null
        pageNumForImage3.value = null
        pageNumForUrl.value = null
        imagesCounter.value = 0
        _isLoading.value = false
        _droppedState.value = emptyMap()
        shuffledItems = null
    }

    fun updateDroppedState(slotId: String, slotState: SlotState?) {
        val current = _droppedState.value.toMutableMap()
        if (slotState == null) {
            current.remove(slotId)
        } else {
            current[slotId] = slotState
        }
        _droppedState.value = current
    }
}