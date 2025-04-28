package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class TenthQuestionType {
    data class TenthQuestionItem(
        val shape: MeasureObjectString = MeasureObjectString(),
        val grade: MeasureObjectDouble = MeasureObjectDouble(),
    ) : TenthQuestionType()
    data class TenthQuestionImage(val imageUrl: MeasureObjectString = MeasureObjectString()) : TenthQuestionType()
}
