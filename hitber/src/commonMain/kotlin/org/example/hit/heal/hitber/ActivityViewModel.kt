package org.example.hit.heal.hitber

import DropDownItem
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
import org.example.hit.heal.hitber.shapes.components.shapeList
import org.example.hit.heal.hitber.shapes.components.shapeSets
import org.jetbrains.compose.resources.DrawableResource


class ActivityViewModel : ViewModel() {

    //Time and Place Question (1/10)
//    private val _focusedQuestion = MutableStateFlow<String?>(null)
//    val focusedQuestion: StateFlow<String?> = _focusedQuestion.asStateFlow()

    private val _answersMap = MutableStateFlow<Map<String, DropDownItem>>(emptyMap())
    val answersMap: StateFlow<Map<String, DropDownItem>> =
        _answersMap.asStateFlow()

//    fun setFocusedQuestion(question: String?) {
//        _focusedQuestion.value = question
//    }

    fun setAnswers(question: String, answer: DropDownItem) {
        _answersMap.value = _answersMap.value.toMutableMap().apply {
            this[question] = answer
        }
    }

    //Shape Question (2/10)
    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<DrawableResource>> = _listShapes

    private val _selectedSet = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedSet: StateFlow<List<DrawableResource>> = _selectedSet.asStateFlow()

    private val _selectedShapes = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedShapes: StateFlow<List<DrawableResource>> = _selectedShapes

    private val _attempt = MutableStateFlow(1)
    private val _correctShapesCount = MutableStateFlow(0)
    private val _distractorsCount = MutableStateFlow(10)

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

    fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
    }

    fun calculateCorrectShapesCount() {
        _correctShapesCount.value = selectedShapes.value.count { it in selectedSet.value }
    }

    fun updateTask() {
        when (_correctShapesCount.value) {
            5 -> {
                _isFinished.value = true
            }

            4 -> {
                when (_attempt.value) {
                    1, 2 -> _attempt.value++
                    3 -> _isFinished.value = true
                }
            }

            3 -> {
                _distractorsCount.value = when (_attempt.value++) {
                    1 -> 1
                    2 -> 3
                    else -> {
                        _isFinished.value = true
                        0
                    }
                }
                removeDistractors(_distractorsCount.value)
            }

            2 -> {
                _distractorsCount.value = when (_attempt.value++) {
                    1 -> 2
                    2 -> 4
                    else -> {
                        _isFinished.value = true
                        0
                    }
                }
                removeDistractors(_distractorsCount.value)
            }

            1, 0 -> {
                _distractorsCount.value = when (_attempt.value++) {
                    1 -> 3
                    2 -> 5
                    else -> {
                        _isFinished.value = true
                        0
                    }
                }
                removeDistractors(_distractorsCount.value)

            }
        }
    }

    private fun removeDistractors(count: Int) {
        val distractors = _listShapes.value.filter { it !in selectedSet.value }
        val distractorsToRemove = distractors.take(count)
        val updatedList = _listShapes.value - distractorsToRemove.toSet()
        _listShapes.value = updatedList
    }

    fun setIsFinished(bool: Boolean) {
        _isFinished.value = bool
    }

    //concentration Question (3/10)
    private val _startButtonIsVisible = MutableStateFlow(true)
    val startButtonIsVisible: StateFlow<Boolean> =
        _startButtonIsVisible.asStateFlow()

    private val _answers = MutableStateFlow<List<Int>>(emptyList())

    private val _number = MutableStateFlow((0..9).random())
    val number: StateFlow<Int> = _number.asStateFlow()
    private var elapsedTime by mutableStateOf(0)

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    fun startButtonSetVisible(visible: Boolean) {
        _startButtonIsVisible.value = visible
    }

    fun addAnswer(answer: Int) {
        _answers.value += answer
    }

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
}