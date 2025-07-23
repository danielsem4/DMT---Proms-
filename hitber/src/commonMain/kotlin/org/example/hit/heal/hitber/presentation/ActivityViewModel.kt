package org.example.hit.heal.hitber.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import core.domain.use_case.BitmapToUploadUseCase
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.EighthQuestionItem
import org.example.hit.heal.hitber.data.model.FirstQuestion
import org.example.hit.heal.hitber.data.model.SecondQuestionItem
import org.example.hit.heal.hitber.data.model.SelectedShapesStringList
import org.example.hit.heal.hitber.data.model.ThirdQuestionItem
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.util.PrefKeys.clinicId
import core.util.PrefKeys.userId
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.data.model.SeventhQuestionItem
import org.example.hit.heal.hitber.data.model.SixthQuestionItem
import org.example.hit.heal.hitber.data.model.TenthQuestionItem

class ActivityViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val bitmapToUploadUseCase: BitmapToUploadUseCase,
    private val api: AppApi,
    private val storage: Storage
) : ViewModel() {

    private var result: CogData = CogData()

    private val _uploadStatus = MutableStateFlow<Result<Unit>?>(null)
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus

    private val _hitBerTest = MutableStateFlow<Evaluation?>(null)
    val hitBerTest: StateFlow<Evaluation?> = _hitBerTest.asStateFlow()

    private val _capturedBitmap1 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap1: StateFlow<ImageBitmap?> = _capturedBitmap1.asStateFlow()

    private val _capturedBitmap2 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap2: StateFlow<ImageBitmap?> = _capturedBitmap2.asStateFlow()

    private val _capturedBitmap3 = MutableStateFlow<ImageBitmap?>(null)
    val capturedBitmap3: StateFlow<ImageBitmap?> = _capturedBitmap3.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var isUploadAllImagesFinished = 0

    fun saveBitmap1(bitmap: ImageBitmap) {
        _capturedBitmap1.value = bitmap
    }

    fun saveBitmap2(bitmap: ImageBitmap) {
        _capturedBitmap2.value = bitmap
    }

    fun saveBitmap3(bitmap: ImageBitmap) {
        _capturedBitmap3.value = bitmap
    }

    fun setFirstQuestion(firstQuestion: FirstQuestion) {
        result.firstQuestion = firstQuestion
    }

    fun setSecondQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val secondQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()

            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = 110,
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = 182,
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.secondQuestion = ArrayList(secondQuestionList)
    }

    fun setThirdQuestion(thirdQuestionAnswers: MutableList<Pair<Int, Int>>, date: String) {

        val thirdQuestionList = thirdQuestionAnswers.map { (answer, reactionTime) ->
            ThirdQuestionItem(
                number = MeasureObjectInt(
                    measureObject = 111,
                    value = answer,
                    dateTime = date
                ),
                time = MeasureObjectInt(
                    measureObject = 112,
                    value = reactionTime,
                    dateTime = date
                ),
                isPressed = MeasureObjectBoolean(
                    measureObject = 113,
                    value = true,
                    dateTime = date
                )
            )
        }

        result.thirdQuestion = ArrayList(thirdQuestionList)
        println("thirdQuestion: ${result.thirdQuestion}")
    }

    fun setFourthQuestion(answers: List<String>, date: String) {

        val measureObjects = answers.mapIndexed { index, answer ->
            MeasureObjectString(
                measureObject = (132 + index),
                value = answer,
                dateTime = date
            )
        }

        result.fourthQuestion = ArrayList(measureObjects)
    }

    fun setSixthQuestion(
        fridgeOpened: Boolean,
        itemMovedCorrectly: Boolean,
        napkinPlacedCorrectly: Boolean,
        date: String
    ) {
        val sixthQuestionItem = SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(
                measureObject = 138,
                value = fridgeOpened,
                dateTime = date
            ),
            correctProductDragged = MeasureObjectBoolean(
                measureObject = 139,
                value = itemMovedCorrectly,
                dateTime = date
            ),
            placedOnCorrectNap = MeasureObjectBoolean(
                measureObject = 140,
                value = napkinPlacedCorrectly,
                dateTime = date
            )
        )

        result.sixthQuestion = arrayListOf(sixthQuestionItem)
    }

    fun setSeventhQuestion(answer: Boolean, date: String) {

        val seventhQuestionItem = SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(
                measureObject = 141,
                value = answer,
                dateTime = date
            )
        )

        result.seventhQuestion = arrayListOf(seventhQuestionItem)
    }

    fun setEighthQuestion(
        answer: Boolean,
        date: String
    ) {

        val eighthQuestionItem = EighthQuestionItem(
            writtenSentence = MeasureObjectBoolean(
                measureObject = 142,
                value = answer,
                dateTime = date
            )
        )

        result.eighthQuestion = arrayListOf(eighthQuestionItem)
    }

    fun setNinthQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {

        val ninthQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = 143,
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = 183,
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.ninthQuestion = ArrayList(ninthQuestionList)
    }


    fun setTenthQuestion(
        answer: ArrayList<Map<String, Double>>,
        date: String
    ) {

        val tenthQuestionList = answer.map { mapEntry ->
            val (shape, grade) = mapEntry.entries.first()
            TenthQuestionItem(
                shape = MeasureObjectString(
                    measureObject = 144,
                    value = shape,
                    dateTime = date
                ),
                grade = MeasureObjectDouble(
                    measureObject = 145,
                    value = grade,
                    dateTime = date
                ),
                imageUrl = null
            )
        }

        result.tenthQuestion = ArrayList(tenthQuestionList)
    }

    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(clinicId) ?: return@launch
            val patientId = storage.get(userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _hitBerTest.value = fetched
                }
                .onError { error ->
                    println("Error fetching evaluation: $error")
                }
        }
    }

    fun uploadImage(
        bitmap: ImageBitmap,
        date: String,
        currentQuestion: Int,
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) {
            return
        }

        val imageByteArray = bitmap.toByteArray()

        uploadScope.launch {
            _isLoading.value = true
            try {
                val userId = storage.get(userId)!!
                val clinicId = storage.get(clinicId)!!
                val measurement = hitBerTest.value?.id ?: 19

                val imagePath = bitmapToUploadUseCase.buildPath(
                    clinicId = clinicId,
                    patientId = userId,
                    measurementId = measurement,
                    pathDate = date
                )

                val result = uploadImageUseCase.execute(
                    imagePath = imagePath,
                    bytes = imageByteArray,
                    clinicId = clinicId,
                    userId = userId
                )

                result.onSuccess {
                    saveUploadedImageUrl(currentQuestion, imagePath, date)

                    if (isUploadAllImagesFinished == 3) {
                        uploadEvaluationResults()
                    }

                }.onError { error ->
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                }
            } catch (e: Exception) {
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
            }
        }
    }

    private fun saveUploadedImageUrl(currentQuestion: Int?, uploadedUrl: String, date: String) {
        val imageIds = mapOf(
            6 to 200,
            7 to 202,
            10 to 204
        )

        val image = MeasureObjectString(
            measureObject = imageIds[currentQuestion] ?: 0,
            value = uploadedUrl,
            dateTime = date
        )

        when (currentQuestion) {
            6 -> updateImageInQuestionList(
                list = result.sixthQuestion,
                createItem = { SixthQuestionItem(imageUrl = image) },
                updateItem = { it.copy(imageUrl = image) }
            )

            7 -> updateImageInQuestionList(
                list = result.seventhQuestion,
                createItem = { SeventhQuestionItem(imageUrl = image) },
                updateItem = { it.copy(imageUrl = image) }
            )

            10 -> {
                updateImageInQuestionList(
                    list = result.tenthQuestion,
                    createItem = { TenthQuestionItem(imageUrl = image) },
                    updateItem = { it.copy(imageUrl = image) },
                )
            }
        }
        isUploadAllImagesFinished++
    }

    private fun <T> updateImageInQuestionList(
        list: MutableList<T>,
        createItem: () -> T,
        updateItem: (T) -> T,
    ) {
        if (list.isEmpty()) {
            list.add(createItem())
        } else {
            val index = list.lastIndex
            list[index] = updateItem(list[index])
        }
    }

    private fun uploadEvaluationResults() {
        uploadScope.launch {
            try {
                result.patientId = storage.get(userId)!!.toInt()
                result.clinicId = storage.get(clinicId)!!
                result.measurement = hitBerTest.value?.id ?: 19
                result.date = getCurrentFormattedDateTime()

                val uploadResult = uploadTestResultsUseCase.execute(result, CogData.serializer())

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
}




