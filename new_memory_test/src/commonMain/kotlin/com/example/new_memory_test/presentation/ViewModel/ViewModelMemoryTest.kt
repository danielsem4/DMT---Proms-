package com.example.new_memory_test.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_memory_test.data.model.ActivityPlacement
import com.example.new_memory_test.data.model.MemoryData
import com.example.new_memory_test.data.model.MemoryQuestionPart1
import com.example.new_memory_test.data.model.MemoryQuestionPart2
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.data.DataItem
import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectString
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Error
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.domain.use_case.BitmapToUploadUseCase
import core.domain.use_case.PlayAudioUseCase
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys
import core.util.PrefKeys.clinicId
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import dmt_proms.new_memory_test.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.hit.heal.core.presentation.Resources.String.date
import org.jetbrains.compose.resources.DrawableResource
import kotlin.math.absoluteValue
import kotlin.onFailure

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

    private val _initialItemIds = mutableListOf<Int>()

    private var result: MemoryData = MemoryData()
    private val _uploadStatus = MutableStateFlow<Result<Unit>?>(null) // Need check
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus

    private val _memoryTest = MutableStateFlow<Evaluation?>(null)
    val memoryTest: StateFlow<Evaluation?> = _memoryTest.asStateFlow()

    private val part1List = arrayListOf<MemoryQuestionPart1>()
    private val part2List = arrayListOf<MemoryQuestionPart2>()
    private val part3List = arrayListOf<MemoryQuestionPart2>()

    private val firstRoundItems = arrayListOf<DataItem>()
    private val secondRoundItems = arrayListOf<DataItem>()
    private val thirdRoundItems = arrayListOf<DataItem>()
    //call
    var callResult  = MeasureObjectBoolean()

    //notification


    //RoomsData


    //audio
    suspend fun onPlayAudio(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }

    //Time
    fun recordInactivity() {
       var  notificationResult = MeasureObjectString(
            measureObject = 165,
            value = "User inactive for 1 minute",
            dateTime = getCurrentFormattedDateTime()
        )
        result.notificationsCount = listOf(notificationResult)
    }


    //Choose reminder option in custom dialog
    val selectedReminderOption = mutableStateOf<String?>(null)

    //successRateAfter
    val userRating = mutableStateListOf<MeasureObjectString>()
    val agendaMap = mutableStateOf<Map<String, String>>(emptyMap())

    fun setSuccessRateAfter(answer: String) {
        userRating.add(
            MeasureObjectString(
                measureObject = 166,
                value = answer,
                dateTime = getCurrentFormattedDateTime()
            )
        )
    }

    //Call result
    fun setPhoneResult(answer: Boolean) {
        callResult = MeasureObjectBoolean(
            measureObject = 170,
            value = answer,
            dateTime = getCurrentFormattedDateTime()
        )

        println("results object: $result")
    }

    //ScheduleScreen
    fun collectPlannedActivities(): List<ActivityPlacement> {
        return agendaMap.value.map { (slotId, activityId) ->
            val parts = slotId.split("_") // ["day", "3", "hour", "10"]
            val day = parts.getOrNull(1) ?: "unknown"
            val hour = parts.getOrNull(3) ?: "unknown"

            ActivityPlacement(
                activity = MeasureObjectString(

                    measureObject = 150,
                    value = activityId
                ),
                day = MeasureObjectString(
                    measureObject = 151,
                    value = day
                ),
                time = MeasureObjectString(
                    measureObject = 216,
                    value = hour
                )
            )
        }
    }


