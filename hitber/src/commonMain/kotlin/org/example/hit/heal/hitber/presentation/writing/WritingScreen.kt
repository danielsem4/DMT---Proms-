package org.example.hit.heal.hitber.presentation.writing

import TabletBaseScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.eighth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.eighth_question_hitbear_title
import dmt_proms.hitber.generated.resources.hitbear_continue
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ActionShapesScreen
import org.example.hit.heal.hitber.presentation.writing.components.StaticWords
import org.example.hit.heal.hitber.presentation.writing.components.WordSlots
import org.example.hit.heal.hitber.presentation.writing.model.draggableWordsList
import org.example.hit.heal.hitber.utils.InstructionText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val eightQuestionViewModel: EightQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val density = LocalDensity.current
        val slots by eightQuestionViewModel.slotsWords.collectAsState()
        val allFinished by eightQuestionViewModel.allFinished.collectAsState()
        val sentences = eightQuestionViewModel.answerSentences.map { stringResource(it) }

        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            TabletBaseScreen(
                title = stringResource(Res.string.eighth_question_hitbear_title),
                onNextClick = {
                    if (allFinished) {
                        eightQuestionViewModel.eighthQuestionAnswer(sentences)
                        viewModel.setEighthQuestion(
                            eightQuestionViewModel.answer,
                            getCurrentFormattedDateTime()
                        )
                        navigator?.push(ActionShapesScreen(9))

                    }
                },
                buttonText = stringResource(Res.string.hitbear_continue),
                question = 8,
                buttonColor = if (!allFinished) Color.Gray else primaryColor,
                content = {
                    InstructionText(stringResource(Res.string.eighth_question_hitbear_instructions))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StaticWords(draggableWordsList, eightQuestionViewModel, density)
                        WordSlots(slots, eightQuestionViewModel, density)
                    }
                })
        }
    }
}








