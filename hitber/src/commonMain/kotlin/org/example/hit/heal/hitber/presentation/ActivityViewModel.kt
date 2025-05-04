package org.example.hit.heal.hitber

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.hit.heal.hitber.core.domain.Result
import org.example.hit.heal.hitber.core.domain.onError
import org.example.hit.heal.hitber.core.domain.onSuccess
import org.example.hit.heal.hitber.core.utils.BitmapToUploadUseCase
import org.example.hit.heal.hitber.core.utils.UploadEvaluationUseCase
import org.example.hit.heal.hitber.core.utils.UploadImageUseCase
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.EighthQuestionItem
import org.example.hit.heal.hitber.data.model.FirstQuestion
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.data.model.MeasureObjectDouble
import org.example.hit.heal.hitber.data.model.MeasureObjectInt
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.data.model.SecondQuestionItem
import org.example.hit.heal.hitber.data.model.SelectedShapesStringList
import org.example.hit.heal.hitber.data.model.SeventhQuestionType
import org.example.hit.heal.hitber.data.model.SixthQuestionType
import org.example.hit.heal.hitber.data.model.TenthQuestionType
import org.example.hit.heal.hitber.data.model.ThirdQuestionItem

class ActivityViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadEvaluationUseCase: UploadEvaluationUseCase
) : ViewModel() {

    var result: CogData = CogData()

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
        println("FirstQuestion answer: (${result.thirdQuestion})")
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


    private var uploadedImageUrl: String = ""

    fun uploadImage(bitmap: ImageBitmap, date: String, currentQuestion: Int) {

        val bitmapToUploadImageUseCase = BitmapToUploadUseCase()
        println("העלאת תמונה התחילה")

        viewModelScope.launch(Dispatchers.IO) {

            uploadedImageUrl = bitmapToUploadImageUseCase.buildPath(
                clinicId = result.clinicId.toString(),
                patientId = result.patientId.toString(),
                measurementId = result.measurement.toString(),
                pathDate = date
            )

            when (val result = uploadImageUseCase.execute(
                bitmap = bitmap,
                clinicId = result.clinicId,
                userId = result.patientId,
                path = uploadedImageUrl
            )) {
                is Result.Success -> {
                    withContext(Dispatchers.Main) {
                        saveUpLoadedImageUrl(currentQuestion, date)
                        println("העלאת תמונה הצליחה")
                    }
                }

                is Result.Error -> {
                    println("Error: ${result.error}")
                }
            }
        }
    }


    private fun saveUpLoadedImageUrl(currentQuestion: Int?, date: String) {
        val image = MeasureObjectString(
            value = uploadedImageUrl,
            dateTime = date
        )

        when (currentQuestion) {
            6 -> result.sixthQuestion.add(
                SixthQuestionType.SixthQuestionImage(image)
            )

            7 -> result.seventhQuestion.add(
                SeventhQuestionType.SeventhQuestionImage(image)
            )

            10 -> result.tenthQuestion.add(
                TenthQuestionType.TenthQuestionImage(image)
            )
        }
    }

    fun uploadEvaluationResults() {
        println("results object: $result")

        viewModelScope.launch {
            val testResult = uploadEvaluationUseCase.execute(result, CogData.serializer())

            testResult.onSuccess {
                println("העלאה הצליחה")
            }.onError {
                println("שגיאה בהעלאה: ${it.name}")
            }
        }
    }
}




