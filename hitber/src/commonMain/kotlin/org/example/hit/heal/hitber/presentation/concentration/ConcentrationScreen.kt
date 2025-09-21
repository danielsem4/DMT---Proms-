package org.example.hit.heal.hitber.presentation.concentration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.start
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberFinishTask
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberTitle
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.concentration.components.RandomNumberScreen
import org.example.hit.heal.hitber.presentation.naming.NamingScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 *
 */

class ConcentrationScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val thirdQuestionViewModel: ThirdQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val buttonVisible by thirdQuestionViewModel.startButtonIsVisible.collectAsState()
        val number by thirdQuestionViewModel.number.collectAsState()
        val isFinished by thirdQuestionViewModel.isFinished.collectAsState()
        val isNumberClickable by thirdQuestionViewModel.isNumberClickable.collectAsState()

        BaseScreen(
            title = stringResource(thirdQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "3/10",
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingMd),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InstructionText(
                        stringResource(thirdQuestionHitberInstructions),
                    )

                    // Show start button if the task hasn't started
                    if (buttonVisible) {
                        RoundedButton(
                            text = stringResource(start),
                            modifier = Modifier.width(200.dp).padding(bottom = paddingSm),
                            onClick = {
                                // Hide start button and start generating random numbers
                                thirdQuestionViewModel.startButtonSetVisible(false)
                                thirdQuestionViewModel.startRandomNumberGeneration()
                            }
                        )
                        // Empty white box to fill space when button is visible
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(color = Color.White)
                        )
                    } else {
                        // When task is finished, show completion message centered
                        if (isFinished) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(color = Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    stringResource(thirdQuestionHitberFinishTask),
                                    color = primaryColor,
                                    fontSize = LARGE,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            // While running, show numbers for user to click
                            RandomNumberScreen(
                                number = number,
                                isClickable = isNumberClickable,
                                onNumberClicked = { thirdQuestionViewModel.thirdQuestionAnswer(it) },
                                modifier = Modifier.weight(1f).fillMaxWidth()
                            )
                        }
                    }

                    // Continue button enabled only after finishing the task
                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier.width(200.dp),
                        onClick = {
                                viewModel.setThirdQuestion(
                                    thirdQuestionViewModel.thirdQuestionAnswers,
                                    getCurrentFormattedDateTime()
                                )
                                navigator?.replace(NamingScreen())
                        },
                        enabled = isFinished
                    )
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}



