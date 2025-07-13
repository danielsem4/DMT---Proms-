package org.example.hit.heal.hitber.presentation.naming

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import getImageName
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberTitle
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
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

        BaseScreen(
            title = stringResource(fourthQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "4/10",
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InstructionText(stringResource(fourthQuestionHitberInstructions))
                    NamingTextFields(answer1, answer2, fourthQuestionViewModel)
                    NamingImages(
                        selectedCouple,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                            .padding(vertical = 16.dp),
                        onClick = {
                            fourthQuestionViewModel.fourthQuestionAnswer(
                                firstImageName,
                                secondImageName
                            )
                            viewModel.setFourthQuestion(
                                fourthQuestionViewModel.fourthQuestionAnswers,
                                getCurrentFormattedDateTime()
                            )
                            navigator?.replace(RepetitionScreen())
                        }
                    )
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}

