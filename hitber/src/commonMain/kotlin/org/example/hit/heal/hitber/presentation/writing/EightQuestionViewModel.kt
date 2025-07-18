package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion1
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion2
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion3
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion4
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.hitber.presentation.writing.model.WordSlotState
import org.example.hit.heal.hitber.presentation.writing.model.slotsList

class EightQuestionViewModel: ViewModel() {

    // List of valid correct answer sentences as resource IDs
    private val sentencesResourceId = listOf(
        eighthQuestionAnswerHitberVersion1,
        eighthQuestionAnswerHitberVersion2,
        eighthQuestionAnswerHitberVersion3,
        eighthQuestionAnswerHitberVersion4)

    private val _slotsWords = MutableStateFlow(slotsList)
    val slotsWords: StateFlow<List<WordSlotState>> = _slotsWords

    private val _activeSlotIndex = MutableStateFlow<Int?>(null)
    val activeSlotIndex: StateFlow<Int?> = _activeSlotIndex

    private val _sentence = MutableStateFlow<List<String>>(emptyList())

    private val _allFinished = MutableStateFlow(false)
    val allFinished: StateFlow<Boolean> = _allFinished

    var answerSentences = sentencesResourceId

    var answer: Boolean = false

    // Checks if user's composed sentence matches any valid answer
    fun eighthQuestionAnswer(sentences: List<String>) {
        val userSentence = _sentence.value.joinToString(" ")

        answer = sentences.any { it.trim().equals(userSentence.trim(), ignoreCase = true) }
    }

    // Determines if given word position overlaps any slot on the screen, returns slot index or null
    fun isWordOnSlot(
        wordState: Offset,
        screenSize: IntSize,
        density: Density,
    ): Int? {
        val foundIndex = _slotsWords.value.indexOfFirst { slot ->
            val slotWidthPx = with(density) { 150.dp.toPx() }
            val slotHeightPx = with(density) { 100.dp.toPx() }

            val slotLeft =   slot.initialOffset.x * screenSize.width - slotWidthPx / 2
            val slotRight =   slot.initialOffset.x * screenSize.width + slotWidthPx / 2
            val slotTop = slot.initialOffset.y * screenSize.height - slotHeightPx / 2
            val slotBottom = slot.initialOffset.y * screenSize.height + slotHeightPx / 2

            wordState.x * screenSize.width in slotLeft..slotRight &&
                    wordState.y * screenSize.height in slotTop..slotBottom
        }.takeIf { it >= 0 }

        _activeSlotIndex.value = foundIndex
        return foundIndex
    }

    // Assigns a word to a slot if the slot is empty, updates sentence and completion state
    fun updateWordInSlot(word: String, slotId: Int) {
        _slotsWords.value = _slotsWords.value.mapIndexed { index, slot ->

            if (index == slotId && slot.word == null) {
                slot.copy(word = word, color = backgroundColor)
            } else {
                slot
            }
        }
        updateSentence()
        _allFinished.value = areAllSlotsFilled()

    }

    // Resets the word in a given slot to null and sets slot color to gray
    fun resetSlot(slotIndex: Int) {
        _slotsWords.value = _slotsWords.value.mapIndexed { index, slot ->
            if (index == slotIndex) {
                slot.copy(word = null, color = Color.Gray)
            } else {
                slot
            }
        }
        updateSentence()
    }

    // Updates the color of a slot based on whether it is active and empty or just empty
    fun updateSlotColor(slotId: Int) {
        _slotsWords.value = _slotsWords.value.map { slot ->
            if (slot.id == slotId && slot.word == null) {
                slot.copy(color = backgroundColor)
            } else if (slot.word == null) {
                slot.copy(color = Color.Gray)
            } else {
                slot
            }
        }
    }

    // Reconstructs the current sentence based on the words placed in slots (sorted by slot ID)
    private fun updateSentence() {
        _sentence.value = _slotsWords.value
            .sortedBy { it.id }
            .mapNotNull { it.word }
    }

    // Checks if all slots are filled with words
    private fun areAllSlotsFilled(): Boolean {
        return _slotsWords.value.all { it.word != null }
    }

}