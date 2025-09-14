package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.lifecycle.ViewModel
import core.data.model.MeasureObjectString
import core.data.model.evaluation.EvaluationObject
import core.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.hitber.data.model.FirstQuestion

class FirstQuestionViewModel : ViewModel() {

    private val _firstQuestion = MutableStateFlow(FirstQuestion())
    val firstQuestion: StateFlow<FirstQuestion> get() = _firstQuestion

    private val _allAnswersFinished = MutableStateFlow(false)
    val allAnswersFinished: StateFlow<Boolean> get() = _allAnswersFinished

    // Index of the current chunk of 3 questions
    private val _currentGroupIndex = MutableStateFlow(0)
    val currentGroupIndex: StateFlow<Int> get() = _currentGroupIndex

    private fun FirstQuestion.getAllFields() =
        listOf(day, month, year, country, city, place, survey)

    fun firstQuestionAnswer(field: String, answer: DropDownItem) {
        val current = _firstQuestion.value
        val date = getCurrentFormattedDateTime()

        val updated = when (field) {
            "day" -> current.copy(day = MeasureObjectString(101, answer.text, date))
            "month" -> current.copy(month = MeasureObjectString(102, answer.text, date))
            "year" -> current.copy(year = MeasureObjectString(109, answer.text, date))
            "country" -> current.copy(country = MeasureObjectString(104, answer.text, date))
            "city" -> current.copy(city = MeasureObjectString(105, answer.text, date))
            "place" -> current.copy(place = MeasureObjectString(106, answer.text, date))
            "survey" -> current.copy(survey = MeasureObjectString(108, answer.text, date))
            else -> current
        }

        _firstQuestion.value = updated
        _allAnswersFinished.value = updated.getAllFields().all { it.value.isNotBlank() }
    }

    /** Move to the next group if possible. Returns true if advanced, false if already at last group. */
    fun nextGroup(totalGroups: Int): Boolean {
        val next = _currentGroupIndex.value + 1
        return if (next in 0 until totalGroups) {
            _currentGroupIndex.value = next
            true
        } else {
            false
        }
    }

    /**
     * True if all questions in [keys] are answered in the provided [snapshot].
     * Passing the snapshot from the screen ensures Compose recomposes on answer changes.
     */
    fun isGroupCompleted(keys: List<String>, snapshot: FirstQuestion): Boolean {
        fun getValue(k: String): String = when (k) {
            "day" -> snapshot.day.value
            "month" -> snapshot.month.value
            "year" -> snapshot.year.value
            "country" -> snapshot.country.value
            "city" -> snapshot.city.value
            "place" -> snapshot.place.value
            "survey" -> snapshot.survey.value
            else -> ""
        }
        return keys.all { getValue(it).isNotBlank() }
    }
}
