package org.example.hit.heal.oriantation.data.model

data class OrientationTestState(
    val selectedNumber: Int = 0,
    val selectedSeason: String = "",

    val drawnShape: String = "",
    val shapeResizeValue: Boolean = false,
    val shapesDragResult: Boolean = false,
    val feelingRate: Int = 0
)