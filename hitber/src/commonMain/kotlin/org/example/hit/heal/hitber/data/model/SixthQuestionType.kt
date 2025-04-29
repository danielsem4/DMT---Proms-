package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SixthQuestionType {
    @Serializable
    @SerialName("SixthQuestionItem")
    data class SixthQuestionItem(
        val fridgeOpened: MeasureObjectBoolean = MeasureObjectBoolean(),
        val correctProductDragged: MeasureObjectBoolean = MeasureObjectBoolean(),
        val placedOnCorrectNap: MeasureObjectBoolean = MeasureObjectBoolean()
    ) : SixthQuestionType()

    @Serializable
    @SerialName("SixthQuestionImage")
    data class SixthQuestionImage(
        val imageUrl: MeasureObjectString = MeasureObjectString()
    ) : SixthQuestionType()
}


