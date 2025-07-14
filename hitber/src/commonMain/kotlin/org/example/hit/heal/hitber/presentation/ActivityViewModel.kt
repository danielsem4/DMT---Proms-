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
import core.domain.Error
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
import org.example.hit.heal.hitber.data.model.SeventhQuestionType
import org.example.hit.heal.hitber.data.model.SixthQuestionType
import org.example.hit.heal.hitber.data.model.TenthQuestionType
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
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.example.hit.heal.core.presentation.Resources.String.clockTest

class ActivityViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val bitmapToUploadUseCase: BitmapToUploadUseCase,
    private val api: AppApi,
    private val storage: Storage
) : ViewModel() {

    private var result: CogData = CogData()

    private val _uploadStatus  = MutableStateFlow<Result<Unit>?>(null)
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus

    private val _hitberTest = MutableStateFlow<Evaluation?>(null)
    val hitberTest: StateFlow<Evaluation?> = _hitberTest.asStateFlow()

    fun setFirstQuestion(firstQuestion: FirstQuestion) {
        result.firstQuestion = firstQuestion
        println("FirstQuestion answer: (${result.firstQuestion})")
    }

    fun setSecondQuestion(
        answers:  List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val secondQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    value = 5 - correctShapesCount,
                    dateTime = date
                )
            )
        }

        result.secondQuestion = ArrayList(secondQuestionList)
        println("FirstQuestion answer: (${result.secondQuestion})")
    }

    fun setThirdQuestion(thirdQuestionAnswers: MutableList<Pair<Int, Int>>, date: String) {
        val thirdQuestionList = thirdQuestionAnswers.map { (answer, reactionTime) ->
            ThirdQuestionItem(
                number = MeasureObjectInt(
                    value = answer,
                    dateTime = date
                ),
                time = MeasureObjectInt(
                    value = reactionTime,
                    dateTime = date
                ),
                isPressed = MeasureObjectBoolean(
                    value = true,
                    dateTime = date
                )
            )
        }

        result.thirdQuestion = ArrayList(thirdQuestionList)
        println("thirdQuestion answer: (${result.thirdQuestion})")
    }

    fun setFourthQuestion(answers: List<String>, date: String) {
        val measureObjects = answers.map { answer ->
            MeasureObjectString(
                value = answer,
                dateTime = date
            )
        }
        result.fourthQuestion = ArrayList(measureObjects)
        println("FirstQuestion answer: (${result.fourthQuestion})")
    }

    fun setSixthQuestion(
        fridgeOpened: Boolean,
        itemMovedCorrectly: Boolean,
        napkinPlacedCorrectly: Boolean,
        date: String
    ) {
        val sixthQuestionItem = SixthQuestionType.SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(value = fridgeOpened, dateTime = date),
            correctProductDragged = MeasureObjectBoolean(
                value = itemMovedCorrectly,
                dateTime = date
            ),
            placedOnCorrectNap = MeasureObjectBoolean(
                value = napkinPlacedCorrectly,
                dateTime = date
            )
        )

        result.sixthQuestion = arrayListOf(sixthQuestionItem)
        println("FirstQuestion answer: (${result.sixthQuestion})")
    }

    fun setSeventhQuestion(answer: Boolean, date: String) {
        val seventhQuestionItem = SeventhQuestionType.SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(value = answer, dateTime = date)
        )

        result.seventhQuestion = arrayListOf(seventhQuestionItem)
        println("FirstQuestion answer: (${result.seventhQuestion})")
    }

    fun setEighthQuestion(
        answer: Boolean,
        date: String
    ) {
        val eighthQuestionItem = EighthQuestionItem(
            writtenSentence = MeasureObjectBoolean(
                value = answer,
                dateTime = date
            )
        )

        result.eighthQuestion = arrayListOf(eighthQuestionItem)
        println("FirstQuestion answer: (${result.eighthQuestion})")
    }

    fun setNinthQuestion(
        answers:  List<Pair<Map<Int, String>, Int>>,
        date: String
    ) {
        val ninthQuestionList = answers.map { (map, correctShapesCount) ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
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
        val tenthQuestionList = answer.map { mapEntry ->
            val (shape, grade) = mapEntry.entries.first()
            TenthQuestionType.TenthQuestionItem(
                shape = MeasureObjectString(
                    value = shape,
                    dateTime = date
                ),
                grade = MeasureObjectDouble(
                    value = grade,
                    dateTime = date
                )
            )
        }

        result.tenthQuestion = ArrayList(tenthQuestionList)
        println("FirstQuestion answer: (${result.tenthQuestion})")
    }

    private val uploadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun loadEvaluation(evaluationName: String) {
        viewModelScope.launch {
            val clinicId = storage.get(PrefKeys.clinicId) ?: return@launch
            val patientId = storage.get(PrefKeys.userId)?.toIntOrNull() ?: return@launch

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
        maxRetries: Int = 3
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) {
            println("❌ תמונה לא תקינה")
            return
        }

        val imageByteArray = bitmap.toByteArray()
        println("📤 התחלת העלאה, image size: ${imageByteArray.size}")

        uploadScope.launch {
            var attempt = 0
            var success = false
            while (attempt < maxRetries && !success) {
                try {
                    val userId = storage.get(PrefKeys.userId)!!
                    val clinicId = storage.get(PrefKeys.clinicId)!!
                    val measurement = hitberTest.value?.id ?: 19

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
                        success = true
                    }.onError {
                        println("❌ שגיאה בהעלאה: $it")
                        if (attempt == maxRetries - 1) {
                        } else {
                            println("ניסיון מחדש אחרי שגיאה...")
                            delay(1000L * (attempt + 1))
                        }
                    }
                } catch (e: Exception) {
                    println("🚨 שגיאה חריגה: ${e.message}")
                    if (attempt == maxRetries - 1) {
                        println("🚨 העלאה נכשלה לאחר מספר ניסיונות")
                    }
                    else {
                        println("ניסיון מחדש אחרי שגיאה חריגה...")
                        delay(1000L * (attempt + 1))
                    }
                }
                attempt++
            }
        }
    }


    private fun saveUploadedImageUrl(currentQuestion: Int?, uploadedUrl: String, date: String) {
        val image = MeasureObjectString(
            value = uploadedUrl,
            dateTime = date
        )

        when (currentQuestion) {
            6 -> result.sixthQuestion.add(SixthQuestionType.SixthQuestionImage(image))
            7 -> result.seventhQuestion.add(SeventhQuestionType.SeventhQuestionImage(image))
            10 -> result.tenthQuestion.add(TenthQuestionType.TenthQuestionImage(image))
        }
    }


    fun uploadEvaluationResults(
        onSuccess: (() -> Unit)? = null,
        onFailure: ((message: Error) -> Unit)? = null
    ) {
        println("results object: $result")
        uploadScope.launch {
            try {
                val userId = storage.get(PrefKeys.userId)!!.toInt()
                val clinicId = storage.get(PrefKeys.clinicId)!!
                val measurement = hitberTest.value?.id ?: 19
                val date = getCurrentFormattedDateTime()

                if (result.fifthQuestion.isEmpty()) {
                    result.fifthQuestion = arrayListOf()
                }

                result.patientId = userId
                result.clinicId = clinicId
                result.measurement = measurement
                result.date = date
                println("results object: $result")

                val json = Json.encodeToString(CogData.serializer(), result)
                println("JSON sent: $json")

                val uploadResult = uploadTestResultsUseCase.execute(result, CogData.serializer())

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
}




