package org.example.hit.heal.hitber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.shapes.shapeList
import org.example.hit.heal.hitber.shapes.shapeSets
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.jetbrains.compose.resources.DrawableResource


class ActivityViewModel : ViewModel() {

    private val _timeAndPlaceFocusedQuestion = MutableStateFlow<String?>(null)
    val timeAndPlaceFocusedQuestion: StateFlow<String?> = _timeAndPlaceFocusedQuestion.asStateFlow()

    private val _timeAndPlaceAnswersMap = MutableStateFlow<Map<String, DropDownItem>>(emptyMap())
    val timeAndPlaceAnswersMap: StateFlow<Map<String, DropDownItem>> =
        _timeAndPlaceAnswersMap.asStateFlow()

    fun timeAndPlaceSetFocusedQuestion(question: String?) {
        _timeAndPlaceFocusedQuestion.value = question
    }

    fun timeAndPlaceSetAnswer(question: String, answer: DropDownItem) {
        _timeAndPlaceAnswersMap.value = _timeAndPlaceAnswersMap.value.toMutableMap().apply {
            this[question] = answer
        }
    }

    private val _concentrationStartButtonIsVisible = MutableStateFlow(true)
    val concentrationStartButtonIsVisible: StateFlow<Boolean> =
        _concentrationStartButtonIsVisible.asStateFlow()


    private val _concentrationAnswers = MutableStateFlow<List<Int>>(emptyList())
    val concentrationAnswers: StateFlow<List<Int>> = _concentrationAnswers.asStateFlow()

    private val _number = MutableStateFlow((0..9).random())  // שימוש ב-StateFlow כדי לשמור על המספר
    val number: StateFlow<Int> = _number.asStateFlow()
    private var elapsedTime by mutableStateOf(0)

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<DrawableResource>> = _listShapes

    private val _selectedSet = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedSet: StateFlow<List<DrawableResource>> = _selectedSet.asStateFlow()


    private val _selectedShapes = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedShapes: StateFlow<List<DrawableResource>> = _selectedShapes

    // להתחיל את המשחק (הסתרת כפתור ההתחלה)
    fun concentrationStartButtonSetVisible(visible: Boolean) {
        _concentrationStartButtonIsVisible.value = visible
    }

    // הוספת תשובות
    fun addConcentrationAnswer(answer: Int) {
        _concentrationAnswers.value += answer
    }

    // עדכון מספרים והזמן
    fun startRandomNumberGeneration(scope: CoroutineScope) {
        scope.launch {
            while (elapsedTime < 60) {
                delay(3000)
                _number.value = (0..9).random()
                elapsedTime += 3
            }
            _isFinished.value = true
        }
    }

    fun setRandomShapeSet() {
        if (_selectedSet.value.isEmpty()) {
            _selectedSet.value = shapeSets.random() // רק אם הוא ריק, הגדר אותו
        }
    }

    fun setSelectedShapes(shape: DrawableResource) {
        if (_selectedShapes.value.size >= 5) {
            // אם הצורה כבר קיימת, נסיר אותה
            if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value = _selectedShapes.value.filterNot { it == shape }
            }
        } else {
            // אם הצורה לא קיימת, נוסיף אותה
            _selectedShapes.value = if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value.filterNot { it == shape } // אם כבר קיימת, נסיר אותה
            } else {
                _selectedShapes.value + shape // נוסיף את הצורה לרשימה
            }
        }
    }


//    fun calculateCorrectShapesCount() {
//        _correctShapesCount.value = selectedShapes.value.count { it in selectedSet.value }
//    }

    private val _correctShapesList = MutableStateFlow<List<DrawableResource>>(emptyList())
    val correctShapesList: StateFlow<List<DrawableResource>> = _correctShapesList


    fun calculateCorrectShapesCount(): Int {
        val correctShapes = selectedShapes.value.filter { it in selectedSet.value }
        _correctShapesCount.value = correctShapes.size
        _correctShapesList.value = correctShapes // עדכון רשימת הצורות הנכונות
        return _correctShapesCount.value
    }


    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt

    private val _correctShapesCount = MutableStateFlow(0)
    val correctShapesCount: StateFlow<Int> = _correctShapesCount

    private val _distractorsCount = MutableStateFlow(10)
    val distractorsCount: StateFlow<Int> = _distractorsCount

    fun updateTask() {

        when (_correctShapesCount.value) {
            5 -> {
                _isFinished.value = true
            }

            4 -> { if (_attempt.value == 3) _isFinished.value = true }

            3 -> {
                _distractorsCount.value = when (_attempt.value) {
                    1 -> 10
                    2 -> 9
                    3 -> 7
                    else -> 10
                }
                // removeDistractors(_distractorsCount.value)
                if (_attempt.value == 3) _isFinished.value = true
            }

            2, 1, 0 -> {
                _distractorsCount.value = when (_attempt.value) {
                    1 -> 10
                    2 -> 7
                    3 -> 5
                    else -> 10
                }
                // removeDistractors(_distractorsCount.value)
                if (_attempt.value == 3) _isFinished.value = true
            }
        }
    }


//    private fun removeDistractors(count: Int) {
//        val distractors = _listShapes.value.filter { it !in selectedSet.value }
//        val distractorsToRemove = distractors.take(count)
//        val updatedList = _listShapes.value - distractorsToRemove.toSet()
//        _listShapes.value = updatedList
//    }


    fun onAttempt() {
        _attempt.value++
    }
}