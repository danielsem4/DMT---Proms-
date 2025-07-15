package org.example.hit.heal.presentation.evaluation

import ContentWithMessageBar
import MessageBarPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.toRawString
import core.domain.onError
import core.domain.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.presentation.components.evaluation.DrawingCanvasController
import org.example.hit.heal.presentation.components.evaluation.EvaluationObjectContent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * EvaluationScreen is a screen that will be used to display evaluation-related content.
 * Questions, measurements, and other evaluation-related information will be displayed here.
 */

class EvaluationTestScreen(
    private val evaluation: Evaluation
) : Screen {

    private val inputTypes = setOf(1, 2, 4, 5, 21, 34)

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel: EvaluationTestViewModel = koinViewModel()
        val messageBarState = rememberMessageBarState()
        val answers by viewModel.answers.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }

        // State for managing the current page index
        var currentPageIndex by remember { mutableStateOf(0) }
        val totalObjects = evaluation.measurement_objects.size
        val currentObject = evaluation.measurement_objects.getOrNull(currentPageIndex)
        val message = stringResource(Resources.String.sentSuccessfully)
        val errorMessage = stringResource(Resources.String.serverError)
        var drawingController by remember { mutableStateOf<DrawingCanvasController?>(null) }

        val uploadResult = {
            scope.launch {
                viewModel.submitEvaluation(evaluation.id).onSuccess {
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

        ContentWithMessageBar(
            messageBarState = messageBarState, position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(
                title = evaluation.measurement_name,
                snackbarHostState = snackbarHostState,
            ) {
                currentObject?.let { obj ->
                    EvaluationObjectContent(
                        obj = obj,
                        answers = answers,
                        onSaveAnswer = { id, answer ->
                            viewModel.saveAnswer(id, answer)
                        },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        onDrawingControllerReady = { controller ->
                            drawingController = controller
                        })
                }
                // Handle case where currentObject is null (e.g., empty evaluation or index out of bounds)
                    ?: Text(
                        text = stringResource(Resources.String.no_evaluation_object_to_display),
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                    )

                Spacer(modifier = Modifier.height(16.dp)) // Spacer between content and buttons

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isFirstPage = currentPageIndex == 0
                    RoundedButton(
                        text = if (isFirstPage) stringResource(Resources.String.back) // Change text to "Back" on first page
                        else stringResource(Resources.String.previous), onClick = {
                            if (isFirstPage) {
                                navigator.pop() // Navigate back
                            } else {
                                currentPageIndex--
                            }
                        }, enabled = true, // Always enabled, logic inside handles navigation
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )

                    val isLastPage = currentPageIndex == totalObjects - 1

                    val currentAnswered = currentObject?.run {
                        if (object_type == 21) {
                            drawingController?.hasPaths?.invoke() == true
                        } else {
                            !isInputType(object_type) || answers[id]?.toRawString()
                                ?.isNotBlank() == true
                        }
                    } ?: false

                    val onClick: () -> Unit = {
                        if (!isLastPage) {
                            if (currentObject?.object_type == 21) {
                                drawingController?.let { controller ->
                                    val bitmap = controller.getBitmap()
                                    viewModel.saveAnswer(
                                        currentObject.id, EvaluationAnswer.Image(bitmap)
                                    )
                                }
                            }
                            currentPageIndex++

                        } else uploadResult()
                    }

                    RoundedButton(
                        text = if (isLastPage) stringResource(Resources.String.done)
                        else stringResource(Resources.String.next),
                        onClick = onClick,
                        enabled = currentAnswered,
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    )
                }
            }
        }
    }

    private fun isInputType(objectType: Int) = objectType in inputTypes

}