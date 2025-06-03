package org.example.hit.heal.hitber.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import core.domain.DataError
import core.domain.Error
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
import core.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

class ActivityViewModel(
    private val uploadImageUseCase: UploadFileUseCase,
    private val uploadTestResultsUseCase: UploadTestResultsUseCase,
    private val bitmapToUploadUseCase: BitmapToUploadUseCase
) : ViewModel() {

    private var result: CogData = CogData()

    fun setFirstQuestion(firstQuestion: FirstQuestion) {
        result.firstQuestion = firstQuestion
        println("FirstQuestion answer: (${result.firstQuestion})")
    }

    fun setSecondQuestion(
        answers: ArrayList<Map<Int, String>>,
        date: String
    ) {
        val secondQuestionList = answers.map { map ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    value = 5 - shapesList.size,
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
        answers: List<Map<Int, String>>,
        date: String
    ) {
        val ninthQuestionList = answers.map { map ->
            val shapesList = map.values.toList()
            SecondQuestionItem(
                selectedShapes = SelectedShapesStringList(
                    value = shapesList,
                    dateTime = date
                ),
                wrongShapes = MeasureObjectInt(
                    value = 5 - shapesList.size,
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

    fun uploadImage(
        onSuccess: (() -> Unit)? = null,
        onFailure: ((message: Error) -> Unit)? = null,
        bitmap: ImageBitmap,
        date: String,
        currentQuestion: Int
    ) {
        if (bitmap.width <= 1 || bitmap.height <= 1) {
            println("❌ תמונה לא תקינה")
            onFailure?.invoke(DataError.Local.EMPTY_FILE)
            return
        }

        val imageByteArray = bitmap.toByteArray()
        println("📤 התחלת העלאה, image size: ${imageByteArray.size}")

        uploadScope.launch {
            val clinicId = 8
            val userId = 168
            val measurement =21

            val imagePath = bitmapToUploadUseCase.buildPath(
                clinicId = clinicId,
                patientId = userId,
                measurementId = measurement,
                pathDate = date
            )


            println("📁 Path: $imagePath")

            try {
                uploadImageUseCase.execute(
                    imagePath = imagePath,
                    bytes = imageByteArray,
                    clinicId = clinicId,
                    userId = userId
                ).onSuccess {
                    saveUploadedImageUrl(currentQuestion, imagePath, date)
                    println("✅ העלאה הצליחה")
                    withContext(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }
                }.onError {
                    println("❌ שגיאה בהעלאה: $it")
                    withContext(Dispatchers.Main) {
                        onFailure?.invoke(it)
                    }
                }
            } catch (e: Exception) {
                println("🚨 שגיאה חריגה: ${e.message}")
                withContext(Dispatchers.Main) {
                    onFailure?.invoke(DataError.Remote.UNKNOWN)
                }
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

        viewModelScope.launch {
            try {
                val uploadResult = uploadTestResultsUseCase.execute(result, CogData.serializer())

                uploadResult.onSuccess {
                    println("✅ העלאה הצליחה")
                    onSuccess?.invoke()
                }.onError { error ->
                    println("❌ שגיאה בהעלאה: $error")
                    onFailure?.invoke(error)
                }

            } catch (e: Exception) {
                println("🚨 שגיאה לא צפויה: ${e.message}")
                onFailure?.invoke(DataError.Remote.UNKNOWN)
            }
        }
    }
}




