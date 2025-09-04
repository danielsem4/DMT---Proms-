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
 * ViewModel for the shapes recognition task:
 * - 5 targets shown first (memorize)
 * - 10 shapes in action phase (5 targets + 5 distractors)
 * - On first wrong attempt: remove 2 distractors
 * - On second wrong: move on
 * - Save choices on each attempt
 */
class SecondQuestionViewModel : ViewModel() {

    // All available shapes (10 total)
    private val allShapes = shapeList

    // The shapes currently displayed in the action screen (starts with all 10)
    private val _listShapes = MutableStateFlow<List<Shape>>(allShapes)
    val listShapes: StateFlow<List<Shape>> = _listShapes.asStateFlow()

    // The chosen 5 targets to memorize/display on the first screen
    private val _selectedSet = MutableStateFlow<List<Shape>>(emptyList())
    val selectedSet: StateFlow<List<Shape>> = _selectedSet.asStateFlow()

    // User’s currently selected shapes (up to 5)
    private val _selectedShapes = MutableStateFlow<List<Shape>>(emptyList())
    val selectedShapes: StateFlow<List<Shape>> = _selectedShapes.asStateFlow()

    // Attempt index: 1 = first attempt, 2 = second/final attempt, 3 = done/advance
    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    // (question answers) pair of (index -> shapeName) and correct count, for Q2 & Q9
    var secondQuestionAnswersList: ArrayList<Pair<Map<Int, String>, Int>> = arrayListOf()

    private var correctShapesCount = 0

    /** Pick one of the predefined 5-shape sets for the memorize screen. */
    fun setRandomShapeSet() {
        val selectedTypes = shapeSets.random()
        _selectedSet.value = allShapes.filter { it.type in selectedTypes }
        resetSelectedShapes()
        _listShapes.value = allShapes // 5 targets + 5 distractors
    }

    /** Toggle selection with max 5 shapes. */
    fun setSelectedShapes(shape: Shape) {
        val current = _selectedShapes.value
        _selectedShapes.value =
            if (current.contains(shape)) {
                current.filterNot { it == shape } // toggle OFF
            } else if (current.size < MAX_SELECTIONS) {
                current + shape // toggle ON
            } else {
                current // at limit, ignore
            }
    }

    /** Count how many selected are part of the 5-target set. */
    fun calculateCorrectShapesCount() {
        correctShapesCount = selectedShapes.value.count { it in selectedSet.value }
    }

    /**
     * Update attempt based on correctness:
     * - If all 5 correct: attempt=3 (finish)
     * - Else if first attempt: attempt=2 (retry)
     * - Else (second attempt wrong): attempt=3 (finish)
     */
    fun updateTask() {
        when {
            correctShapesCount == 5 -> _attempt.value = 3
            _attempt.value == 1     -> _attempt.value = 2
            else                    -> _attempt.value = 3
        }
    }

    /** Remove exactly 2 distractors (not in target set) from the current display list. */
    private fun removeTwoDistractors() {
        val distractors = _listShapes.value.filter { it !in selectedSet.value }
        if (distractors.isEmpty()) return
        val toRemove = distractors.shuffled().take(2)
        _listShapes.value = _listShapes.value - toRemove.toSet()
    }

    /** Save the user's attempt (the picks) + how many were correct (for Q2 & Q9). */
    fun secondQuestionAnswer(questionNumber: Int, shapeNames: List<String>) {
        val map = shapeNames.mapIndexed { index, shapeName -> (index + 1) to shapeName }.toMap()
        if (questionNumber == 2 || questionNumber == 9) {
            secondQuestionAnswersList.add(map to correctShapesCount)
        }
    }

    /** Prepare the next attempt (only when going from attempt 1 -> 2). */
    fun setNewAttempt() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        removeTwoDistractors()
    }

    /** Reset selections/attempts and show the full 10 shapes again (keeps target set). */
    fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        _attempt.value = 1
        _listShapes.value = allShapes
    }

    /** Reset EVERYTHING including the selected target set. */
    fun resetAll() {
        resetSelectedShapes()
        _selectedSet.value = emptyList()
        secondQuestionAnswersList.clear()
    }
}
