package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.all
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion1
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion2
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion3
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionAnswerHitberVersion4
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordDinner
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordGrandma
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordHad
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordI
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordWith
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberWordYesterday
import org.example.hit.heal.core.presentation.components.SlotState
import org.jetbrains.compose.resources.StringResource

class EightQuestionViewModel : ViewModel() {

    // List of valid correct answer sentences as resource IDs
    private val sentencesResourceId = listOf(
        eighthQuestionAnswerHitberVersion1,
        eighthQuestionAnswerHitberVersion2,
        eighthQuestionAnswerHitberVersion3,
        eighthQuestionAnswerHitberVersion4
    )

    private val _slotsWords = MutableStateFlow(
        listOf(
            SlotState(id = "0"),
            SlotState(id = "1"),
            SlotState(id = "2"),
            SlotState(id = "3"),
            SlotState(id = "4"),
            SlotState(id = "5")
        )
    )
    val slotsWords: StateFlow<List<SlotState>> = _slotsWords


    private val _draggableWords = MutableStateFlow(
        listOf(
            eighthQuestionHitberWordGrandma,
            eighthQuestionHitberWordHad,
            eighthQuestionHitberWordWith,
            eighthQuestionHitberWordYesterday,
            eighthQuestionHitberWordI,
            eighthQuestionHitberWordDinner
        )
    )
    val draggableWords: MutableStateFlow<List<StringResource>> = _draggableWords

    private val _droppedState = MutableStateFlow<Map<String, SlotState>>(emptyMap())
    val droppedState = _droppedState.asStateFlow()

    val allSlotsFilled = droppedState.map { map ->
        _slotsWords.value.all { slot -> map[slot.id]?.word != null }
    }

    var answerSentences = sentencesResourceId

    var answer: Boolean = false

    // Checks if user's composed sentence matches any valid answer
    fun eighthQuestionAnswer(sentences: List<String>) {
        val sortedSlots = _droppedState.value
            .toList()
            .sortedBy { it.first.toInt() }
            .mapNotNull { it.second.word }

        val userSentence = sortedSlots.joinToString(" ")

        println("userSentence $userSentence")

        answer = sentences.any { it.trim().equals(userSentence.trim(), ignoreCase = true) }
    }

    fun updateDroppedState(slotId: String, slotState: SlotState?) {
        val current = _droppedState.value.toMutableMap()
        if (slotState == null) {
            current.remove(slotId)
        } else {
            current[slotId] = slotState
        }
        _droppedState.value = current
    }

}