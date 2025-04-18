package org.example.hit.heal.evaluations.presentaion

import DrawingCanvas
import HumanBodyModelSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SimpleRadioButtonGroup
import org.example.hit.heal.evaluations.DrawingCanvasController
import org.example.hit.heal.evaluations.domain.EvaluationObject
import org.example.hit.heal.evaluations.domain.EvaluationViewModel
import org.example.hit.heal.evaluations.domain.api.ApiService
import org.example.hit.heal.utils.imageBitmapToPngByteArray

@Composable
fun EvaluationObjectItem(obj: EvaluationObject, viewModel: EvaluationViewModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        if (obj.evaluationQuestion.isNotBlank()) {
            Text(
                text = obj.evaluationQuestion,
                style = androidx.compose.material.MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        when (obj.objectType) {
            // 1 = Open text field (uses CustomMultilineTextField)
            1 -> {
                var text by remember(obj.id) { mutableStateOf(obj.answer) }
                CustomMultilineTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        viewModel.saveAnswer(obj.id, it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    hintText = "Type your answer...",
                    maxLines = 5
                )
            }

            // 2 = Single choice (radio)
            2 -> {
                var selectedOption by remember { mutableStateOf("") }
                SimpleRadioButtonGroup(
                    options = obj.availableValues?.map { it.value } ?: listOf(),
                    selectedOption = selectedOption,
                    onOptionSelected = {
                        selectedOption = it
                        viewModel.saveAnswer(obj.id, it)
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
                            it.joinToString(separator = ";")
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
                        val value = it.toString()
                        viewModel.saveAnswer(obj.id, value)
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
                        viewModel.saveAnswer(
                            obj.id,
                            it.joinToString(separator = ";") { offset -> "${offset.x},${offset.y}" })
                    }
                )
            }

            // 21 = Drawing canvas
            21 -> {
                val controller = remember { mutableStateOf<DrawingCanvasController?>(null) }

                // Save image on next
                val hasDrawn = remember { mutableStateOf(false) }
                val shouldUpload = rememberUpdatedState(hasDrawn.value)

                val uploaded = remember { mutableStateOf(false) }

                LaunchedEffect(shouldUpload.value) {
                    if (shouldUpload.value && !uploaded.value) {
                        controller.value?.let { canvas ->
                            val image = canvas.drawPathsToBitmap()
                            val bytes = imageBitmapToPngByteArray(image)
                            val url = ApiService.uploadDrawingImage(bytes)
                            viewModel.saveAnswer(obj.id, url)
                            uploaded.value = true
                        }
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                ) {
                    controller.value = DrawingCanvas(
                        onDrawChanged = { drawn ->
                            hasDrawn.value = drawn
                        }
                    )
                }
            }

            // 34 = Scale (slider)
            34 -> {
                var sliderValue by remember { mutableStateOf(5f) }
                RoundedFilledSlider(
                    start = 0f,
                    end = 10f,
                    onValueChanged = {
                        sliderValue = it
                        viewModel.saveAnswer(obj.id, it.toString())
                    }
                )
                Text("Value: ${sliderValue.toInt()}")
            }

            else -> {
                Text("Unsupported question type: ${obj.objectType}")
            }
        }
    }
}
