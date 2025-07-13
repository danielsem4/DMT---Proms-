package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.lifecycle.ViewModel
import core.data.model.MeasureObjectString
import core.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.hit.heal.hitber.data.model.FirstQuestion
import org.example.hit.heal.core.presentation.components.DropDownItem

class FirstQuestionViewModel : ViewModel() {

    private val _firstQuestion = MutableStateFlow(FirstQuestion())
    val firstQuestion: StateFlow<FirstQuestion> get() = _firstQuestion

    private val _allAnswersFinished = MutableStateFlow(false)
    val allAnswersFinished: StateFlow<Boolean> get() = _allAnswersFinished

    private fun FirstQuestion.getAllFields() = listOf(day, month, year, country, city, place, survey)

    fun firstQuestionAnswer(field: String, answer: DropDownItem) {
        val currentFirstQuestion = _firstQuestion.value
        val date = getCurrentFormattedDateTime()

        val updated = when (field) {
            "day" -> currentFirstQuestion.copy(day = MeasureObjectString(101, answer.text, date))
            "month" -> currentFirstQuestion.copy(month = MeasureObjectString(102, answer.text, date))
            "year" -> currentFirstQuestion.copy(year = MeasureObjectString(109, answer.text, date))
            "country" -> currentFirstQuestion.copy(country = MeasureObjectString(104, answer.text, date))
            "city" -> currentFirstQuestion.copy(city = MeasureObjectString(105, answer.text, date))
            "place" -> currentFirstQuestion.copy(place = MeasureObjectString(106, answer.text, date))
            "survey" -> currentFirstQuestion.copy(survey = MeasureObjectString(108, answer.text, date))
            else -> currentFirstQuestion
        }

        _firstQuestion.value = updated

        _allAnswersFinished.value = updated.getAllFields().all { it.value.isNotBlank() }
    }}
