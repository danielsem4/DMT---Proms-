package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.ui.geometry.Offset
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_I
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_dinner
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_grandma
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_had
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_with
import dmt_proms.hitber.generated.resources.nine_question_word_hitbear_yesterday
import org.jetbrains.compose.resources.StringResource

data class DraggableWordState(
    val word: StringResource,
    val initialOffset: Offset,
    var offset: Offset = initialOffset,
)

val draggableWordsList = listOf(
    DraggableWordState(Res.string.nine_question_word_hitbear_grandma, initialOffset = Offset(0.1f, 0.2f)),
    DraggableWordState(Res.string.nine_question_word_hitbear_had, initialOffset = Offset(0.25f, 0.2f)),
    DraggableWordState(Res.string.nine_question_word_hitbear_with, initialOffset = Offset(0.4f, 0.2f)),
    DraggableWordState(Res.string.nine_question_word_hitbear_yesterday, initialOffset = Offset(0.55f, 0.2f)),
    DraggableWordState(Res.string.nine_question_word_hitbear_I, initialOffset = Offset(0.7f, 0.2f)),
    DraggableWordState(Res.string.nine_question_word_hitbear_dinner, initialOffset = Offset(0.85f, 0.2f))
)
