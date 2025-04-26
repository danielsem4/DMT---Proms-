package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.hitber.data.model.EighthQuestionItem
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.utils.getNow

class EighthQuestionManager{

    fun isWordOnSlot(
        wordState: Offset,
        screenSize: IntSize,
        density: Density,
        slots: List<WordSlotState>,
    ): Int? {
        val foundIndex = slots.indexOfFirst { slot ->
            val slotWidthPx = with(density) { 150.dp.toPx() }
            val slotHeightPx = with(density) { 100.dp.toPx() }

            val slotLeft = slot.initialOffset.x * screenSize.width - slotWidthPx / 2
            val slotRight = slot.initialOffset.x * screenSize.width + slotWidthPx / 2
            val slotTop = slot.initialOffset.y * screenSize.height - slotHeightPx / 2
            val slotBottom = slot.initialOffset.y * screenSize.height + slotHeightPx / 2

            wordState.x * screenSize.width in slotLeft..slotRight &&
                    wordState.y * screenSize.height in slotTop..slotBottom
        }.takeIf { it >= 0 }

        return foundIndex
    }

    fun updateWordInSlot(word: String, slotId: Int, slots: List<WordSlotState>): List<WordSlotState> {
        return slots.mapIndexed { index, slot ->
            if (index == slotId && slot.word == null) {
                slot.copy(word = word, color = backgroundColor)
            } else {
                slot
            }
        }
    }

    fun resetSlot(slotIndex: Int, slots: List<WordSlotState>): List<WordSlotState> {
        return slots.mapIndexed { index, slot ->
            if (index == slotIndex) {
                slot.copy(word = null, color = Color.Gray)
            } else {
                slot
            }
        }
    }

    fun updateSlotColor(slotId: Int, slots: List<WordSlotState>): List<WordSlotState> {
        return slots.map { slot ->
            if (slot.id == slotId && slot.word == null) {
                slot.copy(color = backgroundColor)
            } else if (slot.word == null) {
                slot.copy(color = Color.Gray)
            } else {
                slot
            }
        }
    }

    fun eighthQuestionAnswer(userSentence: List<String>, sentences: List<String>): EighthQuestionItem {
        val userSentenceString = userSentence.joinToString(" ")
        println("User sentence: '$userSentenceString'")

        val isCorrect = sentences.any { it.trim().equals(userSentenceString.trim(), ignoreCase = true) }
        val answer = MeasureObjectBoolean(value = isCorrect, dateTime = getNow())
        return EighthQuestionItem(writtenSentence = answer)
    }
}
