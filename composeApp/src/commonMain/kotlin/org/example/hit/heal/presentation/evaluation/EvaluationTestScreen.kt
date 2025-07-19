package org.example.hit.heal.presentation.evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.evaluation.Evaluation
import org.example.hit.heal.core.presentation.FontSize.LARGE
import core.data.model.evaluation.EvaluationAnswer.Unanswered.isAnswered
import core.domain.onError
import core.domain.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
import org.example.hit.heal.presentation.components.evaluation.DrawingCanvasController
import org.example.hit.heal.presentation.components.evaluation.EvaluationObjectContent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class EvaluationTestScreen(
    private val evaluation: Evaluation
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EvaluationTestViewModel = koinViewModel()
        val answers by viewModel.answers.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Success) }

        val allObjects = remember(evaluation) {
            evaluation.measurement_objects.groupBy { it.measurement_screen }
        }

        var currentScreen by remember { mutableStateOf(1) }
        val objectsOnCurrentScreen = allObjects[currentScreen].orEmpty()
        val totalScreens = allObjects.keys.maxOrNull() ?: 1

        val message = stringResource(Resources.String.sentSuccessfully)
        val errorMessage = stringResource(Resources.String.serverError)

        var drawingController by remember { mutableStateOf<DrawingCanvasController?>(null) }

        val uploadResult = {
            scope.launch {
                viewModel.submitEvaluation(evaluation.id)
                    .onSuccess {
                        withContext(Dispatchers.Main) {
                            println("Successfully uploaded test results")
                            snackbarHostState.showSnackbar(message)
                            navigator.pop()
                        }
                    }.onError {
                        withContext(Dispatchers.Main) {
                            println("Error uploading test results:\n$it")
                            snackbarHostState.showSnackbar("$errorMessage: $it")
                        }
                    }
            }
        }


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
                if (objectsOnCurrentScreen.isNotEmpty()) {
                    objectsOnCurrentScreen.forEach { obj ->
                        EvaluationObjectContent(
                            obj = obj,
                            answers = answers,
                            onSaveAnswer = { id, answer ->
                                viewModel.saveAnswer(id, answer)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            onDrawingControllerReady = { controller ->
                                drawingController = controller
                            }
                        )
                    }
                } else {
                    Text(
                        text = stringResource(Resources.String.no_evaluation_object_to_display),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

                    val isFirstScreen = currentScreen == 1
                    val isLastScreen = currentScreen == totalScreens

                    val allAnswered =
                        objectsOnCurrentScreen.all { answers[it.id]?.isAnswered ?: false }
                    Spacer(modifier = Modifier.height(paddingMd))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingMd, vertical = paddingSm),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundedButton(
                            text = if (isFirstScreen) stringResource(Resources.String.back)
                            else stringResource(Resources.String.previous),
                            onClick = {
                                if (isFirstScreen) {
                                    navigator.pop()
                                } else {
                                    currentScreen--
                                }
                            },
                            enabled = true,
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        )

                        RoundedButton(
                            text = if (isLastScreen) stringResource(Resources.String.done)
                            else stringResource(Resources.String.next),
                            onClick = {
                                if (!isLastScreen) {
                                    currentScreen++
                                } else {
                                    uploadResult()
                                }
                            },
                            enabled = allAnswered,
                            modifier = Modifier.weight(1f).padding(start = paddingSm)
                        )
                    }
                }
            }
        }
    }
}