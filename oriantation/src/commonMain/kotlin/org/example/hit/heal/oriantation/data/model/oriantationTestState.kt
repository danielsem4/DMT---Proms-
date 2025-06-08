package org.example.hit.heal.oriantation.data.model

import androidx.compose.ui.graphics.ImageBitmap

data class OrientationTestState(
    val selectedNumber: Int = 0,
    val selectedSeason: String = "",

    val drawnShape: ImageBitmap? = null,
    val shapeResizeValue: Boolean = false,
    val shapesDragResult: Boolean = false,
    val feelingRate: Int = 0
)