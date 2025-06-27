package org.example.hit.heal.hitber.presentation.naming

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import getImageName
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberTitle
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.naming.components.NamingImages
import org.example.hit.heal.hitber.presentation.naming.components.NamingTextFields
import org.example.hit.heal.hitber.presentation.repetition.RepetitionScreen
import org.example.hit.heal.hitber.presentation.components.InstructionText
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

        HorizontalTabletBaseScreen(title = stringResource(fourthQuestionHitberTitle),
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
            buttonText = stringResource(`continue`),
            buttonColor = primaryColor,
            content = {
                InstructionText(stringResource(fourthQuestionHitberInstructions))
                NamingTextFields(answer1, answer2, fourthQuestionViewModel)
                NamingImages(selectedCouple)
            })
    }
}

