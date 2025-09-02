package org.example.hit.heal.hitber.presentation.timeAndPlace.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.core.presentation.components.DropDownQuestionField
import org.example.hit.heal.hitber.presentation.timeAndPlace.FirstQuestionViewModel
import org.example.hit.heal.hitber.presentation.timeAndPlace.QuestionData

@Composable
fun Questions(viewModel: FirstQuestionViewModel, questionList: List<QuestionData>) {
    val snapshot = viewModel.firstQuestion.collectAsState().value

    questionList.forEach { questionData ->
        val selectedValue = when (questionData.questionKey) {
            "day" -> snapshot.day.value
            "month" -> snapshot.month.value
            "year" -> snapshot.year.value
            "country" -> snapshot.country.value
            "city" -> snapshot.city.value
            "place" -> snapshot.place.value
            "survey" -> snapshot.survey.value
            else -> ""
        }

        DropDownQuestionField(
            question = questionData.question,
            dropDownItems = questionData.answers.map { DropDownItem(it) },
            selectedText = selectedValue,
            onItemClick = { selected ->
                viewModel.firstQuestionAnswer(questionData.questionKey, selected)
            }
        )
    }
}
