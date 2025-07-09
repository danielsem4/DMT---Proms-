package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberTitle
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
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
            BaseScreen(
                title = stringResource(eighthQuestionHitberTitle),
                config = ScreenConfig.TabletConfig,
                topRightText = "8/10",
                content = {
                    InstructionText(stringResource(eighthQuestionHitberInstructions))
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)
                    ) {
                        StaticWords(draggableWordsList, eightQuestionViewModel, density)
                        WordSlots(slots, eightQuestionViewModel, density)
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        RoundedButton(
                            text = stringResource(`continue`),
                            modifier = Modifier.align(Alignment.BottomCenter).width(200.dp),
                            buttonColor = if (!allFinished) Color.Gray else primaryColor,
                            onClick = {
                                if (allFinished) {
                                    eightQuestionViewModel.eighthQuestionAnswer(sentences)
                                    viewModel.setEighthQuestion(
                                        eightQuestionViewModel.answer,
                                        getCurrentFormattedDateTime()
                                    )
                                    navigator?.replace(ActionShapesScreen(9))
                                }
                            }
                        )
                    }
                })
        }
    }
}








