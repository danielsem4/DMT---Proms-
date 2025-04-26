package org.example.hit.heal.hitber.presentation.shapes.components

data class ShapesSelectionState(
    val shapeList: List<Shape> = emptyList(),
    val selectedSet: List<Shape> = emptyList(),
    val selectedShapes: List<Shape> = emptyList(),
    val attempt: Int = 1,
)
