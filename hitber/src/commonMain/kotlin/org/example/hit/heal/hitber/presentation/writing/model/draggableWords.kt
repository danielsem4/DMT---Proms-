package org.example.hit.heal.hitber.presentation.writing.model

import androidx.compose.ui.geometry.Offset
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordDinner
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordGrandma
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordHad
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordI
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordWith
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordYesterday
import org.jetbrains.compose.resources.StringResource

data class DraggableWordState(
    val word: StringResource,
    val initialOffset: Offset,
    var offset: Offset = initialOffset,
)

val draggableWordsList = listOf(
    DraggableWordState(eighthQuestionHitberWordGrandma, initialOffset = Offset(0.1f, 0.2f)),
    DraggableWordState(eighthQuestionHitberWordHad, initialOffset = Offset(0.25f, 0.2f)),
    DraggableWordState(eighthQuestionHitberWordWith, initialOffset = Offset(0.4f, 0.2f)),
    DraggableWordState(eighthQuestionHitberWordYesterday, initialOffset = Offset(0.55f, 0.2f)),
    DraggableWordState(eighthQuestionHitberWordI, initialOffset = Offset(0.7f, 0.2f)),
    DraggableWordState(eighthQuestionHitberWordDinner, initialOffset = Offset(0.85f, 0.2f))
)
