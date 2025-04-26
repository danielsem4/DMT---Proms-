package org.example.hit.heal.hitber.presentation.timeAndPlace.components

import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.utils.getNow

class FirstQuestionManager {
    fun updateAnswer(field: String, answer: DropDownItem, currentCogData: CogData): CogData {
        val updated = when (field) {
            "day" -> currentCogData.firstQuestion.copy(day = MeasureObjectString(101, answer.text, getNow()))
            "month" -> currentCogData.firstQuestion.copy(month = MeasureObjectString(102, answer.text, getNow()))
            "year" -> currentCogData.firstQuestion.copy(year = MeasureObjectString(109, answer.text, getNow()))
            "country" -> currentCogData.firstQuestion.copy(country = MeasureObjectString(104, answer.text, getNow()))
            "city" -> currentCogData.firstQuestion.copy(city = MeasureObjectString(105, answer.text, getNow()))
            "place" -> currentCogData.firstQuestion.copy(place = MeasureObjectString(106, answer.text, getNow()))
            "survey" -> currentCogData.firstQuestion.copy(survey = MeasureObjectString(108, answer.text, getNow()))
            else -> currentCogData.firstQuestion
        }

        return currentCogData.copy(firstQuestion = updated)
    }

    fun allAnswersFinished(cogData: CogData): Boolean {
        val q = cogData.firstQuestion
        return listOf(
            q.day, q.month, q.year, q.country, q.city, q.place, q.survey
        ).all { it.value.isNotBlank() }
    }
}