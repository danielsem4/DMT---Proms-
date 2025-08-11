package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.DragAndDropState
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberTitle
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.SlotState
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ActionShapesScreen
import org.example.hit.heal.hitber.presentation.writing.components.WordSlots
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.writing.components.WordsRow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val eightQuestionViewModel: EightQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val slots by eightQuestionViewModel.slotsWords.collectAsState()
        val draggableWords by eightQuestionViewModel.draggableWords.collectAsState()
        val droppedState by eightQuestionViewModel.droppedState.collectAsState()
        val allFilled by eightQuestionViewModel.allSlotsFilled.collectAsState(initial = false)
        val sentences = eightQuestionViewModel.answerSentences.map { stringResource(it) }
        val dragAndDropState = remember { DragAndDropState<SlotState>() }
        val usedDraggableIds = remember { mutableStateListOf<String>() }

        BaseScreen(
            title = stringResource(eighthQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "8/10",
            content = {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    InstructionText(stringResource(eighthQuestionHitberInstructions))

                    DragAndDropContainer(state = dragAndDropState) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(100.dp)
                        ) {
                            WordsRow(
                                words = draggableWords,
                                dragAndDropState = dragAndDropState
                            )

                            WordSlots(
                                slots = slots,
                                droppedState = droppedState,
                                dragAndDropState = dragAndDropState,
                                usedDraggableIds = usedDraggableIds,
                                eightQuestionViewModel = eightQuestionViewModel
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        RoundedButton(
                            text = stringResource(`continue`),
                            modifier = Modifier
                                .width(200.dp).align(Alignment.BottomCenter),
                            onClick = {
                                eightQuestionViewModel.eighthQuestionAnswer(sentences)
                                viewModel.setEighthQuestion(
                                    eightQuestionViewModel.answer,
                                    getCurrentFormattedDateTime()
                                )
                                navigator?.replace(ActionShapesScreen(9))
                            },

                            enabled = allFilled
                        )
                    }
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.pop()
        }

    }
}








