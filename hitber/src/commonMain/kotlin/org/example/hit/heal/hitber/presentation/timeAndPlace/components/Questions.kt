package org.example.hit.heal.hitber.presentation.timeAndPlace.components

import androidx.compose.runtime.Composable
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer1_7
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_10
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_11
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_12
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_7
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_8
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer2_9
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_7
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer3_8
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_10
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_7
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_8
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer4_9
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_7
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer5_8
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer6_6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer7_1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer7_2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer7_3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberAnswer7_4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion1
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion2
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion3
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion4
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion5
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion6
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberQuestion7
import org.example.hit.heal.hitber.presentation.timeAndPlace.FirstQuestionViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun Questions(viewModel: FirstQuestionViewModel) {

    val questionsData = listOf(
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion1),
            questionKey = "day",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer1_1),
                stringResource(firstQuestionHitberAnswer1_2),
                stringResource(firstQuestionHitberAnswer1_3),
                stringResource(firstQuestionHitberAnswer1_4),
                stringResource(firstQuestionHitberAnswer1_5),
                stringResource(firstQuestionHitberAnswer1_6),
                stringResource(firstQuestionHitberAnswer1_7)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion2),
            questionKey = "month",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer2_1),
                stringResource(firstQuestionHitberAnswer2_2),
                stringResource(firstQuestionHitberAnswer2_3),
                stringResource(firstQuestionHitberAnswer2_4),
                stringResource(firstQuestionHitberAnswer2_5),
                stringResource(firstQuestionHitberAnswer2_6),
                stringResource(firstQuestionHitberAnswer2_7),
                stringResource(firstQuestionHitberAnswer2_8),
                stringResource(firstQuestionHitberAnswer2_9),
                stringResource(firstQuestionHitberAnswer2_10),
                stringResource(firstQuestionHitberAnswer2_11),
                stringResource(firstQuestionHitberAnswer2_12)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion3),
            questionKey = "year",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer3_1),
                stringResource(firstQuestionHitberAnswer3_2),
                stringResource(firstQuestionHitberAnswer3_3),
                stringResource(firstQuestionHitberAnswer3_4),
                stringResource(firstQuestionHitberAnswer3_5),
                stringResource(firstQuestionHitberAnswer3_6),
                stringResource(firstQuestionHitberAnswer3_7),
                stringResource(firstQuestionHitberAnswer3_8)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion4),
            questionKey = "country",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer4_1),
                stringResource(firstQuestionHitberAnswer4_2),
                stringResource(firstQuestionHitberAnswer4_3),
                stringResource(firstQuestionHitberAnswer4_4),
                stringResource(firstQuestionHitberAnswer4_5),
                stringResource(firstQuestionHitberAnswer4_6),
                stringResource(firstQuestionHitberAnswer4_7),
                stringResource(firstQuestionHitberAnswer4_8),
                stringResource(firstQuestionHitberAnswer4_9),
                stringResource(firstQuestionHitberAnswer4_10)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion5),
            questionKey = "city",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer5_1),
                stringResource(firstQuestionHitberAnswer5_2),
                stringResource(firstQuestionHitberAnswer5_3),
                stringResource(firstQuestionHitberAnswer5_4),
                stringResource(firstQuestionHitberAnswer5_5),
                stringResource(firstQuestionHitberAnswer5_6),
                stringResource(firstQuestionHitberAnswer5_7),
                stringResource(firstQuestionHitberAnswer5_8)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion6),
            questionKey = "place",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer6_1),
                stringResource(firstQuestionHitberAnswer6_2),
                stringResource(firstQuestionHitberAnswer6_3),
                stringResource(firstQuestionHitberAnswer6_4),
                stringResource(firstQuestionHitberAnswer6_5),
                stringResource(firstQuestionHitberAnswer6_6)
            )
        ),
        QuestionData(
            question = stringResource(firstQuestionHitberQuestion7),
            questionKey = "survey",
            answers = listOf(
                stringResource(firstQuestionHitberAnswer7_1),
                stringResource(firstQuestionHitberAnswer7_2),
                stringResource(firstQuestionHitberAnswer7_3),
                stringResource(firstQuestionHitberAnswer7_4)
            )
        )
    )


    questionsData.forEach { questionData ->
        TimeAndPlaceQuestion(
            question = questionData.question,
            dropDownItems = questionData.answers.map { DropDownItem(it) },
            onItemClick = { selectedAnswer ->
                viewModel.firstQuestionAnswer(questionData.questionKey, selectedAnswer)
            }
        )
    }
}

data class QuestionData(val question: String, val questionKey: String, val answers: List<String>)
