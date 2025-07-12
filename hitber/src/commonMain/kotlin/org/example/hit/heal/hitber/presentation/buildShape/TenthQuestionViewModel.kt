package org.example.hit.heal.hitber.presentation.buildShape

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes
import kotlin.math.absoluteValue

class TenthQuestionViewModel : ViewModel() {

    val answer: ArrayList<Map<String, Double>> = ArrayList()

    fun uploadTenthQuestionImageAnswer(
        itemPositions: List<Offset>,
        draggableShapes: List<BuildShapes>,
        staticShapes: List<BuildShapes>,
        triangleWidth: Float,
        triangleHeight: Float
    ) {
        val results = getCorrectlyPlacedShapes(
            itemPositions,
            draggableShapes,
            staticShapes,
            triangleWidth,
            triangleHeight
        )

        results.forEach { (shape, resultFlag) ->
            tenthQuestionAnswer(shape.id, resultFlag.toDouble())
        }
    }

    private fun getCorrectlyPlacedShapes(
        itemPositions: List<Offset>,
        draggableShapes: List<BuildShapes>,
        staticShapes: List<BuildShapes>,
        containerWidth: Float,
        containerHeight: Float,
    ): List<Pair<BuildShapes, Int>> {

        val result = mutableListOf<Pair<BuildShapes, Int>>()
        if (itemPositions.isEmpty() || draggableShapes.isEmpty()) return result
        val basePosition = itemPositions[0]

        itemPositions.drop(1).withIndex().forEach { (index, position) ->
            val draggable = draggableShapes[index + 1]
            val static = staticShapes.find { it.id == draggable.id } ?: return@forEach

            val expectedOffsetX = static.xRatio * containerWidth
            val expectedOffsetY = static.yRatio * containerHeight

            val expectedX = basePosition.x + expectedOffsetX
            val expectedY = basePosition.y + expectedOffsetY

            val dx = (position.x - expectedX).absoluteValue
            val dy = (position.y - expectedY).absoluteValue

            val isCorrect = dx <= static.toleranceX && dy <= static.toleranceY
            val resultValue = if (isCorrect) 1 else 0

            result.add(Pair(draggable, resultValue))
        }
        return result
    }

    private fun tenthQuestionAnswer(shape: String, grade: Double) {
        answer.add(mapOf(shape to grade))
    }
}
