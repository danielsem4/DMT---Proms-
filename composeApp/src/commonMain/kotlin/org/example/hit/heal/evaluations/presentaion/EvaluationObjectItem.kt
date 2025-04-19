package org.example.hit.heal.evaluations.presentaion

import HumanBodyModelSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SimpleRadioButtonGroup
import org.example.hit.heal.core.utils.formatLabel
import org.example.hit.heal.evaluations.DrawingCanvasController
import org.example.hit.heal.evaluations.domain.EvaluationObject
import org.example.hit.heal.evaluations.domain.EvaluationViewModel
import org.example.hit.heal.evaluations.domain.api.ApiService
import org.example.hit.heal.evaluations.domain.data.EvaluationAnswer
import org.example.hit.heal.utils.imageBitmapToPngByteArray

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
                            hintText = "Type your answer...",
                            textStyle = TextStyle(fontSize = 18.sp),
                            maxLines = 8
                        )
                    }
                }
            }

            // 2 = Single choice (radio)
            2 -> {
                var selectedOption by remember { mutableStateOf("") }
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
                MultiSelectCheckboxGroup(
                    options = obj.availableValues?.map { it.value } ?: listOf(),
                    selectedValues = remember { mutableStateListOf() },
                    onSelectionChanged = {
                        viewModel.saveAnswer(
                            obj.id,
                            EvaluationAnswer.MultiChoice(it)
                        )
                    }
                )
            }

            // 5 = Toggle button
            5 -> {
                var toggleState by remember { mutableStateOf(obj.answer == "true") }

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
                var selectedPoints by remember { mutableStateOf(setOf<Offset>()) }
                HumanBodyModelSelector(
                    selectedPoints = selectedPoints,
                    onSelectionChanged = {
                        selectedPoints = it
                        val selectedPointsList =
                            it.joinToString(separator = ";") { offset -> "${offset.x},${offset.y}" }
                        viewModel.saveAnswer(
                            obj.id,
                            EvaluationAnswer.Text(selectedPointsList)
                        )
                    }
                )
            }

            // 21 = Drawing canvas
            21 -> {
                val controller = remember { mutableStateOf<DrawingCanvasController?>(null) }
                val hasDrawn = remember { mutableStateOf(false) }

                LaunchedEffect(hasDrawn.value) {
                    if (hasDrawn.value) {
                        controller.value?.let { canvas ->
                            val bitmap = canvas.drawPathsToBitmap()
                            val bytes = imageBitmapToPngByteArray(bitmap)
                            val url = ApiService.uploadDrawingImage(bytes)
                            viewModel.saveAnswer(obj.id, EvaluationAnswer.Image(url))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                ) {
                    controller.value = DrawingCanvas(
                        onDrawChanged = { hasDrawn.value = it },
                        modifier = Modifier
                            .fillMaxSize()
                    )
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
                        text = "Value: ${sliderValue.formatLabel()}",
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
