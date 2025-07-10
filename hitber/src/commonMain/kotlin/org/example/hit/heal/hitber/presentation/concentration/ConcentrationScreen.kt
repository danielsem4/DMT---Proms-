package org.example.hit.heal.hitber.presentation.concentration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.start
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberFinishTask
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.thirdQuestionHitberTitle
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.concentration.components.RandomNumberScreen
import org.example.hit.heal.hitber.presentation.naming.NamingScreen
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

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
                InstructionText(
                    stringResource(thirdQuestionHitberInstructions),
                )

                if (buttonVisible) {
                    RoundedButton(
                        text = stringResource(start),
                        modifier = Modifier.width(200.dp).padding(bottom = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            thirdQuestionViewModel.startButtonSetVisible(false)
                            thirdQuestionViewModel.startRandomNumberGeneration()
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().fillMaxHeight(0.8f)
                            .background(color = Color.White)
                    )
                } else {
                    if (isFinished) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight(0.85f)
                                .background(color = Color.White)
                        ) {
                            Text(
                                stringResource(thirdQuestionHitberFinishTask),
                                color = primaryColor,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else
                        RandomNumberScreen(
                            number = number,
                            isClickable = isNumberClickable,
                            onNumberClicked = { thirdQuestionViewModel.thirdQuestionAnswer(it) })
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                        buttonColor = if (isFinished) primaryColor else Color.Gray,
                        onClick = {
                            if (isFinished) {
                                viewModel.setThirdQuestion(
                                    thirdQuestionViewModel.thirdQuestionAnswers,
                                    getCurrentFormattedDateTime()
                                )
                                navigator?.replace(NamingScreen())
                            }
                        }
                    )
                }
            })
    }
}



