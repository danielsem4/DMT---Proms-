package com.example.new_memory_test.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_memory_test.data.model.MemoryData
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.data.DataItem
import core.domain.use_case.PlayAudioUseCase
import core.utils.AudioPlayer
import core.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import kotlin.math.absoluteValue

class ViewModelMemoryTest(
    private val playAudioUseCase: PlayAudioUseCase

) : ViewModel() {
    var txtMemoryPage by mutableStateOf(1)
    private val _placedItems = mutableStateListOf<DataItem>()
    val placedItems: List<DataItem> get() = _placedItems.toList()
    var finalScore: Int =0 ;
    val firstRoundItems = mutableListOf<DataItem>()
    val secondRoundItems = mutableListOf<DataItem>()
    val thirdRoundItems = mutableListOf<DataItem>()
    private val _initialItemIds = mutableListOf<Int>()
    private var result: MemoryData = MemoryData()
    fun getResult(): MemoryData {
        return result
    }

//audio
    fun onPlayAudio(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }
    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }




    fun setResult(result: MemoryData) {
        result.PhoneCallResult= emptyList()
        this.result = result
    }

    //Choose reminder option in custom dialog
    val selectedReminderOption = mutableStateOf<String?>(null)

    //Raiting for user in the end
    val userRating = mutableStateOf<Float?>(null)

    val agendaMap = mutableStateOf<Map<String, String>>(emptyMap())

    val screensFirstRound :List<ByteArray> =mutableListOf()
    val screensSecondRound :List<ByteArray> =mutableListOf()
    val screensThirdRound :List<ByteArray> =mutableListOf()

    var clickedAgree by mutableStateOf(false)
        private set

    var clickedDisagree by mutableStateOf(false)
        private set

    fun setAgree() {
        clickedAgree = true
        clickedDisagree = false
    }

    fun setDisagree() {
        clickedDisagree = true
        clickedAgree = false
    }

    //   var screenshotByteArray: ByteArray? by mutableStateOf(null)
    //    private set

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

    fun checkFinalScore(): Int {
        finalScore = 0
        val tolerance = 15f
        // Check for correct placement in the first round and second round
        for (itemSecond in secondRoundItems) {
            for (itemFirst in firstRoundItems) {
                // Check if the two items have the same ID
                if (itemSecond.id == itemFirst.id) {
                    // Check if the two items are in the same room and zone
                    if (itemSecond.room == itemFirst.room && itemSecond.zone == itemFirst.zone) {
                        if (positionsAreClose(itemFirst.position, itemSecond.position, tolerance)) {
                            finalScore++
                        }
                    }
                }
            }
        }
        // Check for correct placement in the second round and third round (SAME THAT IN SECOND AND FIRST)
        for (itemThird in thirdRoundItems) {
            for (itemFirst in firstRoundItems) {
                if (itemThird.id == itemFirst.id) {

                    if (itemThird.room == itemFirst.room && itemThird.zone == itemFirst.zone) {
                        if (positionsAreClose(itemFirst.position, itemThird.position, tolerance)) {
                            finalScore++
                        }
                    }
                }
            }
        }
        //check correct final score
        println(" 🔍 Final score: $finalScore")
        return finalScore
    }
    private fun positionsAreClose(pos1: Offset?, pos2: Offset?, tolerance: Float): Boolean {
        if (pos1 == null || pos2 == null) return false
        // calculate the distance between the two positions
        val dx = (pos1.x - pos2.x).absoluteValue
        val dy = (pos1.y - pos2.y).absoluteValue
        // if the distance is less than or equal to the tolerance, the positions are close and we need to return true
        return dx <= tolerance && dy <= tolerance
    }
    fun saveItemForRound(item: DataItem, pageNumber: Int) {
        when (pageNumber) {
            2 -> {
                firstRoundItems.removeAll { it.id == item.id }
                firstRoundItems.add(item)
            }
            4 -> {
                secondRoundItems.removeAll { it.id == item.id }
                secondRoundItems.add(item)
            }
            6 -> {
                thirdRoundItems.removeAll { it.id == item.id }
                thirdRoundItems.add(item)
            }
        }
        println("💾 Item for page $pageNumber saved: $item")
        println("🔍 First round: $firstRoundItems")
        println("🔍 Second round: $secondRoundItems")
        println("🔍 Third round: $thirdRoundItems")
        saveItem(item)
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


 //fun buildAndSendMemoryData() {
 //    val memoryData = MemoryData(
 //        measurement = 20,
 //        patient_id = patientId,
 //        date = getCurrentFormattedDateTime(), // например, с помощью kotlinx.datetime
 //        clinicId = currentClinicId,
 //        MemoryQuestionPart1 = firstRoundItems,
 //        images1 = selectedImages1,
 //        notificationsCount = notificationCounts,
 //        successRateAfter = successRates,
 //        PhoneCallResult = phoneCallResults,
 //        MemoryQuestionPart2 = collectedPart2,
 //        images2 = selectedImages2,
 //        activitiesPlaced = plannedActivities,
 //        imageUrl = uploadedImageUrls,
 //        MemoryQuestionPart3 = collectedPart3,
 //        images3 = selectedImages3
 //    )

 //    viewModelScope.launch {
 //        val result = remoteDataSource.sendResults(memoryData, MemoryData.serializer())
 //        result.onSuccess {
 //            println("✅ Успешно отправлено: $it")
 //        }.onFailure {
 //            println("❌ Ошибка при отправке: $it")
 //        }
 //    }
 //}



  // private val _audioResourceId = MutableStateFlow<StringResource?>(null)
  // val audioResourceId = _audioResourceId.asStateFlow()

  // val isPlaying = playAudioUseCase.isPlaying

  // fun setAudio(stringResource: StringResource) {
  //     _audioResourceId.value =  StringResource(stringResource) // или другой способ хранения строки
  // }

  // fun onPlayAudio() {
  //     val text = _audioResourceId.value?.let { it.toString() } ?: return
  //     playAudioUseCase.playAudio(text)
  // }


    fun saveScreenshot(bytes: ByteArray) {

        // screenshotByteArray = bytes
    }

    //ScreenShots of room
    // private val _roomScreenshotsFirst  = map
    //private val _roomScreenshotsSecond = map
    //private val _roomScreenshotsThird = map

    //val roomScreenshotsSecond: Map<Room, ImageBitmap> get() = _roomScreenshotsSecond
    // val roomScreenshotsFirst: Map<Room, ImageBitmap> get() = _roomScreenshotsFirst
    //val roomScreenshotsThird: Map<Room, ImageBitmap> get() = _roomScreenshotsThird

    fun saveRoomScreenshotFirst(room: Room, bitmap: ImageBitmap) {
        // val byteArray = bitmap.toByteArray(CompressionFormat.PNG, 100)
        //    _roomScreenshotsFirst[room] = bitmap
    }

    fun saveRoomScreenshotSecond(room: Room, bitmap: ImageBitmap) {
        // val byteArray = bitmap.toByteArray(CompressionFormat.PNG, 100)
        //   _roomScreenshotsSecond[room] = bitmap
    }

    fun saveRoomScreenshotThird(room: Room, bitmap: ImageBitmap) {
        // val byteArray = bitmap.toByteArray(CompressionFormat.PNG, 100)
        // _roomScreenshotsThird[room] = bitmap
    }

}
