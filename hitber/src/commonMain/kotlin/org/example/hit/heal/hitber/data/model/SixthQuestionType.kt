package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class SixthQuestionType {
    data class SixthQuestionItem(
        val fridgeOpened: MeasureObjectBoolean = MeasureObjectBoolean(),
        val correctProductDragged: MeasureObjectBoolean = MeasureObjectBoolean(),
        val placedOnCorrectNap: MeasureObjectBoolean = MeasureObjectBoolean()
    ) : SixthQuestionType()
    data class SixthQuestionImage(val imageUrl: MeasureObjectString = MeasureObjectString()) : SixthQuestionType()
}

