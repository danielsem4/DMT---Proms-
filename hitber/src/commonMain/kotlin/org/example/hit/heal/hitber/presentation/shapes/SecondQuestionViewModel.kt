package org.example.hit.heal.hitber.presentation.shapes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.presentation.shapes.model.Shape
import org.example.hit.heal.hitber.presentation.shapes.model.shapeList
import org.example.hit.heal.hitber.presentation.shapes.model.shapeSets

private const val MAX_SELECTIONS = 5

/**
 * Shapes viewmodel
 */

class SecondQuestionViewModel : ViewModel() {
    private val allShapes = shapeList
    private val _listShapes = MutableStateFlow<List<Shape>>(allShapes)
    val listShapes: StateFlow<List<Shape>> = _listShapes.asStateFlow()
    private val _selectedSet = MutableStateFlow<List<Shape>>(emptyList())
    val selectedSet: StateFlow<List<Shape>> = _selectedSet.asStateFlow()
    private val _selectedShapes = MutableStateFlow<List<Shape>>(emptyList())
    val selectedShapes: StateFlow<List<Shape>> = _selectedShapes.asStateFlow()
    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()
    var secondQuestionAnswersList: ArrayList<Pair<Map<Int, String>, Int>> = arrayListOf()
    private val _showMaxSelectionToast = MutableStateFlow(false)
    val showMaxSelectionToast: StateFlow<Boolean> = _showMaxSelectionToast.asStateFlow()
    private var correctShapesCount = 0

    fun setRandomShapeSet() {
        val selectedTypes = shapeSets.random()
        _selectedSet.value = allShapes.filter { it.type in selectedTypes }
        resetSelectedShapes()
        _listShapes.value = allShapes
    }

    fun setSelectedShapes(shape: Shape) {
        val current = _selectedShapes.value
        if (current.contains(shape)) {
            _selectedShapes.value = current.filterNot { it == shape }
        } else if (current.size < MAX_SELECTIONS) {
            _selectedShapes.value = current + shape
        } else {
            _showMaxSelectionToast.value = true
        }
    }

    fun onToastShown() {
        _showMaxSelectionToast.value = false
    }

    fun calculateCorrectShapesCount() {
        correctShapesCount = selectedShapes.value.count { it in selectedSet.value }
    }


    fun updateTask() {
        when {
            correctShapesCount == 5 -> _attempt.value = 3
            _attempt.value == 1 -> _attempt.value = 2
            else -> _attempt.value = 3
        }
    }

    private fun removeTwoDistractors() {
        val distractors = _listShapes.value.filter { it !in selectedSet.value }
        if (distractors.isEmpty()) return
        val toRemove = distractors.shuffled().take(2)
        _listShapes.value = _listShapes.value - toRemove.toSet()
    }

    fun secondQuestionAnswer(questionNumber: Int, shapeNames: List<String>) {
        val map = shapeNames.mapIndexed { index, shapeName -> (index + 1) to shapeName }.toMap()
        if (questionNumber == 2 || questionNumber == 9) {
            secondQuestionAnswersList.add(map to correctShapesCount)
        }
    }

    fun setNewAttempt() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        removeTwoDistractors()
    }

    fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        _attempt.value = 1
        _listShapes.value = allShapes
    }

    fun resetAll() {
        resetSelectedShapes()
        _selectedSet.value = emptyList()
        secondQuestionAnswersList.clear()
    }
}