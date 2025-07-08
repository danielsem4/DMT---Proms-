package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectDouble
import core.data.model.MeasureObjectString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
sealed class TenthQuestionType {
    @Serializable
    @SerialName("TenthQuestionItem")
    data class TenthQuestionItem(
        val shape: MeasureObjectString = MeasureObjectString(),
        val grade: MeasureObjectDouble = MeasureObjectDouble(),
    ) : TenthQuestionType()

    @Serializable
    @SerialName("TenthQuestionImage")
    data class TenthQuestionImage(
        val imageUrl: MeasureObjectString = MeasureObjectString()
    ) : TenthQuestionType()
}

