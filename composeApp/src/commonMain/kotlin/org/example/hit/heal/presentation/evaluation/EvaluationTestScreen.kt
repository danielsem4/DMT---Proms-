package org.example.hit.heal.presentation.evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.evaluation.Evaluation
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Green
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.evaluationText
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.custom_ui.ToastMessage
import org.example.hit.heal.evaluations.presentaion.EvaluationObjectContent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * EvaluationScreen is a screen that will be used to display evaluation-related content.
 * Questions, measurements, and other evaluation-related information will be displayed here.
 */

class EvaluationTestScreen(
    private val evaluation: Evaluation
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: EvaluationTestViewModel = koinViewModel()
        val answers by viewModel.answers.collectAsState()

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Success) }

        // State for managing the current page index
        var currentPageIndex by remember { mutableStateOf(0) }
        val totalObjects = evaluation.measurement_objects.size
        val currentObject = evaluation.measurement_objects.getOrNull(currentPageIndex)


        BaseScreen(title = stringResource(evaluationText)) {
            toastMessage?.let {
                ToastMessage(
                    message = it,
                    type = toastType,
                    alignUp = true,
                    onDismiss = { toastMessage = null }
                )
            }
            Text(
                text = evaluation.measurement_name,
                color = Green,
                fontSize = LARGE,
                modifier = Modifier.padding(spacingMd)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = paddingMd)
            ) {
                currentObject?.let { obj ->
                    EvaluationObjectContent(
                        obj = obj,
                        answers = answers,
                        onSaveAnswer = { id, answer ->
                            viewModel.saveAnswer(id, answer)
                        },
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight()
                    )
                } ?: run {
                    Text(
                        text = stringResource(Resources.String.no_evaluation_object_to_display),
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingMd, vertical = paddingSm),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isFirstPage = currentPageIndex == 0
                RoundedButton(
                    text = if (isFirstPage) stringResource(Resources.String.back) else stringResource(
                        Resources.String.previous
                    ),
                    onClick = {
                        if (isFirstPage) {
                            navigator.pop()
                        } else {
                            currentPageIndex--
                        }
                    },
                    enabled = true,
                    modifier = Modifier.weight(1f).padding(end = paddingSm)
                )

                val isLastPage = currentPageIndex == totalObjects - 1
                RoundedButton(
                    text = if (isLastPage) stringResource(Resources.String.done) else stringResource(
                        Resources.String.next
                    ),
                    onClick = {
                        if (isLastPage) {
                            viewModel.submitEvaluation(evaluation.id, answers)
                            navigator.pop()
                        } else {
                            currentPageIndex++
                        }
                    },
                    enabled = true,
                    modifier = Modifier.weight(1f).padding(start = paddingSm)
                )
            }
        }
    }
}