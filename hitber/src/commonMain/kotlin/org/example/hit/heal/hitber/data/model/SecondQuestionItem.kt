package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectInt
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SecondQuestionItem(
    @SerialName("selected_shapes") val selectedShapes: SelectedShapesStringList = SelectedShapesStringList(),
    @SerialName("wrong_shapes") val wrongShapes: MeasureObjectInt = MeasureObjectInt(),
)

