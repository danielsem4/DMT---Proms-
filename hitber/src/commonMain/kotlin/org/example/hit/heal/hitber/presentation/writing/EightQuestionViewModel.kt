package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.eighth_question_answer_hitbear_version_1
import dmt_proms.hitber.generated.resources.eighth_question_answer_hitbear_version_2
import dmt_proms.hitber.generated.resources.eighth_question_answer_hitbear_version_3
import dmt_proms.hitber.generated.resources.eighth_question_answer_hitbear_version_4
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.hitber.presentation.writing.model.WordSlotState
import org.example.hit.heal.hitber.presentation.writing.model.slotsList

class EightQuestionViewModel: ViewModel() {

    private val sentencesResourceId = listOf(
        Res.string.eighth_question_answer_hitbear_version_1,
        Res.string.eighth_question_answer_hitbear_version_2,
        Res.string.eighth_question_answer_hitbear_version_3,
        Res.string.eighth_question_answer_hitbear_version_4)

    private val _slotsWords = MutableStateFlow(slotsList)
    val slotsWords: StateFlow<List<WordSlotState>> = _slotsWords

    private val _activeSlotIndex = MutableStateFlow<Int?>(null)
    val activeSlotIndex: StateFlow<Int?> = _activeSlotIndex

    private val _sentence = MutableStateFlow<List<String>>(emptyList())

    private val _allFinished = MutableStateFlow(false)
    val allFinished: StateFlow<Boolean> = _allFinished

    var answerSentences = sentencesResourceId

    var answer: Boolean = false

    fun eighthQuestionAnswer(sentences: List<String>) {
        val userSentence = _sentence.value.joinToString(" ")

        answer = sentences.any { it.trim().equals(userSentence.trim(), ignoreCase = true) }
    }


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

    private fun updateSentence() {
        _sentence.value = _slotsWords.value
            .sortedBy { it.id }
            .mapNotNull { it.word }
    }

    private fun areAllSlotsFilled(): Boolean {
        return _slotsWords.value.all { it.word != null }
    }

}