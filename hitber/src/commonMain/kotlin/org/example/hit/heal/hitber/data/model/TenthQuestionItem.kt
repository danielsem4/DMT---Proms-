package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TenthQuestionEntry(
    @SerialName("Tenth-Question-shape")
    val shapePayload: TenthQuestionShape? = null,
    val imageUrl: MeasureObjectString? = null
)

@Serializable
data class TenthQuestionShape(
    @SerialName("DateTime")
    val dateTime: String = "2030-12-12 12:12:12.6",
    val measureObject: Int = 0,

    @SerialName("value")
    val value: TenthQuestionShapeValue = TenthQuestionShapeValue()
)

@Serializable
data class TenthQuestionShapeValue(
    val correct: String = "[]",
    val wrong: String = "[]"
)