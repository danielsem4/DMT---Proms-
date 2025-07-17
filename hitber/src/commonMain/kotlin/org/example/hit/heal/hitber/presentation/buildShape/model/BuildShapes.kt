package org.example.hit.heal.hitber.presentation.buildShape.model

import org.example.hit.heal.core.presentation.Resources.Icon.arrowUpIcon
import org.example.hit.heal.core.presentation.Resources.Icon.filledCircleIcon
import org.example.hit.heal.core.presentation.Resources.Icon.horizontalStrokeIcon
import org.example.hit.heal.core.presentation.Resources.Icon.outlineCircleIcon
import org.example.hit.heal.core.presentation.Resources.Icon.rotateStar
import org.example.hit.heal.core.presentation.Resources.Icon.triangleIcon
import org.example.hit.heal.core.presentation.Resources.Icon.verticalStrokeIcon
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

// List of static shapes with predefined target positions and tolerances.
// These represent the correct positions where the draggable shapes should be placed.
val staticShapesItem = listOf(
    BuildShapes(triangleIcon, 0.05f, 0.4f, 0.4f, 0.4f, "triangle"),
    BuildShapes(rotateStar, 0.5f, -0.25f, 0.5f, 0.5f,"star",25f, 30f),
    BuildShapes(arrowUpIcon, 0.5f, 0.45f, 0.8f, 0.5f, "arrow key",30f, 25f),
    BuildShapes(outlineCircleIcon, 0.15f, 0.65f, 0.21f, 0.21f, "circle", 25f, 25f),
    BuildShapes(filledCircleIcon, 0.6f, 0.65f, 0.2f, 0.2f, "black circle",25f, 25f),
    BuildShapes(verticalStrokeIcon, 0.07f, 0.12f, 0.7f, 0.8f, "vertical line",20f, 40f),
    BuildShapes(horizontalStrokeIcon, 0.2f, 0.12f, 0.5f, 0.8f, "horizontal line", 35f, 40f)
)

// List of draggable shapes initialized at starting positions.
// These shapes can be moved by the user to match the static target shapes.
val draggableShapesItem = listOf(
    BuildShapes(triangleIcon, 0.8f, 0.05f, 0.8f, 1f, "triangle"),
    BuildShapes(rotateStar, 0.87f, 0.7f, 0.5f, 0.5f, "star"),
    BuildShapes(arrowUpIcon, 0.7f, 0.1f, 0.8f, 0.5f, "arrow key"),
    BuildShapes(outlineCircleIcon, 0.95f, 0.55f, 0.21f, 0.21f, "circle"),
    BuildShapes(filledCircleIcon, 0.88f, 0.55f, 0.2f, 0.2f, "black circle"),
    BuildShapes(verticalStrokeIcon, 0.75f, 0.55f, 0.7f, 0.8f, "vertical line"),
    BuildShapes(horizontalStrokeIcon, 0.65f, 0.4f, 0.5f, 0.8f, "horizontal line")
)