package org.example.hit.heal.hitber.presentation.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
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
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.Icon.errorIcon
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogTitle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTaskRetryInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTitle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberUnderstand
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.dialogs.CustomDialog
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.BuildShapeScreen
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Action screen:
 * - Shows 10 shapes (5 targets + 5 distractors)
 * - Max 5 selectable; tap toggles selection
 * - On first wrong attempt: removes 2 distractors and shows retry dialog (stay on screen)
 * - On second wrong: move on
 * - Saves answers on each attempt
 */
class ActionShapesScreen(private val question: Int) : Screen {
    @Composable
    override fun Content() {

        val secondQuestionViewModel: SecondQuestionViewModel = koinViewModel()
        val activityViewModel: ActivityViewModel = koinViewModel()
        val navigator = LocalNavigator.current

        val selectedShapes by secondQuestionViewModel.selectedShapes.collectAsState()
        val listShapes by secondQuestionViewModel.listShapes.collectAsState()
        var showDialog by remember { mutableStateOf(false) }

        val shapeNames = selectedShapes.map { it.type.name }
        val canSubmit = selectedShapes.size == 5 // optional: require 5 picks

        BaseScreen(
            title = stringResource(secondQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "$question/10",
            content = {
                InstructionText(stringResource(secondQuestionHitberTaskInstructions))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White, shape = RoundedCornerShape(4))
                        .padding(paddingMd)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        // Show shapes in rows of up to 5
                        val chunkedShapes = listShapes.chunked(5)
                        chunkedShapes.forEach { rowShapes ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(40.dp)
                            ) {
                                rowShapes.forEach { shapeRes ->
                                    val isSelected = selectedShapes.contains(shapeRes)

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                color = if (isSelected) primaryColor.copy(alpha = 0.2f) else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .border(
                                                width = if (isSelected) 2.dp else 0.dp,
                                                color = if (isSelected) primaryColor else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { secondQuestionViewModel.setSelectedShapes(shapeRes) }
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(shapeRes.drawable),
                                            contentDescription = stringResource(secondQuestionHitberTitle),
                                            modifier = Modifier.fillMaxSize(),
                                            tint = Color.Unspecified,
                                        )
                                    }
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
                    enabled = canSubmit, // prevent accidental submits with < 5 picks
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            // 1) compute correctness
                            secondQuestionViewModel.calculateCorrectShapesCount()

                            // 2) update attempt based on result
                            secondQuestionViewModel.updateTask()

                            // 3) save this attempt (always)
                            secondQuestionViewModel.secondQuestionAnswer(question, shapeNames)

                            // 4) use the UPDATED value, not a stale collected one
                            val newAttempt = secondQuestionViewModel.attempt.value

                            when (newAttempt) {
                                2 -> {
                                    // First attempt was wrong -> stay here.
                                    // Clear picks + remove two distractors + show retry dialog.
                                    secondQuestionViewModel.setNewAttempt()
                                    showDialog = true
                                    return@launch // do NOT navigate
                                }
                                3 -> {
                                    // Done (either 5/5 on first try OR second attempt ended) -> proceed
                                    proceedAndNavigate(
                                        question,
                                        activityViewModel,
                                        secondQuestionViewModel,
                                        navigator
                                    )
                                }
                                else -> {
                                    // newAttempt == 1 should not happen here; ignore.
                                }
                            }
                        }
                    }
                )
            }
        )

        RegisterBackHandler(this) {
            secondQuestionViewModel.resetAll()
            navigator?.pop()
        }

        // Retry dialog for the 2nd attempt
        if (showDialog) {
            CustomDialog(
                icon = errorIcon,
                title = stringResource(secondQuestionHitberDialogTitle),
                description = stringResource(secondQuestionHitberTaskRetryInstructions),
                onDismiss = { showDialog = false },
                buttons = listOf(
                    stringResource(secondQuestionHitberUnderstand) to { showDialog = false },
                )
            )
        }
    }

    private fun proceedAndNavigate(
        question: Int,
        activityViewModel: ActivityViewModel,
        secondQuestionViewModel: SecondQuestionViewModel,
        navigator: cafe.adriel.voyager.navigator.Navigator?
    ) {
        if (question == 2) {
            activityViewModel.setSecondQuestion(
                secondQuestionViewModel.secondQuestionAnswersList,
                getCurrentFormattedDateTime()
            )
            secondQuestionViewModel.resetSelectedShapes()
            navigator?.replace(ConcentrationScreen())
        } else {
            activityViewModel.setNinthQuestion(
                secondQuestionViewModel.secondQuestionAnswersList,
                getCurrentFormattedDateTime()
            )
            secondQuestionViewModel.resetAll()
            navigator?.replace(BuildShapeScreen())
        }
    }
}
