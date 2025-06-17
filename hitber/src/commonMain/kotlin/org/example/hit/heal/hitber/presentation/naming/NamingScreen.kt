package org.example.hit.heal.hitber.presentation.naming

import TabletBaseScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_title
import dmt_proms.hitber.generated.resources.hitbear_continue
import getImageName
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.naming.components.NamingImages
import org.example.hit.heal.hitber.presentation.naming.components.NamingTextFields
import org.example.hit.heal.hitber.presentation.repetition.RepetitionScreen
import org.example.hit.heal.hitber.utils.InstructionText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class NamingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val fourthQuestionViewModel: FourthQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val answer1 by fourthQuestionViewModel.answer1.collectAsState()
        val answer2 by fourthQuestionViewModel.answer2.collectAsState()
        val selectedCouple by fourthQuestionViewModel.selectedCouple.collectAsState()

        val firstImageName = selectedCouple?.let { getImageName(it.first) } ?: ""
        val secondImageName = selectedCouple?.let { getImageName(it.second) } ?: ""

        TabletBaseScreen(title = stringResource(Res.string.fourth_question_hitbear_title),
            onNextClick = {
                fourthQuestionViewModel.fourthQuestionAnswer(
                    firstImageName,
                    secondImageName
                )
                viewModel.setFourthQuestion(
                    fourthQuestionViewModel.fourthQuestionAnswers,
                    getCurrentFormattedDateTime()
                )
                navigator?.push(RepetitionScreen())
            },
            question = 4,
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = primaryColor,
            content = {
                InstructionText(stringResource(Res.string.fourth_question_hitbear_instructions))
                NamingTextFields(answer1, answer2, fourthQuestionViewModel)
                NamingImages(selectedCouple)
            })
    }
}

