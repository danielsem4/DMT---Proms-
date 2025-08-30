package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.shapes.ShapeScreen
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.Questions
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.provideQuestions
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val firstQuestionViewModel: FirstQuestionViewModel = koinViewModel()
        val activityViewModel: ActivityViewModel = koinViewModel()

        // Collect both index and answers to trigger recomposition appropriately
        val currentGroupIndex by firstQuestionViewModel.currentGroupIndex.collectAsState()
        val firstQuestionSnapshot by firstQuestionViewModel.firstQuestion.collectAsState()

        val allQuestions = provideQuestions()
        val groups = remember(allQuestions) { allQuestions.chunked(3) }
        val totalGroups = groups.size
        val clampedIndex = currentGroupIndex.coerceIn(0, totalGroups - 1)
        val currentGroup = groups[clampedIndex]

        // Enable Next only if currently shown questions are all answered
        val currentGroupKeys = remember(clampedIndex, currentGroup) { currentGroup.map { it.questionKey } }
        val canGoNext = firstQuestionViewModel.isGroupCompleted(currentGroupKeys, firstQuestionSnapshot)

        // Progress text like "1-3/7"
        val startIdx = clampedIndex * 3 + 1
        val endIdx = (clampedIndex + 1) * 3
        val headerProgress = "${startIdx}-${minOf(endIdx, allQuestions.size)}/${allQuestions.size}"

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
                    // Render only the current chunk of 3
                    Questions(
                        viewModel = firstQuestionViewModel,
                        questionList = currentGroup
                    )
                }

                // Single Next button (enabled only when current group answered)
                RoundedButton(
                    text = "Next",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        val advanced = firstQuestionViewModel.nextGroup(totalGroups)
                        if (!advanced) {
                            // No more groups -> save & navigate
                            activityViewModel.setFirstQuestion(firstQuestionViewModel.firstQuestion.value)
                            navigator?.replace(ShapeScreen())
                        }
                    },
                    enabled = canGoNext
                )
            }
        )

        RegisterBackHandler(this) { navigator?.pop() }
    }
}
