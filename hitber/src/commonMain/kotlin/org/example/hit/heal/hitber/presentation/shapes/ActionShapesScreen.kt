package org.example.hit.heal.hitber.presentation.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.Icon.errorIcon
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogTitle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskRetryInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTitle
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.BuildShapeScreen
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class ActionShapesScreen(private val question: Int) : Screen {
    @Composable
    override fun Content() {

        val secondQuestionViewModel: SecondQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val navigator = LocalNavigator.current
        val selectedShapes by secondQuestionViewModel.selectedShapes.collectAsState()
        val attempt by secondQuestionViewModel.attempt.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        val listShapes by secondQuestionViewModel.listShapes.collectAsState()
        val shapeNames = selectedShapes.map { it.type.name }

        BaseScreen(title = stringResource(secondQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "$question/10",
            content = {
                InstructionText(stringResource(secondQuestionHitberTaskInstructions))

                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .background(Color.White, shape = RoundedCornerShape(4))
                        .padding(paddingMd)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        // Display shapes in rows of 5
                        val chunkedShapes = listShapes.chunked(5)
                        chunkedShapes.forEach { rowShapes ->
                            Row(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(40.dp)
                            ) {
                                rowShapes.forEach { shapeRes ->
                                    val isSelected = selectedShapes.contains(shapeRes)
                                    val shapeColor =
                                        if (isSelected) primaryColor else Color.Transparent

                                    Icon(
                                        painter = painterResource(shapeRes.drawable),
                                        contentDescription = stringResource(
                                            secondQuestionHitberTitle
                                        ),
                                        modifier = Modifier
                                            .background(shapeColor).weight(1f)
                                            .clickable {
                                                // Update selected shapes on click
                                                secondQuestionViewModel.setSelectedShapes(
                                                    shapeRes
                                                )
                                            },
                                        tint = Color.Unspecified,
                                    )
                                }
                            }
                        }
                    }
                }
                RoundedButton(
                    text = stringResource(`continue`),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(200.dp),
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            // Calculate and update attempt based on selected shapes
                            secondQuestionViewModel.calculateCorrectShapesCount()
                            secondQuestionViewModel.updateTask()
                            delay(300)
                            secondQuestionViewModel.secondQuestionAnswer(question, shapeNames)

                            if (attempt <= 3) {
                                secondQuestionViewModel.setNewAttempt()
                                showDialog = true
                            } else {
                                if (question == 2) {
                                    viewModel.setSecondQuestion(
                                        secondQuestionViewModel.secondQuestionAnswersList,
                                        getCurrentFormattedDateTime()
                                    )
                                    secondQuestionViewModel.resetSelectedShapes()
                                    navigator?.replace(ConcentrationScreen())
                                } else {
                                    viewModel.setNinthQuestion(
                                        secondQuestionViewModel.secondQuestionAnswersList,
                                        getCurrentFormattedDateTime()
                                    )
                                    secondQuestionViewModel.resetAll()
                                    navigator?.replace(BuildShapeScreen())
                                }
                            }
                        }
                    }
                )


            })

        RegisterBackHandler(this) {
            secondQuestionViewModel.resetAll()
            navigator?.pop()
        }

        // Retry dialog shown when user needs to retry the task
        if (showDialog) {
            DialogTask(
                icon = errorIcon,
                title = stringResource(secondQuestionHitberDialogTitle),
                text = stringResource(secondQuestionHitberTaskRetryInstructions),
                onDismiss = { showDialog = false })
        }

    }
}

