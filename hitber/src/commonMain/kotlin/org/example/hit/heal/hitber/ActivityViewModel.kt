package org.example.hit.heal.hitber

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem


class ActivityViewModel: ViewModel() {

    private val _timeAndPlaceFocusedQuestion = mutableStateOf<String?>(null)
    val timeAndPlaceFocusedQuestion: State<String?> = _timeAndPlaceFocusedQuestion

    private val _timeAndPlaceAnswersMap = mutableStateOf(mutableMapOf<String, DropDownItem>())
    val timeAndPlaceAnswersMap: State<Map<String, DropDownItem>> = _timeAndPlaceAnswersMap

    fun timeAndPlaceSetFocusedQuestion(question: String?) {
        _timeAndPlaceFocusedQuestion.value = question
    }

    fun timeAndPlaceSetAnswer(question: String, answer: DropDownItem) {
        _timeAndPlaceAnswersMap.value = _timeAndPlaceAnswersMap.value.toMutableMap().apply {
            this[question] = answer
        }
    }
}