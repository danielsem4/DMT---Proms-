package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectInt
import kotlinx.serialization.Serializable

@Serializable
data class ThirdQuestionItem(
    val number: MeasureObjectInt = MeasureObjectInt(),
    val time: MeasureObjectInt = MeasureObjectInt(),
    val isPressed: MeasureObjectBoolean = MeasureObjectBoolean()
)

