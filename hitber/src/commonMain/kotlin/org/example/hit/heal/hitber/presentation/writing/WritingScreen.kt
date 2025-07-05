package org.example.hit.heal.hitber.presentation.writing

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
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
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberTitle
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ActionShapesScreen
import org.example.hit.heal.hitber.presentation.writing.components.StaticWords
import org.example.hit.heal.hitber.presentation.writing.components.WordSlots
import org.example.hit.heal.hitber.presentation.writing.model.draggableWordsList
import org.example.hit.heal.hitber.presentation.components.InstructionText
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
            HorizontalTabletBaseScreen(
                title = stringResource(eighthQuestionHitberTitle),
                onNextClick = {
                    if (allFinished) {
                        eightQuestionViewModel.eighthQuestionAnswer(sentences)
                        viewModel.setEighthQuestion(
                            eightQuestionViewModel.answer,
                            getCurrentFormattedDateTime()
                        )
                        navigator?.replace(ActionShapesScreen(9))

                    }
                },
                buttonText = stringResource(`continue`),
                question = 8,
                buttonColor = if (!allFinished) Color.Gray else primaryColor,
                content = {
                    InstructionText(stringResource(eighthQuestionHitberInstructions))
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








