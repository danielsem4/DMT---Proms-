package org.example.hit.heal.hitber.presentation.dragAndDrop

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.presentation.dragAndDrop.model.instructions
import org.example.hit.heal.hitber.utils.isObjectInsideTargetArea
import org.jetbrains.compose.resources.StringResource

class SeventhQuestionViewModel : ViewModel() {

    private val _instructionsResourceId = MutableStateFlow<StringResource?>(null)
    val instructionsResourceId: StateFlow<StringResource?> get() = _instructionsResourceId.asStateFlow()

    private val _targetCircleColor = MutableStateFlow<Color?>(null)
    val targetCircleColor: StateFlow<Color?> get() = _targetCircleColor


    init {
        setRandomInstructions()
    }

    fun evaluateAnswer(
        circlePositions : List<Offset>,
        circleColors: List<Color>,
        targetColor: Color,
        targetBoxXRange: ClosedFloatingPointRange<Float>,
        targetBoxYRange: ClosedFloatingPointRange<Float>,
        radiusPx: Float
    ) {
        val diameter = 2 * radiusPx

        val correctCircleIndex = circleColors.indexOf(targetColor)
        val correctCirclePosition = circlePositions.getOrNull(correctCircleIndex)

        val draggablePosition = Offset(
            x = correctCirclePosition?.x ?: 0f,
            y = correctCirclePosition?.y ?: 0f
        )

        val isInside = isObjectInsideTargetArea(
            targetPosition = Offset(targetBoxXRange.start, targetBoxYRange.start),
            draggablePosition = draggablePosition,
            targetSize = (targetBoxXRange.endInclusive - targetBoxXRange.start) to (targetBoxYRange.endInclusive - targetBoxYRange.start),
            draggableSize = diameter to diameter,
            threshold = 50f,
            isCircle = true
        )
        seventhQuestionAnswer(isInside)
    }

    var answer: Boolean = false

    private fun setRandomInstructions() {
        val (randomInstruction, color) = instructions.random()
        _instructionsResourceId.value = randomInstruction
        _targetCircleColor.value = color
    }

    private fun seventhQuestionAnswer(isCorrect: Boolean) {
        answer = isCorrect
    }
}