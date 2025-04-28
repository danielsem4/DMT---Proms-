package org.example.hit.heal.hitber

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

class ActivityViewModel : ViewModel() {

    private val _result = MutableStateFlow(CogData())
    val result : StateFlow<CogData> get() = _result

    fun setFirstQuestion(firstQuestion: FirstQuestion) {
        _result.value = _result.value.copy(firstQuestion = firstQuestion)
        println("FirstQuestion answer: (${_result.value.firstQuestion})")
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

        _result.value = _result.value.copy(
            secondQuestion = ArrayList(secondQuestionList)
        )
        println("FirstQuestion answer: (${_result.value.secondQuestion})")
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

        _result.value = _result.value.copy(
            thirdQuestion = ArrayList(thirdQuestionList)
        )
        println("FirstQuestion answer: (${_result.value.thirdQuestion})")}

    fun setFourthQuestion(answers: List<String>, date: String) {
        val measureObjects = answers.map { answer ->
            MeasureObjectString(
                value = answer,
                dateTime = date
            )
        }
        _result.value = _result.value.copy(
            fourthQuestion = ArrayList(measureObjects)
        )
        println("FirstQuestion answer: (${_result.value.fourthQuestion})")
    }


    fun setSixthQuestion(
        fridgeOpened: Boolean,
        itemMovedCorrectly: Boolean,
        napkinPlacedCorrectly: Boolean,
        date : String
    ) {
        val sixthQuestionItem = SixthQuestionType.SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(value = fridgeOpened, dateTime = date),
            correctProductDragged = MeasureObjectBoolean(value = itemMovedCorrectly, dateTime = date),
            placedOnCorrectNap = MeasureObjectBoolean(value = napkinPlacedCorrectly, dateTime = date)
        )

        _result.value = _result.value.copy(sixthQuestion = arrayListOf(sixthQuestionItem))
        println("FirstQuestion answer: (${_result.value.sixthQuestion})")
    }

    fun setSeventhQuestion(answer: Boolean, date: String) {
        val seventhQuestionItem = SeventhQuestionType.SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(value = answer, dateTime = date)
        )

        _result.value = _result.value.copy(
            seventhQuestion = arrayListOf(seventhQuestionItem)
        )
        println("FirstQuestion answer: (${_result.value.seventhQuestion})")
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

        _result.value = _result.value.copy(
            eighthQuestion = arrayListOf(eighthQuestionItem)
        )
        println("FirstQuestion answer: (${_result.value.eighthQuestion})")
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

        _result.value = _result.value.copy(
            ninthQuestion = ArrayList(ninthQuestionList)
        )
        println("FirstQuestion answer: (${_result.value.ninthQuestion})")
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

        _result.value = _result.value.copy(
            tenthQuestion = ArrayList(tenthQuestionList)
        )
        println("FirstQuestion answer: (${_result.value.tenthQuestion})")
    }


//    fun addImageToQuestion(image: String, questionType: QuestionType) {
//        val imageObject = MeasureObjectString(value = image, dateTime = getNow())
//
//        when (questionType) {
//            is QuestionType.SixthQuestion -> {
//                val currentList = ArrayList(_result.value.sixthQuestion)
//                val imageItem = SixthQuestionType.SixthQuestionImage(imageUrl = imageObject)
//                currentList.add(imageItem)
//                _result.value = _result.value.copy(sixthQuestion = currentList)
//            }
//            is QuestionType.SeventhQuestion -> {
//                val currentList = ArrayList(_result.value.seventhQuestion)
//                val imageItem = SeventhQuestionType.SeventhQuestionImage(imageUrl = imageObject)
//                currentList.add(imageItem)
//                _result.value = _result.value.copy(seventhQuestion = currentList)
//            }
//            is QuestionType.TenthQuestion -> {
//                val currentList = ArrayList(_result.value.tenthQuestion)
//                val imageItem = TenthQuestionType.TenthQuestionImage(imageUrl = imageObject)
//                currentList.add(imageItem)
//                _result.value = _result.value.copy(tenthQuestion = currentList)
//            }
//        }
//    }
//
//    fun uploadHitberResult(){
//
//    }

}




