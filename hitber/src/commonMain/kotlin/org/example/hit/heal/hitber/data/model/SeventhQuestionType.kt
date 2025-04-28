package org.example.hit.heal.hitber.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SeventhQuestionType {
    data class SeventhQuestionItem(@SerialName("Seven-Question-drag-and-drop") val isCorrect: MeasureObjectBoolean = MeasureObjectBoolean()) : SeventhQuestionType()
    data class SeventhQuestionImage(val imageUrl: MeasureObjectString = MeasureObjectString()) : SeventhQuestionType()
}

