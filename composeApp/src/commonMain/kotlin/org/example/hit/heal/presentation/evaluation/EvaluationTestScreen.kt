package org.example.hit.heal.presentation.evaluation

import ToastMessage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import core.data.model.evaluation.EvaluationValue
import core.domain.onError
import core.domain.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.presentation.components.evaluation.DrawingCanvasController
import org.example.hit.heal.presentation.components.evaluation.EvaluationObjectContent
import org.example.hit.heal.presentation.components.evaluation.EvaluationObjectType
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

        var sentSuccessfully by remember { mutableStateOf(false) }
        var toastMessage by remember { mutableStateOf<String?>(null) }
        val toastType by mutableStateOf(
            if (sentSuccessfully) ToastType.Success else ToastType.Error
        )

        val allObjects = remember(evaluation) {
            evaluation.measurement_objects.groupBy { it.measurement_screen }
        }

        var currentScreen by remember { mutableStateOf(1) }
        val objectsOnCurrentScreen = allObjects[currentScreen].orEmpty()
        val togglesGrouped = objectsOnCurrentScreen
            .filter { EvaluationObjectType.fromInt(it.object_type) == EvaluationObjectType.TOGGLE }
            .groupBy { it.measurement_order }

        val totalScreens = allObjects.keys.maxOrNull() ?: 1
        val message = stringResource(Resources.String.sentSuccessfully)
        val errorMessage = stringResource(Resources.String.serverError)

        var drawingController by remember { mutableStateOf<DrawingCanvasController?>(null) }

        val uploadResult = {
            scope.launch {
                viewModel.submitEvaluation(evaluation.id)
                    .onSuccess {
                        withContext(Dispatchers.Main) {
                            toastMessage = message
                            sentSuccessfully = true
                        }
                    }.onError {
                        withContext(Dispatchers.Main) {
                            toastMessage = errorMessage
                            sentSuccessfully = false
                        }
                    }
            }
        }

        val displayObjects = remember(objectsOnCurrentScreen) {
            buildList {
                val handledIds = mutableSetOf<Int>()
                objectsOnCurrentScreen.forEach { obj ->
                    val type = EvaluationObjectType.fromInt(obj.object_type)

                    if (type == EvaluationObjectType.TOGGLE && !handledIds.contains(obj.id)) {
                        val group = togglesGrouped[obj.measurement_order].orEmpty()
                        handledIds.addAll(group.map { it.id })

                        if (group.size >= 2) {
                            val baseObj = group[0]
                            val onObj = group[0]
                            val offObj = group[1]

                            add(
                                baseObj.copy(
                                    object_type = EvaluationObjectType.TOGGLE.type,
                                    available_values = listOf(
                                        EvaluationValue(
                                            id = onObj.id,
                                            available_value = onObj.object_label,
                                            default_value = false,
                                            object_address = "",
                                            measurementObject_id = baseObj.id
                                        ),
                                        EvaluationValue(
                                            id = offObj.id,
                                            available_value = offObj.object_label,
                                            default_value = false,
                                            object_address = "",
                                            measurementObject_id = baseObj.id
                                        )
                                    ),
                                    style = "toggle button"
                                )
                            )
                        }
                    } else if (!handledIds.contains(obj.id)) {
                        add(obj)
                    }
                }
            }
        }

        BaseScreen(title = evaluation.measurement_name) {
            toastMessage?.let {
                ToastMessage(
                    message = it,
                    type = toastType,
                    onDismiss = {
                        if (sentSuccessfully) navigator.pop()
                        toastMessage = null
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = paddingMd)
            ) {
                if (displayObjects.isNotEmpty()) {
                    val showLabel = displayObjects.size <= 1
                    displayObjects.forEach { obj ->
                        EvaluationObjectContent(
                            obj = obj,
                            answers = answers,
                            onSaveAnswer = { id, answer -> viewModel.saveAnswer(id, answer) },
                            showLabel = showLabel,
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
                        if (isFirstScreen) navigator.pop()
                        else currentScreen--
                    },
                    enabled = true,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                RoundedButton(
                    text = if (isLastScreen) stringResource(Resources.String.done)
                    else stringResource(Resources.String.next),
                    onClick = {
                        if (!isLastScreen) currentScreen++
                        else uploadResult()
                    },
                    enabled = true,
                    modifier = Modifier.weight(1f).padding(start = paddingSm)
                )
            }
        }
    }
}
