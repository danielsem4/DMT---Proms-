package org.example.hit.heal.hitber.presentation.timeAndPlace

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberTitle
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ShapeScreen
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.Questions
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val firstQuestionViewModel: FirstQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val allAnswersFinished by firstQuestionViewModel.allAnswersFinished.collectAsState()

        HorizontalTabletBaseScreen(
            title = stringResource(firstQuestionHitberTitle),
            onNextClick = {
                if (allAnswersFinished) {
                    viewModel.setFirstQuestion(firstQuestionViewModel.firstQuestion.value)
                    navigator?.push(ShapeScreen())
                }
            },
            question = 1,
            buttonText = stringResource(`continue`),
            buttonColor = if (allAnswersFinished) {
                primaryColor
            } else Color.Gray,
            content = {
                InstructionText(
                    stringResource(firstQuestionHitberInstructions ),
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Questions(firstQuestionViewModel)
                }
            })

    }
}

