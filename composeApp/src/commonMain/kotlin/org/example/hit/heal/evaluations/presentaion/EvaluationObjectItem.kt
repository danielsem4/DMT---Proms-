package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.clear
import dmt_proms.composeapp.generated.resources.slider_value_prefix
import dmt_proms.composeapp.generated.resources.undo
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SimpleRadioButtonGroup
import org.example.hit.heal.core.utils.formatLabel
import org.example.hit.heal.evaluations.DrawingCanvasController
import org.example.hit.heal.evaluations.domain.data.EvaluationObject
import org.example.hit.heal.evaluations.domain.api.ApiService
import org.example.hit.heal.evaluations.domain.data.EvaluationAnswer
import org.example.hit.heal.utils.imageBitmapToPngByteArray
import org.jetbrains.compose.resources.stringResource

@Composable
fun EvaluationObjectItem(obj: EvaluationObject, viewModel: EvaluationViewModel) {
    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (obj.evaluationQuestion.isNotBlank()) {
            Text(
                text = obj.evaluationQuestion,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Colors.primaryColor,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        when (obj.objectType) {
            // 1 = Open text field (uses CustomMultilineTextField)
            1 -> {
                var text by remember(obj.id) { mutableStateOf(obj.answer) }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (obj.evaluationQuestion.isNotBlank()) {
                            Text(
                                text = obj.evaluationQuestion,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        CustomMultilineTextField(
                            value = text,
                            onValueChange = {
                                text = it
                                viewModel.saveAnswer(obj.id, EvaluationAnswer.Text(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp), // gives a clean consistent height
                            textStyle = TextStyle(fontSize = 18.sp),
                            maxLines = 8
                        )
                    }
                }
            }

            // 2 = Single choice (radio)
            2 -> {
                var selectedOption by remember(obj.id) { mutableStateOf(obj.answer) }

                SimpleRadioButtonGroup(
                    modifier = Modifier.fillMaxWidth(),
                    options = obj.availableValues?.map { it.value } ?: listOf(),
                    selectedOption = selectedOption,
                    onOptionSelected = {
                        selectedOption = it
                        viewModel.saveAnswer(obj.id, EvaluationAnswer.Text(it))
                    }
                )
            }

            // 4 = Multiple choice (checkboxes)
            4 -> {
                val initialValues = remember(obj.id) {
                    if (obj.answer.isNotBlank()) obj.answer.split(",") else emptyList()
                }
                val selectedValues = remember(obj.id) {
                    mutableStateListOf<String>().apply { addAll(initialValues) }
                }

                MultiSelectCheckboxGroup(
                    options = obj.availableValues?.map { it.value } ?: listOf(),
                    selectedValues = selectedValues,
                    onSelectionChanged = {
                        selectedValues.clear()
                        selectedValues.addAll(it)
                        viewModel.saveAnswer(obj.id, EvaluationAnswer.MultiChoice(it))
                    }
                )
            }

            // 5 = Toggle button
            5 -> {
                var toggleState by remember(obj.id) {
                    mutableStateOf(obj.answer == "true")
                }

                OnOffToggle(
                    checked = toggleState,
                    onCheckedChange = {
                        toggleState = it
                        viewModel.saveAnswer(obj.id, EvaluationAnswer.Toggle(it))
                    }
                )
            }

            // 11 = Human body model
            11 -> {
                val initial: EvaluationAnswer.HumanModelPoints = when (val answer = viewModel.getAnswer(obj.id)) {
                    is EvaluationAnswer.HumanModelPoints -> answer
                    else -> EvaluationAnswer.HumanModelPoints(emptySet(), emptySet())
                }

                var front by remember(obj.id) { mutableStateOf(initial.front) }
                var back by remember(obj.id) { mutableStateOf(initial.back) }
                var isFrontView by remember(obj.id) { mutableStateOf(true) }

                HumanBodyModelSelector(
                    frontPoints = front,
                    backPoints = back,
                    isFrontView = isFrontView,
                    onSelectionChanged = { updatedFront, updatedBack ->
                        front = updatedFront
                        back = updatedBack
                        viewModel.saveAnswer(obj.id, EvaluationAnswer.HumanModelPoints(front, back))
                    },
                    onToggleView = {
                        isFrontView = !isFrontView
                    }
                )
            }


            // 21 = Drawing canvas
            21 -> {
                val controller = remember { mutableStateOf<DrawingCanvasController?>(null) }
                val coroutineScope = rememberCoroutineScope()

                val previousPaths = viewModel.getDrawingPaths(obj.id)

                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RoundedButton(Res.string.undo) {
                            controller.value?.run {
                                undoLastStroke()
                                viewModel.saveDrawingPaths(obj.id, getPaths())
                            }
                        }
                        RoundedButton(Res.string.clear) {
                            controller.value?.run {
                                clearCanvas()
                                viewModel.saveDrawingPaths(obj.id, getPaths())
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    controller.value = DrawingCanvas(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                        initialPaths = previousPaths,
                        onStrokeCommitted = {
                            controller.value?.let { canvas ->
                                coroutineScope.launch {
                                    val image = canvas.drawPathsToBitmap()
                                    val bytes = imageBitmapToPngByteArray(image)
                                    val url = ApiService.uploadDrawingImage(bytes)
                                    viewModel.saveAnswer(obj.id, EvaluationAnswer.Image(url))
                                    viewModel.saveDrawingPaths(obj.id, canvas.getPaths())
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


            // 34 = Scale (slider)
            34 -> {
                val values = obj.availableValues?.mapNotNull {
                    it.value.toFloatOrNull()
                } ?: emptyList()
                val sliderStart = values.minOrNull() ?: 0f
                val sliderEnd = values.maxOrNull() ?: 10f

                var sliderValue by remember(obj.id) {
                    mutableStateOf(obj.answer.toFloatOrNull() ?: sliderStart)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    RoundedFilledSlider(
                        start = sliderStart,
                        end = sliderEnd,
                        value = sliderValue,
                        onValueChanged = {
                            sliderValue = it
                            viewModel.saveAnswer(obj.id, EvaluationAnswer.Number(it))
                        }
                    )

                    Text(
                        text = stringResource(Res.string.slider_value_prefix) + sliderValue.formatLabel(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Colors.primaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp).fillMaxWidth()
                    )
                }
            }

            else -> {
                Text(
                    "Unsupported question type: ${obj.objectType}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
