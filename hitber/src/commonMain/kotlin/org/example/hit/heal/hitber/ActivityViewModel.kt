package org.example.hit.heal.hitber

import DropDownItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.hit.heal.hitber.shapes.components.shapeList
import org.example.hit.heal.hitber.shapes.components.shapeSets
import org.jetbrains.compose.resources.DrawableResource


class ActivityViewModel : ViewModel() {

    //Time and Place Question (1/10)
//    private val _focusedQuestion = MutableStateFlow<String?>(null)
//    val focusedQuestion: StateFlow<String?> = _focusedQuestion.asStateFlow()

    private val _answersTimeAndPlace = MutableStateFlow<Map<String, DropDownItem>>(emptyMap())
    val answersTimeAndPlace: StateFlow<Map<String, DropDownItem>> =
        _answersTimeAndPlace.asStateFlow()

//    fun setFocusedQuestion(question: String?) {
//        _focusedQuestion.value = question
//    }

    fun setAnswersTimeAndPlace(question: String, answer: DropDownItem) {
        _answersTimeAndPlace.value = _answersTimeAndPlace.value.toMutableMap().apply {
            this[question] = answer
        }
    }

    //Shape Question (2/10)
    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<DrawableResource>> = _listShapes.asStateFlow()

    private val _selectedSet = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedSet: StateFlow<List<DrawableResource>> = _selectedSet.asStateFlow()

    private val _selectedShapes = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedShapes: StateFlow<List<DrawableResource>> = _selectedShapes.asStateFlow()

    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    private val _answersShapes = MutableStateFlow<List<Triple<Int,List<DrawableResource>, Int>>>(emptyList())

//    private var attempt = 1
    private var correctShapesCount = 0
    private var distractorToRemove = 0


    fun setRandomShapeSet() {
        if (_selectedSet.value.isEmpty()) {
            _selectedSet.value = shapeSets.random()
        }
    }

    fun setSelectedShapes(shape: DrawableResource) {
        if (_selectedShapes.value.size >= 5) {
            if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value = _selectedShapes.value.filterNot { it == shape }
            }
        } else {
            _selectedShapes.value = if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value.filterNot { it == shape }
            } else {
                _selectedShapes.value + shape
            }
        }
    }



    fun calculateCorrectShapesCount() {
        correctShapesCount = selectedShapes.value.count { it in selectedSet.value }
    }

    fun updateTask() {
        when (correctShapesCount) {
            5 -> _attempt.value=3

            4 -> {
                when (attempt.value) {
                    1, 2 -> _attempt.value++
                }
            }

            3 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove = 1
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }

            2 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove = 2
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }

            1, 0 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove =  3
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }
        }
    }

//    private fun finishTask() {
//        _attempt.value = 3
//    }

    private fun removeDistractors(count: Int) {
        val distractors = _listShapes.value.filter { it !in selectedSet.value }
        val distractorsToRemove = distractors.take(count)
        val updatedList = _listShapes.value - distractorsToRemove.toSet()
        _listShapes.value = updatedList
    }

    fun setAnswersShapes(){
        _answersShapes.value += Triple(_attempt.value-1, selectedShapes.value, correctShapesCount)
        resetSelectedShapes()
        println("תשובות שנשמרו: ${_answersShapes.value}")

    }

    private fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
    }


    //concentration Question (3/10)
    private val _startButtonIsVisible = MutableStateFlow(true)
    val startButtonIsVisible: StateFlow<Boolean> =
        _startButtonIsVisible.asStateFlow()

    private val _answersConcentration = MutableStateFlow<List<Pair<Double, Int>>>(emptyList())

    private var numberAppearedAt: Long = 0L
    private var elapsedTime = 0.0

    private val _number = MutableStateFlow((0..9).random())
    val number: StateFlow<Int> = _number.asStateFlow()

    private val _isNumberClickable = MutableStateFlow(true)
    val isNumberClickable: StateFlow<Boolean> = _isNumberClickable.asStateFlow()


    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    fun startButtonSetVisible(visible: Boolean) {
        _startButtonIsVisible.value = visible
    }

    fun setAnswersConcentration(answer: Int) {
        if (_isNumberClickable.value) {
            val reactionTime = (Clock.System.now().toEpochMilliseconds() - numberAppearedAt) / 1000.0
            _answersConcentration.value += Pair(reactionTime, answer)
            _isNumberClickable.value = false
        }
    }

    fun startRandomNumberGeneration() {
        viewModelScope.launch {
            while (elapsedTime < 60) {
                delay(2500)
                numberAppearedAt = Clock.System.now().toEpochMilliseconds()
                _number.value = (0..9).random()
                _isNumberClickable.value = true
                elapsedTime += 2.5
            }
            _isFinished.value = true
        }
    }

    //naming Question (4/10)
    private val _answersNaming = MutableStateFlow<List<Pair<Pair<String, String>, Pair<String, String>>>>(emptyList())
    val answersNaming: StateFlow<List<Pair<Pair<String, String>, Pair<String, String>>>> = _answersNaming.asStateFlow()

    fun setAnswersNaming(answer1: String, answer2: String,a: String, b: String) {
        _answersNaming.value += Pair(Pair(answer1, a), Pair(answer2, b))
    }
}