package org.example.hit.heal.hitber.presentation.shapes

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.Icon.errorIcon
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogTitle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskRetryInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTitle
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.BuildShapeScreen
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
import org.example.hit.heal.hitber.presentation.shapes.components.getShapeName
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class ActionShapesScreen(private val question: Int) : Screen {
    @Composable
    override fun Content() {

        val secondQuestionViewModel: SecondQuestionViewModel = koinViewModel()
        val viewModel : ActivityViewModel = koinViewModel()
        val navigator = LocalNavigator.current
        val selectedShapes by secondQuestionViewModel.selectedShapes.collectAsState()
        val attempt by secondQuestionViewModel.attempt.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        val listShapes by secondQuestionViewModel.listShapes.collectAsState()
        val shapeNames = selectedShapes.map { getShapeName(it.type) }

        HorizontalTabletBaseScreen(title = stringResource(secondQuestionHitberTitle), onNextClick = {
            secondQuestionViewModel.calculateCorrectShapesCount()
            secondQuestionViewModel.updateTask()
            secondQuestionViewModel.secondQuestionAnswer(question, shapeNames)

            if (attempt < 3) {
                showDialog = true
            } else {
                secondQuestionViewModel.resetSelectedShapes()
                if(question == 2) {
                    viewModel.setSecondQuestion(secondQuestionViewModel.secondQuestionAnswersMap, getCurrentFormattedDateTime())
                    navigator?.push(ConcentrationScreen())
                }

                else {viewModel.setNinthQuestion(secondQuestionViewModel.secondQuestionAnswersMap, getCurrentFormattedDateTime())
                    navigator?.push(BuildShapeScreen())
                }
            }

        }, question = question, buttonText = stringResource(`continue`), buttonColor = primaryColor, content = {

           InstructionText( stringResource(secondQuestionHitberTaskInstructions))

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(4))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    val chunkedShapes = listShapes.chunked(5)

                    chunkedShapes.forEach { rowShapes ->
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(40.dp)
                        ) {
                            rowShapes.forEach { shapeRes ->
                                val isSelected = selectedShapes.contains(shapeRes)
                                val shapeColor = if (isSelected) primaryColor else Color.Transparent

                                Image(
                                    painter = painterResource(shapeRes.drawable),
                                    contentDescription = stringResource(secondQuestionHitberTitle),
                                    modifier = Modifier
                                        .background(shapeColor).weight(1f)
                                        .clickable { secondQuestionViewModel.setSelectedShapes(shapeRes) }
                                )
                            }
                        }
                    }
                }
            }

        })
        if (showDialog) {
            DialogTask(
                icon = errorIcon,
                title = stringResource(secondQuestionHitberDialogTitle),
                text = stringResource(secondQuestionHitberTaskRetryInstructions),
                onDismiss = { showDialog = false })
        }

    }
}

