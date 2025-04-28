package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ThirdQuestionItem(
    val number: MeasureObjectInt = MeasureObjectInt(),
    val time: MeasureObjectInt = MeasureObjectInt(),
    val isPressed: MeasureObjectBoolean = MeasureObjectBoolean()
)