//Room Screen
    fun DataItem.toMemoryQuestionPart1(): MemoryQuestionPart1 {
        return MemoryQuestionPart1(
            item = MeasureObjectString(
                measureObject = 157,
                value = id.toString()
            ),
            place = MeasureObjectString(
                measureObject = 158,
                value = zone?.displayName ?: "unknown"
            )
        )
    }
    fun convertToMemoryQuestionPart2List(
        currentRound: List<DataItem>,
        originalRound: List<DataItem>,
        part: Int
    ): List<MemoryQuestionPart2> {
        val tolerance = 15f
        return currentRound.map { current ->
            val original = originalRound.find { it.id == current.id }
            current.toMemoryQuestionPart2(original, tolerance, part)
        }
    }
    fun DataItem.toMemoryQuestionPart2(original: DataItem?, tolerance: Float = 15f, part : Int ): MemoryQuestionPart2 {
        val score = if (
            original != null &&
            room == original.room &&
            zone == original.zone &&
            positionsAreClose(position, original.position, tolerance)
        ) "1" else "0"

        return MemoryQuestionPart2(
            item = MeasureObjectString(
                measureObject = 161,
                value = id.toString()
            ),
            place = MeasureObjectString(
                measureObject = 162,
                value = zone?.displayName ?: "unknown"
            ),
            placeGrade = MeasureObjectString(
                measureObject = 163,
                value = score
            ),
            room = MeasureObjectString(
                measureObject = 164,
                value = room?.displayName?.key ?: "unknown"
            )
        )
    }
    private fun compareRounds(
        baseline: List<DataItem>,
        toCheck: List<DataItem>,
        tolerance: Float
    ): Int {
        var score = 0

        for (item in toCheck) {
            val original = baseline.find { it.id == item.id }

            if (original != null) {
                val sameRoom = original.room == item.room
                val sameZone = original.zone == item.zone
                val close = positionsAreClose(original.position, item.position, tolerance)

                if (sameRoom && sameZone && close) {
                    score++
                }
            }
        }
        return score
    }
    private fun positionsAreClose(pos1: Offset?, pos2: Offset?, tolerance: Float): Boolean {
        if (pos1 == null || pos2 == null) return false
        val dx = (pos1.x - pos2.x).absoluteValue
        val dy = (pos1.y - pos2.y).absoluteValue
        return dx <= tolerance && dy <= tolerance
    }
    private var shuffledItems: List<Pair<Int, DrawableResource>>? = null
    fun getItemsForPage(page: Int, allItems: List<Pair<Int, DrawableResource>>): List<Pair<Int, DrawableResource>> {
        return when (page) {
            2 -> allItems.take(8)
            4 -> {
                if (shuffledItems == null) {
                    shuffledItems = allItems.shuffled().take(12)
                }
                shuffledItems!!
            }
            6 -> shuffledItems ?: allItems.take(12) // fallback на случай сбоя
            else -> allItems
        }
    }

   // fun getPlannedActivities(): List<ActivityPlacement> {
   //     return agendaMap.value.map { (slotId, activityId) ->
   //         val parts = slotId.split("_")
   //         val day = parts.getOrNull(1) ?: "unknown"
   //         val hour = parts.getOrNull(3) ?: "unknown"
//
   //         ActivityPlacement(
   //             activity = MeasureObjectString(activityId.hashCode(), activityId),
   //             day = MeasureObjectString(day.hashCode(), day),
   //             time = MeasureObjectString(hour.hashCode(), hour)
   //         )
   //     }
   // }

    fun saveItemForRound(item: DataItem, pageNumber: Int) {
        println("Saving item for page $pageNumber: $item")
        when (pageNumber) {
            2 -> {
                firstRoundItems.add(item)
                val part1 = item.toMemoryQuestionPart1()

                part1List.add(part1)
            }
            4 -> {
                secondRoundItems.add(item)
                val converted = convertToMemoryQuestionPart2List(listOf(item), firstRoundItems, 2).first()
                part2List.add(converted)
                println("Added to part2List: $converted")
            }
            6 -> {
                thirdRoundItems.add(item)
                val converted = convertToMemoryQuestionPart2List(listOf(item), firstRoundItems, 3).first()
                part3List.add(converted)
                println("Added to part3List: $converted")
            }
        }
        saveItem(item)
        println("Current result state: part1=${result.MemoryQuestionPart1}, part2=${result.MemoryQuestionPart2}, part3=${result.MemoryQuestionPart3}")
    }

    fun clearPlacedItems() {
        _placedItems.clear()
    }

    fun initializeItemIdsIfNeeded(items: List<Int>) {
        if (_initialItemIds.isEmpty()) {
            _initialItemIds.addAll(items.indices)
        }
    }
    fun saveItem(item: DataItem) {
        println("Saving item: $item")
        val index = _placedItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            _placedItems[index] = item
        } else {
            _placedItems.add(item)
        }
    }
    fun removeItem(itemId: Int) {
        println("Removing item with id: $itemId")
        _placedItems.removeAll { it.id == itemId }
    }
    fun setPage(page: Int) {
        txtMemoryPage = page
    }

    fun resultUpload() {

        result.date = getCurrentFormattedDateTime()
        result.PhoneCallResult = listOf(callResult)
        result.successRateAfter = userRating
        result.MemoryQuestionPart1 =ArrayList(part1List)
        result.MemoryQuestionPart2=ArrayList(part2List)
        result.MemoryQuestionPart3=ArrayList (part3List)
        result.activitiesPlaced = collectPlannedActivities() as ArrayList<ActivityPlacement>
        println("Result ready: $result")
        println("MemoryQuestionPart1 inside result: ${result.MemoryQuestionPart1}")

    }



 //  fun getMemoryQuestionPart2List( tolerance: Float = 15f, part : Int): List<MemoryQuestionPart2> {
 //      return secondRoundItems.map { item ->
 //          val original = firstRoundItems.find { it.id == item.id }
 //          item.toMemoryQuestionPart2(original, tolerance, part)
 //      }
 //  }


 // fun getMemoryQuestionPart3List(): List<MemoryQuestionPart2> {
 //     return thirdRoundItems.map { item ->
 //         val original = firstRoundItems.find { it.id == item.id }
 //         item.toMemoryQuestionPart2(original, tolerance, part)
 //     }
 // }


    var imageUrl: ArrayList<MeasureObjectString> = ArrayList()
    val images1 = mutableStateListOf<MeasureObjectString>()
    val images2 = mutableStateListOf<MeasureObjectString>()
    val images3 = mutableStateListOf<MeasureObjectString>()


   fun uploadEvaluationResults(
        onSuccess: (() -> Unit)? = null,
        onFailure: ((message: Error) -> Unit)? = null
    ){

        uploadScope.launch {
            try {
                recordInactivity()
                result.patient_id = storage.get(PrefKeys.userId)!!.toInt()
                result.clinicId = storage.get(clinicId)!!
                val measurement = _memoryTest.value?.id ?: 20

                result.measurement = measurement
                resultUpload()

                println("images1: $result")
                val json = Json.encodeToString(MemoryData.serializer(), result)
                println("JSON sent: $json")
                val uploadResult = uploadTestResultsUseCase.execute(result, MemoryData.serializer())
                //println("results object: $result")
                uploadResult.onSuccess {
                    println("✅ העלאה של הכל הצליחה")
                    _uploadStatus.value = Result.success(Unit)
                    onSuccess?.invoke()
                }.onError { error ->
                    println("❌ שגיאה העלאה: $error")
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                    onFailure?.invoke(error)
                }

            } catch (e: Exception) {
                println("🚨 שגיאה לא צפויה: ${e.message}")
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
                onFailure?.invoke(DataError.Remote.UNKNOWN)
            }
        }
    }

