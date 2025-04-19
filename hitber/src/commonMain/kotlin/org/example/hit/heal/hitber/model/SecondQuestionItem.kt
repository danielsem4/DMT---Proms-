package org.example.hit.heal.hitber.model

import kotlinx.serialization.SerialName


data class SecondQuestionItem(
    @SerialName("selected_shapes") val selectedShapes: SelectedShapesStringList = SelectedShapesStringList(),
    @SerialName("wrong_shapes") val wrongShapes: MeasureObjectInt = MeasureObjectInt(),
)

