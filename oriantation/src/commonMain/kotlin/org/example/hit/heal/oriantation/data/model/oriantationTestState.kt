package org.example.hit.heal.oriantation.data.model

import kotlinx.serialization.SerialName

data class OrientationTestState(
    @SerialName("selectedNumber") var selectedNumber: Int = 0,
    @SerialName("selectedSeason") var selectedSeason: String = "",

    @SerialName("drawnShape") var drawnShape: ByteArray? = null,
    @SerialName("shapeResizValue") var shapeResizeValue: Boolean = false,
    @SerialName("shapesDragResult") var shapesDragResult: Boolean = false,
    @SerialName("feelingRate") var feelingRate: Int = 0
)