private val uploadScope= CoroutineScope(Dispatchers.IO + SupervisorJob())
    fun uploadImage(
        bitmap: ImageBitmap,
        date: String,
        currentQuestion: Int,
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) {
            println("❌ תמונה לא תקינה")
            return
        }

        val imageByteArray = bitmap.toByteArray()
        println("📤 התחלת העלאה, image size: ${imageByteArray.size}")

        uploadScope.launch {

                try {

                    val userId = storage.get(PrefKeys.userId)!!
                    val clinicId = storage.get(PrefKeys.clinicId)!!
                    val measurement = _memoryTest.value?.id ?: 20  /// Need to check what a number

                    val imagePath = bitmapToUploadUseCase.buildPath(
                        clinicId = clinicId,
                        patientId = userId,
                        measurementId = measurement,
                        pathDate = date
                    )

                    println("📁 Path: $imagePath")

                    val result = uploadImageUseCase.execute(
                        imagePath = imagePath,
                        bytes = imageByteArray,
                        clinicId = clinicId,
                        userId = userId
                    )

                    result.onSuccess {
                        println("✅ העלאה הצליחה")
                        saveUploadedImageUrl(currentQuestion, imagePath, date)

                    }.onError {
                        println("❌ שגיאה בהעלאה: $it")

                    }
                } catch (e: Exception) {
                    println("🚨 שגיאה חריגה: ${e.message}")

                }
        }
    }




    private fun saveUploadedImageUrl(currentQuestion: Int?, uploadedUrl: String, date: String) {
        val idImage  = mapOf(
            2 to 228,
            4 to 229,
            5 to 197,
            6 to 231

        )

        val image = MeasureObjectString(
            measureObject = idImage[currentQuestion] ?: 0,
            value = uploadedUrl,
            dateTime = date
        )
        when (currentQuestion) {
            2 -> result.images1.add( image)
            4 -> result.images2.add( image)
            5 -> result.imageUrl.add(image)
            6 -> result.images3.add( image )
        }
    }


    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(PrefKeys.clinicId) ?: return@launch
            val patientId = storage.get(PrefKeys.userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _memoryTest.value = fetched
                    println("fetched evaluation: $fetched")
                }
                .onError { error ->
                    // post an error to a MessageBarState here todo
                    println("Error fetching evaluation: $error")
                }
        }
    }

    //Screenshots
    fun saveRoomScreenshotFirst(bitmap: ImageBitmap, selectedRoom: Room) {
        val dateTime = getCurrentFormattedDateTime()
        val generatedPath = "${selectedRoom.name}_$dateTime"

        images1.add(
            MeasureObjectString(
                value = generatedPath,
                dateTime = dateTime
            )
        )
    }

    fun  saveRoomScreenshotSecond(bitmap: ImageBitmap, selectedRoom: Room) {
        val dateTime = getCurrentFormattedDateTime()
        val generatedPath = "${selectedRoom.name}_$dateTime"

        images2.add(
            MeasureObjectString(
                value = generatedPath,
                dateTime = dateTime
            )
        )
    }

    fun saveRoomScreenshotThird(bitmap: ImageBitmap, selectedRoom: Room) {
        val dateTime = getCurrentFormattedDateTime()
        val generatedPath = "${selectedRoom.name}_$dateTime"

        images3.add(
            MeasureObjectString(
                value = generatedPath,
                dateTime = dateTime
            )
        )
    }

    private fun saveUploadedImageUrl(uploadedUrl: String, date: String) {
        images1.add(MeasureObjectString(value = uploadedUrl, dateTime = date))
    }


}
