package org.example.hit.heal.hitber.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import core.data.model.evaluation.Evaluation
import core.data.model.evaluation.EvaluationObject
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
import core.util.PrefKeys
import core.util.PrefKeys.clinicId
import core.util.PrefKeys.userId
import core.utils.getCurrentFormattedDateTime
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
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

    private val _hitberTest = MutableStateFlow<Evaluation?>(null)
    val hitberTest: StateFlow<Evaluation?> = _hitberTest.asStateFlow()


    private fun Evaluation.getObjectsByScreen(screen: Int): List<EvaluationObject> {
        return measurement_objects
            .filter { it.measurement_screen == screen }
            .sortedBy { it.measurement_order }
    }


    fun setFirstQuestion(firstQuestion: FirstQuestion) {
        result.firstQuestion = firstQuestion
        println("FirstQuestion answer: (${result.firstQuestion})")
    }

    fun setSecondQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val secondQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()

            val secondQuestionObjects = _hitberTest.value?.getObjectsByScreen(2)

            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = secondQuestionObjects?.getOrNull(0)?.id ?: 110,
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = secondQuestionObjects?.getOrNull(1)?.id ?: 182,
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.secondQuestion = ArrayList(secondQuestionList)
        println("FirstQuestion answer: (${result.secondQuestion})")
    }

    fun setThirdQuestion(thirdQuestionAnswers: MutableList<Pair<Int, Int>>, date: String) {
        val thirdQuestionObjects = _hitberTest.value?.getObjectsByScreen(3)

        val thirdQuestionList = thirdQuestionAnswers.map { (answer, reactionTime) ->
            ThirdQuestionItem(
                number = MeasureObjectInt(
                    measureObject = thirdQuestionObjects?.getOrNull(0)?.id ?: 111,
                    value = answer,
                    dateTime = date
                ),
                time = MeasureObjectInt(
                    measureObject = thirdQuestionObjects?.getOrNull(1)?.id ?: 112,
                    value = reactionTime,
                    dateTime = date
                ),
                isPressed = MeasureObjectBoolean(
                    measureObject = thirdQuestionObjects?.getOrNull(2)?.id ?: 113,
                    value = true,
                    dateTime = date
                )
            )
        }

        result.thirdQuestion = ArrayList(thirdQuestionList)
        println("thirdQuestion answer: (${result.thirdQuestion})")
    }

    fun setFourthQuestion(answers: List<String>, date: String) {
        val fourthQuestionObjects = _hitberTest.value?.getObjectsByScreen(4) ?: emptyList()

        val measureObjects = answers.mapIndexed { index, answer ->
            MeasureObjectString(
                measureObject = fourthQuestionObjects.getOrNull(index)?.id ?: (132 + index),
                value = answer,
                dateTime = date
            )
        }

        result.fourthQuestion = ArrayList(measureObjects)
        println("FourthQuestion answer: (${result.fourthQuestion})")
    }

    fun setSixthQuestion(
        fridgeOpened: Boolean,
        itemMovedCorrectly: Boolean,
        napkinPlacedCorrectly: Boolean,
        date: String
    ) {
        val sixthQuestionObjects = _hitberTest.value?.getObjectsByScreen(6)

        val sixthQuestionItem = SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(
                measureObject = sixthQuestionObjects?.getOrNull(0)?.id ?: 138,
                value = fridgeOpened,
                dateTime = date
            ),
            correctProductDragged = MeasureObjectBoolean(
                measureObject = sixthQuestionObjects?.getOrNull(1)?.id ?: 139,
                value = itemMovedCorrectly,
                dateTime = date
            ),
            placedOnCorrectNap = MeasureObjectBoolean(
                measureObject = sixthQuestionObjects?.getOrNull(2)?.id ?: 140,
                value = napkinPlacedCorrectly,
                dateTime = date
            )
        )

        result.sixthQuestion = arrayListOf(sixthQuestionItem)
        println("FirstQuestion answer: (${result.sixthQuestion})")
    }

    fun setSeventhQuestion(answer: Boolean, date: String) {
        val seventhQuestionObject = _hitberTest.value?.getObjectsByScreen(7)

        val seventhQuestionItem = SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(
                measureObject = seventhQuestionObject?.getOrNull(0)?.id ?: 141,
                value = answer,
                dateTime = date
            )
        )

        result.seventhQuestion = arrayListOf(seventhQuestionItem)
        println("FirstQuestion answer: (${result.seventhQuestion})")
    }

    fun setEighthQuestion(
        answer: Boolean,
        date: String
    ) {
        val eighthQuestionObject = _hitberTest.value?.getObjectsByScreen(8)

        val eighthQuestionItem = EighthQuestionItem(
            writtenSentence = MeasureObjectBoolean(
                measureObject = eighthQuestionObject?.getOrNull(0)?.id ?: 142,
                value = answer,
                dateTime = date
            )
        )

        result.eighthQuestion = arrayListOf(eighthQuestionItem)
        println("FirstQuestion answer: (${result.eighthQuestion})")
    }

    fun setNinthQuestion(
        answers: List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val ninthQuestionObjects = _hitberTest.value?.getObjectsByScreen(9)

        val ninthQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    measureObject = ninthQuestionObjects?.getOrNull(0)?.id ?: 143,
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    measureObject = ninthQuestionObjects?.getOrNull(1)?.id ?: 183,
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.ninthQuestion = ArrayList(ninthQuestionList)
        println("FirstQuestion answer: (${result.ninthQuestion})")
    }


    fun setTenthQuestion(
        answer: ArrayList<Map<String, Double>>,
        date: String
    ) {
        val tenthQuestionObjects = _hitberTest.value?.getObjectsByScreen(10)

        val tenthQuestionList = answer.map { mapEntry ->
            val (shape, grade) = mapEntry.entries.first()
            TenthQuestionItem(
                shape = MeasureObjectString(
                    measureObject = tenthQuestionObjects?.getOrNull(0)?.id ?: 144,
                    value = shape,
                    dateTime = date
                ),
                grade = MeasureObjectDouble(
                    measureObject = tenthQuestionObjects?.getOrNull(1)?.id ?: 145,
                    value = grade,
                    dateTime = date
                ),
                imageUrl = null
            )
        }

        result.tenthQuestion = ArrayList(tenthQuestionList)
        println("FirstQuestion answer: (${result.tenthQuestion})")
    }


    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(clinicId) ?: return@launch
            val patientId = storage.get(userId)?.toIntOrNull() ?: return@launch

            api.getSpecificEvaluation(clinicId, patientId, evaluationName)
                .onSuccess { fetched ->
                    _hitberTest.value = fetched
                    println("fetched evaluation: $fetched")
                }
                .onError { error ->
                    // post an error to a MessageBarState here todo
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
        println("📤 התחלת העלאה, image size: ${imageByteArray.size}")

        uploadScope.launch {
                try {
                    val userId = storage.get(userId)!!
                    val clinicId = storage.get(clinicId)!!
                    val measurement = hitberTest.value?.id ?: 19

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
                        println("✅ העלאה הצליחה")
                        saveUploadedImageUrl(currentQuestion, imagePath, date)
                    }.onError {error ->
                        _uploadStatus.value = Result.failure(Exception(error.toString()))
                        println("❌ שגיאה בהעלאה: ")
                    }
                } catch (e: Exception) {
                    _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
                    println("🚨 שגיאה חריגה: ${e.message}")
                }
        }
    }

    private fun saveUploadedImageUrl(currentQuestion: Int?, uploadedUrl: String, date: String) {
        val screenObjects =
            _hitberTest.value?.getObjectsByScreen(currentQuestion ?: -1) ?: emptyList()

        val defaultIds = mapOf(
            6 to 200,
            7 to 202,
            10 to 204
        )

        val imageObjectId = screenObjects.firstOrNull { it.object_label == "imageUrl" }?.id
            ?: defaultIds[currentQuestion] ?: 0

        val image = MeasureObjectString(
            measureObject = imageObjectId,
            value = uploadedUrl,
            dateTime = date
        )

        when (currentQuestion) {
            6 -> {
                if (result.sixthQuestion.isEmpty()) {
                    result.sixthQuestion.add(SixthQuestionItem(imageUrl = image))
                } else {
                    val oldItem = result.sixthQuestion[0]
                    val newItem = oldItem.copy(imageUrl = image)
                    result.sixthQuestion[0] = newItem
                }
            }

            7 -> {
                if (result.seventhQuestion.isEmpty()) {
                    result.seventhQuestion.add(SeventhQuestionItem(imageUrl = image))
                } else {
                    val oldItem = result.seventhQuestion[0]
                    val newItem = oldItem.copy(imageUrl = image)
                    result.seventhQuestion[0] = newItem
                }
            }

            10 -> {
                if (result.tenthQuestion.isEmpty()) {
                    result.tenthQuestion.add(TenthQuestionItem(imageUrl = image))
                } else {
                    val lastIndex = result.tenthQuestion.lastIndex
                    val oldItem = result.tenthQuestion[lastIndex]
                    val newItem = oldItem.copy(imageUrl = image)
                    result.tenthQuestion[lastIndex] = newItem
                }

                uploadEvaluationResults()
            }
        }
    }

    private fun uploadEvaluationResults() {
        uploadScope.launch {
            try {
                result.patientId = storage.get(userId)!!.toInt()
                result.clinicId = storage.get(clinicId)!!
                result.measurement = hitberTest.value?.id ?: 19
                result.date = getCurrentFormattedDateTime()

                val json = Json.encodeToString(CogData.serializer(), result)
                println("JSON sent: $json")

                val uploadResult = uploadTestResultsUseCase.execute(result, CogData.serializer())

                uploadResult.onSuccess {
                    println("✅ העלאה של הכל הצליחה")
                    _uploadStatus.value = Result.success(Unit)

                }.onError { error ->
                    println("❌ שגיאה העלאה: $error")
                    _uploadStatus.value = Result.failure(Exception(error.toString()))
                }

            } catch (e: Exception) {
                println("🚨 שגיאה לא צפויה: ${e.message}")
                _uploadStatus.value = Result.failure(Exception(DataError.Remote.UNKNOWN.toString()))
            }

        }
    }
}




