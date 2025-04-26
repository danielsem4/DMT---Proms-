package org.example.hit.heal.hitber.presentation.shapes.components

class ShapesSelectionManager {

    var correctShapesCount = 0

    fun setRandomShapeSet(state: ShapesSelectionState): ShapesSelectionState {
        val selectedTypes = shapeSets.random()
        val selectedSet = state.shapeList.filter { it.type in selectedTypes }
        return state.copy(selectedSet = selectedSet)
    }

    fun setSelectedShapes(state: ShapesSelectionState, shape: Shape): ShapesSelectionState {
        val currentSelected = state.selectedShapes.toMutableList()
        if (currentSelected.size >= 5) {
            if (currentSelected.contains(shape)) {
                currentSelected.remove(shape)
            }
        } else {
            if (currentSelected.contains(shape)) {
                currentSelected.remove(shape)
            } else {
                currentSelected.add(shape)
            }
        }
        return state.copy(selectedShapes = currentSelected)
    }

    fun calculateCorrectShapesCount(state: ShapesSelectionState) {
        correctShapesCount = state.selectedShapes.count { it in state.selectedSet }
    }

    fun updateTask(state: ShapesSelectionState): ShapesSelectionState {
        return when (correctShapesCount) {
            5 -> {
                state.copy(attempt = 4)
            }
            4 -> {
                if (state.attempt in 1..3) {
                    state.copy(attempt = state.attempt + 1)
                } else state
            }
            3 -> {
                val currentAttempt = state.attempt
                val distractorCount = when (currentAttempt) {
                    1 -> 1
                    2 -> 2
                    else -> 0
                }
                val updatedState = handleDistractor(state, distractorCount)
                updatedState.copy(attempt = currentAttempt + 1)
            }
            2 -> {
                val currentAttempt = state.attempt
                val distractorCount = when (currentAttempt) {
                    1 -> 2
                    2 -> 2
                    else -> 0
                }
                val updatedState = handleDistractor(state, distractorCount)
                updatedState.copy(attempt = currentAttempt + 1)
            }
            else -> {
                val currentAttempt = state.attempt
                val distractorCount = when (currentAttempt) {
                    1 -> 3
                    2 -> 2
                    else -> 0
                }
                val updatedState = handleDistractor(state, distractorCount)
                updatedState.copy(attempt = currentAttempt + 1)
            }
        }
    }

    private fun handleDistractor(state: ShapesSelectionState, count: Int): ShapesSelectionState {
        val distractors = state.shapeList.filter { shape ->
            state.selectedSet.none { it.drawable == shape.drawable }
        }
        val updatedList = state.shapeList - distractors.take(count).toSet()
        return state.copy(shapeList = updatedList)

    }

    fun resetSecondQuestion(state: ShapesSelectionState): ShapesSelectionState {
        correctShapesCount = 0
        return state.copy(
            shapeList = shapeList,
            selectedSet = state.selectedSet,
            selectedShapes = emptyList(),
            attempt = 1,
        )
    }

    fun resetSelectedShapes(state: ShapesSelectionState): ShapesSelectionState {
        correctShapesCount = 0
        return state.copy(selectedShapes = emptyList())
    }
}
