package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberTitle
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.core.presentation.components.DropDownQuestionField
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.shapes.ShapeScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val firstQuestionViewModel: FirstQuestionViewModel = koinViewModel()
        val activityViewModel: ActivityViewModel = koinViewModel()

        LaunchedEffect(Unit) {
            activityViewModel.setFirstQuestion()?.let { list ->
                firstQuestionViewModel.initializeQuestions(list)
            }
        }

        val questions by firstQuestionViewModel.questions.collectAsState()
        val answers by firstQuestionViewModel.answers.collectAsState()
        val currentGroupIndex by firstQuestionViewModel.currentGroupIndex.collectAsState()

        val groups = remember(questions) { questions.chunked(3) }
        val totalGroups = groups.size
        val clampedIndex = currentGroupIndex.coerceIn(0, maxOf(totalGroups - 1, 0))
        val currentGroup = if (totalGroups == 0) emptyList() else groups[clampedIndex]

        val currentGroupKeys = remember(clampedIndex, currentGroup) { currentGroup.map { it.second } }
        val canGoNext = firstQuestionViewModel.isGroupCompleted(currentGroupKeys)

        val startIdx = clampedIndex * 3 + 1
        val endIdx = (clampedIndex + 1) * 3
        val headerProgress = if (questions.isEmpty()) "0/0"
        else "${startIdx}-${minOf(endIdx, questions.size)}/${questions.size}"

        BaseScreen(
            title = stringResource(firstQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = headerProgress,
            content = {
                InstructionText(stringResource(firstQuestionHitberInstructions))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = paddingMd)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(paddingLg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    currentGroup.forEach { (answersList, label) ->
                        if (answersList.isNotEmpty()) {
                            val selectedText = answers[label].orEmpty()

                            DropDownQuestionField(
                                question = label,
                                dropDownItems = answersList.map(::DropDownItem),
                                selectedText = selectedText,
                                onItemClick = { item: DropDownItem ->
                                    firstQuestionViewModel.firstQuestionAnswer(label, item)
                                }
                            )
                        }
                    }
                }

                RoundedButton(
                    text = if (clampedIndex < totalGroups - 1) "Next" else "Continue",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = canGoNext,
                    onClick = {
                        val advanced = firstQuestionViewModel.nextGroup(totalGroups)
                        if (!advanced) {
                            val result = firstQuestionViewModel.buildResult()
                            activityViewModel.setFirstQuestionResults(result)
                            navigator?.replace(ShapeScreen())
                        }
                    }
                )
            }
        )

        RegisterBackHandler(this) { navigator?.pop() }
    }
}
