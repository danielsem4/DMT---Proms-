package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectString
import kotlinx.serialization.Serializable

@Serializable
data class SeventhQuestionItem(
    val isCorrect: MeasureObjectBoolean = MeasureObjectBoolean(),
    val imageUrl: MeasureObjectString = MeasureObjectString()
)


