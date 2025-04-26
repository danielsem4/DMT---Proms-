package org.example.hit.heal.hitber.presentation.shapes

import TabletBaseScreen
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.error_icon
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.second_question_hitbear_dialog_title
import dmt_proms.hitber.generated.resources.second_question_hitbear_task_instructions
import dmt_proms.hitber.generated.resources.second_question_hitbear_task_retry_instructions
import dmt_proms.hitber.generated.resources.second_question_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.BuildShapeScreen
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
import org.example.hit.heal.hitber.presentation.shapes.components.getShapeName
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


class ActionShapesScreen(private val question: Int) : Screen {
    @Composable
    override fun Content() {

        val viewModel: ActivityViewModel = koinViewModel()
        val navigator = LocalNavigator.current
        var showDialog by remember { mutableStateOf(false) }
        val state by viewModel.secondQuestionState.collectAsState()
        val shapeNames = state.selectedShapes.map { getShapeName(it.type) }

        TabletBaseScreen(title = stringResource(Res.string.second_question_hitbear_title), onNextClick = {
            viewModel.onNextClicked(
                question = question,
                shapeNames = shapeNames,
                onShowDialog = { showDialog = true },
                onNavigateNext = { isConcentrationScreen ->
                    if (isConcentrationScreen) {
                        navigator?.push(ConcentrationScreen())
                    } else {
                        navigator?.push(BuildShapeScreen())
                    }
                }
            )
        }, question = question, buttonText = stringResource(Res.string.hitbear_continue), buttonColor = primaryColor, content = {
            Text(
                stringResource(Res.string.second_question_hitbear_task_instructions),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(4))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    val chunkedShapes = state.shapeList.chunked(5)

                    chunkedShapes.forEach { rowShapes ->
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(40.dp)
                        ) {
                            rowShapes.forEach { shapeRes ->
                                val isSelected = state.selectedShapes.contains(shapeRes)
                                val shapeColor = if (isSelected) primaryColor else Color.Transparent

                                Image(
                                    painter = painterResource(shapeRes.drawable),
                                    contentDescription = stringResource(Res.string.second_question_hitbear_title),
                                    modifier = Modifier
                                        .background(shapeColor).weight(1f)
                                        .clickable { viewModel.setSelectedShapes(shapeRes) }
                                )
                            }
                        }
                    }
                }
            }
        })
        if (showDialog) {
            DialogTask(
                icon = Res.drawable.error_icon,
                title = stringResource(Res.string.second_question_hitbear_dialog_title),
                text = stringResource(Res.string.second_question_hitbear_task_retry_instructions),
                onDismiss = { showDialog = false })
        }
    }
}

