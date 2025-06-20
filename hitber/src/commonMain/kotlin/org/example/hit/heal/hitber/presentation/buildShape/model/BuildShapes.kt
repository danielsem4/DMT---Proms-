package org.example.hit.heal.hitber.presentation.buildShape.model

import org.example.hit.heal.core.presentation.Resources.Icon.arrowKey
import org.example.hit.heal.core.presentation.Resources.Icon.blackCircle
import org.example.hit.heal.core.presentation.Resources.Icon.circle
import org.example.hit.heal.core.presentation.Resources.Icon.horizontalLine
import org.example.hit.heal.core.presentation.Resources.Icon.rotateStar
import org.example.hit.heal.core.presentation.Resources.Icon.triangle
import org.example.hit.heal.core.presentation.Resources.Icon.verticalLine
import org.jetbrains.compose.resources.DrawableResource

data class BuildShapes(
    val image: DrawableResource,
    val xRatio: Float,
    val yRatio: Float,
    val width: Float,
    val height: Float,
    val id: String,
    val toleranceX: Float = 0f,
    val toleranceY: Float = 0f
)

val staticShapesItem = listOf(
    BuildShapes(triangle, 0.05f, 0.4f, 0.4f, 0.4f, "triangle"),
    BuildShapes(rotateStar, 0.5f, -0.25f, 0.5f, 0.5f,"star",20f, 25f),
    BuildShapes(arrowKey, 0.5f, 0.45f, 0.8f, 0.5f, "arrow key",25f, 20f),
    BuildShapes(circle, 0.15f, 0.65f, 0.2f, 0.2f, "circle", 15f, 20f),
    BuildShapes(blackCircle, 0.6f, 0.65f, 0.2f, 0.2f, "black circle",15f, 20f),
    BuildShapes(verticalLine, 0.22f, 0.3f, 0.45f, 0.5f, "vertical line",20f, 15f),
    BuildShapes(horizontalLine, 0.2f, 0.1f, 0.5f, 0.8f, "horizontal line", 15f, 15f)
)

val draggableShapesItem = listOf(
    BuildShapes(triangle, 0.8f, 0.05f, 0.8f, 1f, "triangle"),
    BuildShapes(rotateStar, 0.87f, 0.7f, 0.5f, 0.5f, "star"),
    BuildShapes(arrowKey, 0.7f, 0.1f, 0.8f, 0.5f, "arrow key"),
    BuildShapes(circle, 0.95f, 0.55f, 0.2f, 0.2f, "circle"),
    BuildShapes(blackCircle, 0.88f, 0.55f, 0.2f, 0.2f, "black circle"),
    BuildShapes(verticalLine, 0.75f, 0.7f, 0.45f, 0.5f, "vertical line"),
    BuildShapes(horizontalLine, 0.65f, 0.4f, 0.5f, 0.8f, "horizontal line")
)