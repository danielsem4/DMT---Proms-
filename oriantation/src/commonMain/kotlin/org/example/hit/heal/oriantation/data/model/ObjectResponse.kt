package org.example.hit.heal.oriantation.data.model

import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectString
import core.data.model.MeasureObjectStringArray
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponse(
    @SerialName("selected_number") var selectedNumber: MeasureObjectString = MeasureObjectString(171),
    @SerialName("selected_seasons") var selectedSeasons: MeasureObjectStringArray = MeasureObjectStringArray(
        172
    ),
    @SerialName("isTriangleDragged") var isTriangleDragged: MeasureObjectBoolean = MeasureObjectBoolean(
        173
    ),
    @SerialName("isTriangleSizeChanged") var isTriangleSizeChanged: MeasureObjectBoolean = MeasureObjectBoolean(
        174
    ),
    @SerialName("xDrawing") var xDrawing: MeasureObjectString = MeasureObjectString(175),
    @SerialName("imageUrl") var imageUrl: MeasureObjectString = MeasureObjectString(206),
    @SerialName("healthLevel") var healthLevel: MeasureObjectString = MeasureObjectString(176)
)