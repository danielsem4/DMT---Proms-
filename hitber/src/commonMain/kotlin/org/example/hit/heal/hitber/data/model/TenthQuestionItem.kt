package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A sealed class to represent the two different types of data entries for the tenth question.
 */
@Serializable
sealed class TenthQuestionType

/**
 * Represents the collection of correct and incorrect shapes.
 */
@Serializable
@SerialName("TenthQuestionShape") // This name will be used during serialization
data class TenthQuestionShape(
    val dateTime: String,
    val measureObject: Int,
    val value: TenthQuestionShapeValue
) : TenthQuestionType()

/**
 * A container for the lists of correct and wrong shape names.
 */
@Serializable
data class TenthQuestionShapeValue(
    val correct: List<String>,
    val wrong: List<String>
)

/**
 * Represents the image URL associated with the tenth question.
 */
@Serializable
@SerialName("TenthQuestionImage") // This name will be used during serialization
data class TenthQuestionImage(
    val imageUrl: MeasureObjectString
) : TenthQuestionType()