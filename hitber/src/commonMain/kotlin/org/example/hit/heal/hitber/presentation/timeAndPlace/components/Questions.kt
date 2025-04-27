package org.example.hit.heal.hitber.presentation.timeAndPlace.components

import androidx.compose.runtime.Composable
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer1_7
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_10
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_11
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_12
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_7
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_8
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer2_9
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_7
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer3_8
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_10
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_7
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_8
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer4_9
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_7
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer5_8
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_5
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer6_6
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer7_1
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer7_2
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer7_3
import dmt_proms.hitber.generated.resources.first_question_hitbear_answer7_4
import dmt_proms.hitber.generated.resources.first_question_hitbear_question1
import dmt_proms.hitber.generated.resources.first_question_hitbear_question2
import dmt_proms.hitber.generated.resources.first_question_hitbear_question3
import dmt_proms.hitber.generated.resources.first_question_hitbear_question4
import dmt_proms.hitber.generated.resources.first_question_hitbear_question5
import dmt_proms.hitber.generated.resources.first_question_hitbear_question6
import dmt_proms.hitber.generated.resources.first_question_hitbear_question7
import org.example.hit.heal.hitber.ActivityViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun Questions(viewModel: ActivityViewModel) {

    val questionsData = listOf(
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question1),
            questionKey = "day",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer1_1),
                stringResource(Res.string.first_question_hitbear_answer1_2),
                stringResource(Res.string.first_question_hitbear_answer1_3),
                stringResource(Res.string.first_question_hitbear_answer1_4),
                stringResource(Res.string.first_question_hitbear_answer1_5),
                stringResource(Res.string.first_question_hitbear_answer1_6),
                stringResource(Res.string.first_question_hitbear_answer1_7)
            )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question2),
            questionKey = "month",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer2_1),
                stringResource(Res.string.first_question_hitbear_answer2_2),
                stringResource(Res.string.first_question_hitbear_answer2_3),
                stringResource(Res.string.first_question_hitbear_answer2_4),
                stringResource(Res.string.first_question_hitbear_answer2_5),
                stringResource(Res.string.first_question_hitbear_answer2_6),
                stringResource(Res.string.first_question_hitbear_answer2_7),
                stringResource(Res.string.first_question_hitbear_answer2_8),
                stringResource(Res.string.first_question_hitbear_answer2_9),
                stringResource(Res.string.first_question_hitbear_answer2_10),
                stringResource(Res.string.first_question_hitbear_answer2_11),
                stringResource(Res.string.first_question_hitbear_answer2_12)
                )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question3),
            questionKey = "year",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer3_1),
                stringResource(Res.string.first_question_hitbear_answer3_2),
                stringResource(Res.string.first_question_hitbear_answer3_3),
                stringResource(Res.string.first_question_hitbear_answer3_4),
                stringResource(Res.string.first_question_hitbear_answer3_5),
                stringResource(Res.string.first_question_hitbear_answer3_6),
                stringResource(Res.string.first_question_hitbear_answer3_7),
                stringResource(Res.string.first_question_hitbear_answer3_8)
                )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question4),
            questionKey = "country",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer4_1),
                stringResource(Res.string.first_question_hitbear_answer4_2),
                stringResource(Res.string.first_question_hitbear_answer4_3),
                stringResource(Res.string.first_question_hitbear_answer4_4),
                stringResource(Res.string.first_question_hitbear_answer4_5),
                stringResource(Res.string.first_question_hitbear_answer4_6),
                stringResource(Res.string.first_question_hitbear_answer4_7),
                stringResource(Res.string.first_question_hitbear_answer4_8),
                stringResource(Res.string.first_question_hitbear_answer4_9),
                stringResource(Res.string.first_question_hitbear_answer4_10)
                )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question5),
            questionKey = "city",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer5_1),
                stringResource(Res.string.first_question_hitbear_answer5_2),
                stringResource(Res.string.first_question_hitbear_answer5_3),
                stringResource(Res.string.first_question_hitbear_answer5_4),
                stringResource(Res.string.first_question_hitbear_answer5_5),
                stringResource(Res.string.first_question_hitbear_answer5_6),
                stringResource(Res.string.first_question_hitbear_answer5_7),
                stringResource(Res.string.first_question_hitbear_answer5_8)
            )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question6),
            questionKey = "place",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer6_1),
                stringResource(Res.string.first_question_hitbear_answer6_2),
                stringResource(Res.string.first_question_hitbear_answer6_3),
                stringResource(Res.string.first_question_hitbear_answer6_4),
                stringResource(Res.string.first_question_hitbear_answer6_5),
                stringResource(Res.string.first_question_hitbear_answer6_6)
            )
        ),
        QuestionData(
            question = stringResource(Res.string.first_question_hitbear_question7),
            questionKey = "survey",
            answers = listOf(
                stringResource(Res.string.first_question_hitbear_answer7_1),
                stringResource(Res.string.first_question_hitbear_answer7_2),
                stringResource(Res.string.first_question_hitbear_answer7_3),
                stringResource(Res.string.first_question_hitbear_answer7_4)
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
