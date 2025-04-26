package org.example.hit.heal.hitber.presentation.writing.components

import org.jetbrains.compose.resources.StringResource

data class EighthQuestionState(
    val slots: List<WordSlotState> = emptyList(),
    val activeSlotIndex: Int? = null,
    val sentence: List<String> = emptyList(),
    val allFinished: Boolean = false,
    val answerSentences: List<StringResource> = emptyList()
)
