package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectBoolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EighthQuestionItem(
    @SerialName("written sentence") val writtenSentence: MeasureObjectBoolean = MeasureObjectBoolean(),
)
