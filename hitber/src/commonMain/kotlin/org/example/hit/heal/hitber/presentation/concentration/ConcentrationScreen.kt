package org.example.hit.heal.hitber.presentation.concentration

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import org.example.hit.heal.core.presentation.Colors.primaryColor
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

        HorizontalTabletBaseScreen(
            title = stringResource(thirdQuestionHitberTitle),
            onNextClick = {
                if (isFinished) {
                    viewModel.setThirdQuestion(
                        thirdQuestionViewModel.thirdQuestionAnswers,
                        getCurrentFormattedDateTime()
                    )
                    navigator?.replace(NamingScreen())
                }
            },
            question = 3,
            buttonText = stringResource(`continue`),
            buttonColor = if (isFinished) primaryColor else Color.Gray,
            content = {

                InstructionText(
                    stringResource(thirdQuestionHitberInstructions),
                )

                if (buttonVisible) {
                    Button(
                        modifier = Modifier.width(300.dp).padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            thirdQuestionViewModel.startButtonSetVisible(false)
                            thirdQuestionViewModel.startRandomNumberGeneration()
                        },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        shape = RoundedCornerShape(30)
                    ) {
                        Text(
                            stringResource(start),
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    )
                } else {
                    if (isFinished) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
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
            })
    }
}



