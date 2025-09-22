package org.example.hit.heal.hitber.presentation.timeAndPlace

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import org.example.hit.heal.core.presentation.Resources.String.fill_fields
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.ToastType
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

/**
 * Time and place screen Hitber
 * Drop downs
 */

class TimeAndPlace : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val firstQuestionViewModel: FirstQuestionViewModel = koinViewModel()
        val activityViewModel: ActivityViewModel = koinViewModel()

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }
        val fillFieldsMsg = stringResource(fill_fields)
        val goodJob = "Good job"

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

        val currentGroupKeys =
            remember(clampedIndex, currentGroup) { currentGroup.map { it.second } }

        TabletBaseScreen(
            title = stringResource(firstQuestionHitberTitle),
            onNextClick = {
                val isCompleted = firstQuestionViewModel.isGroupCompleted(currentGroupKeys)
                if (isCompleted) {
                    toastMessage = fillFieldsMsg
                    toastType = ToastType.Warning
                } else {
                    toastMessage = goodJob
                    toastType = ToastType.Success
                    val advanced = firstQuestionViewModel.nextGroup(totalGroups)
                    if (!advanced) {
                        val result = firstQuestionViewModel.buildResult()
                        activityViewModel.setFirstQuestionResults(result)
                        navigator?.replace(ShapeScreen())
                    }
                }
            },
            question = 1,
            content = {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingMd)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(paddingLg),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InstructionText(stringResource(firstQuestionHitberInstructions))

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

                    toastMessage?.let { msg ->
                        ToastMessage(
                            message = msg,
                            type = toastType,
                            alignUp = false,
                            onDismiss = { toastMessage = null }
                        )
                    }
                }
            }
        )

        RegisterBackHandler(this) { navigator?.pop() }
    }
}
