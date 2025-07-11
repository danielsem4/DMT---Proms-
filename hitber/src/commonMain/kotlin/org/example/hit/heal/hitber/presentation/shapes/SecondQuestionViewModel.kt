package org.example.hit.heal.hitber.presentation.shapes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.presentation.shapes.model.Shape
import org.example.hit.heal.hitber.presentation.shapes.model.shapeList
import org.example.hit.heal.hitber.presentation.shapes.model.shapeSets

class SecondQuestionViewModel: ViewModel() {
    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<Shape>> = _listShapes.asStateFlow()

    private val _selectedSet = MutableStateFlow<List<Shape>>(emptyList())
    val selectedSet: StateFlow<List<Shape>> = _selectedSet.asStateFlow()

    private val _selectedShapes = MutableStateFlow<List<Shape>>(emptyList())
    val selectedShapes: StateFlow<List<Shape>> = _selectedShapes.asStateFlow()

    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    var secondQuestionAnswersList: ArrayList<Pair<Map<Int, String>, Int>> = arrayListOf()
    private var correctShapesCount = 0
    private var distractorToRemove = 0


    fun setRandomShapeSet() {
        val selectedTypes = shapeSets.random()
        _selectedSet.value = shapeList.filter { it.type in selectedTypes }
    }

    fun setSelectedShapes(shape: Shape) {
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
            5 -> _attempt.value = 3

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
                    1 -> distractorToRemove = 3
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }
        }
    }

    private fun removeDistractors(count: Int) {
        val distractors = _listShapes.value.filter { shape ->
            selectedSet.value.none { it.drawable == shape.drawable }
        }
        val distractorsToRemove = distractors.take(count)
        val updatedList = _listShapes.value - distractorsToRemove.toSet()
        _listShapes.value = updatedList
    }

    fun secondQuestionAnswer(
        questionNumber: Int,
        shapeNames: List<String>
    ) {
        val map = shapeNames.mapIndexed { index, shapeName ->
            (index + 1) to shapeName
        }.toMap()

        when (questionNumber) {
            2, 9 -> secondQuestionAnswersList.add(Pair(map, correctShapesCount))
        }

        _selectedShapes.value = emptyList()
        correctShapesCount = 0
    }


    fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        distractorToRemove = 0
        _attempt.value = 1
        _listShapes.value = shapeList
    }

    fun resetSelectedSet(){
        _selectedSet.value = emptyList()
    }
}