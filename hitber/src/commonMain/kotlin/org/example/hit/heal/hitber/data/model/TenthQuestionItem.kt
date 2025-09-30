package org.example.hit.heal.hitber.data.model

import core.data.model.MeasureObjectString
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject


@Serializable(with = TenthQuestionTypeSerializer::class)
sealed class TenthQuestionType {
    @Serializable
    data class TenthQuestionItem(
        @SerialName("Tenth-Question-shape")
        val payload: TenthQuestionShape = TenthQuestionShape()
    ) : TenthQuestionType()

    @Serializable
    data class TenthQuestionImage(
        val imageUrl: MeasureObjectString = MeasureObjectString()
    ) : TenthQuestionType()
}

/**
 * Custom polymorphic serializer that decides based on field names,
 * and does NOT add a "type" discriminator to JSON.
 */
object TenthQuestionTypeSerializer :
    JsonContentPolymorphicSerializer<TenthQuestionType>(TenthQuestionType::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out TenthQuestionType> {
        return when {
            "Tenth-Question-shape" in element.jsonObject -> TenthQuestionType.TenthQuestionItem.serializer()
            "imageUrl" in element.jsonObject -> TenthQuestionType.TenthQuestionImage.serializer()
            else -> throw SerializationException("Unknown TenthQuestionType: $element")
        }
    }
}

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