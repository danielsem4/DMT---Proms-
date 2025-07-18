package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberTitle
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
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

        BaseScreen(
            title = stringResource(firstQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "1/10",
            content = {

                InstructionText(
                    stringResource(firstQuestionHitberInstructions ),
                )

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .padding(horizontal = paddingMd,).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(paddingLg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Questions(firstQuestionViewModel)
                }
                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally),
                        onClick = {
                                viewModel.setFirstQuestion(firstQuestionViewModel.firstQuestion.value)
                                navigator?.replace(ShapeScreen())
                        },
                        enabled = allAnswersFinished
                    )

            })

        RegisterBackHandler(this) {
            navigator?.pop()
        }

    }
}

