package org.example.hit.heal.hitber.presentation.concentration

import TabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.hitbear_start
import dmt_proms.hitber.generated.resources.third_question_hitbear_finish_task
import dmt_proms.hitber.generated.resources.third_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.third_question_hitbear_title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.naming.NamingScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class ConcentrationScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val thirdQuestionViewModel : ThirdQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val buttonVisible by thirdQuestionViewModel.startButtonIsVisible.collectAsState()
        val number by thirdQuestionViewModel.number.collectAsState()
        val isFinished by thirdQuestionViewModel.isFinished.collectAsState()
        val isNumberClickable by thirdQuestionViewModel.isNumberClickable.collectAsState()

        TabletBaseScreen(
            title = stringResource(Res.string.third_question_hitbear_title),
            onNextClick = { if (isFinished){ viewModel.setThirdQuestion(thirdQuestionViewModel.thirdQuestionAnswers, getCurrentFormattedDateTime())
                navigator?.push(NamingScreen())
            } },
            question = 3,
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = if (isFinished) primaryColor else Color.Gray,
            content = {
                Text(
                    stringResource(Res.string.third_question_hitbear_instructions),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp)
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
                            stringResource(Res.string.hitbear_start), color = Color.White, fontSize = 25.sp,
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
                                stringResource(Res.string.third_question_hitbear_finish_task), color = primaryColor, fontSize = 30.sp,
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

@Composable
fun RandomNumberScreen(number: Int, isClickable: Boolean, onNumberClicked: (Int) -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isClicked) primaryColor else Color.White)
            .clickable(enabled = isClickable) {
                isClicked = true
                onNumberClicked(number)
                coroutineScope.launch {
                    delay(100)
                    isClicked = false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = if (isClicked) Color.White else primaryColor
        )
    }
}

