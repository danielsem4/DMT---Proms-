package org.example.hit.heal.presentation.evaluation

import ContentWithMessageBar
import MessageBarPosition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import core.data.model.evaluation.Evaluation
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.toRawString
import org.example.hit.heal.core.presentation.Resources.String.evaluationText
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.InstructionBox
import org.example.hit.heal.core.presentation.components.MultiSelectCheckboxGroup
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SimpleRadioButtonGroup
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * EvaluationScreen is a screen that will be used to display evaluation-related content.
 * Questions, measurements, and other evaluation-related information will be displayed here.
 */

class EvaluationScreen(
    private val evaluation: Evaluation
) : Screen {

    @Composable
    override fun Content() {

        val viewModel: EvaluationViewModel = koinViewModel()
        val messageBarState = rememberMessageBarState()
        val answers by viewModel.answers.collectAsState()
        val scrollState = rememberScrollState()

        ContentWithMessageBar(
            messageBarState = messageBarState,
            position = MessageBarPosition.BOTTOM
        ) {
            BaseScreen(
                title = stringResource(evaluationText) + "-" + evaluation.measurement_name,
                modifier = Modifier.verticalScroll(scrollState)
            ) {

                for (obj in evaluation.measurement_objects) {
                    Spacer(Modifier.height(16.dp)) // Add some spacing between objects

                    when (obj.object_type) {
                        // Open question / Text Input
                        1 -> {
                            CustomMultilineTextField(
                                value = (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: "",
                                onValueChange = { newValue ->
                                    viewModel.saveAnswer(obj.id, EvaluationAnswer.Text(newValue))
                                },
                                hintText = obj.object_label,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        // Radio Button
                        2 -> {
                            // "Choose one option:" with radio buttons
                            if (obj.style == "radio styled" || obj.style == "none") {
                                Text(
                                    text = obj.object_label,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                        .align(Alignment.Start)
                                )
                                SimpleRadioButtonGroup(
                                    options = obj.available_values?.map { it.available_value }
                                        ?: emptyList(),
                                    selectedOption = (answers[obj.id] as? EvaluationAnswer.Text)?.value
                                        ?: "",
                                    onOptionSelected = { selectedOption ->
                                        viewModel.saveAnswer(
                                            obj.id,
                                            EvaluationAnswer.Text(selectedOption)
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        // Checkbox / Multi-select
                        4 -> {
                            // "choose multiple options:" with checkboxes
                            if (obj.style == "checkbox styled" || obj.style == "none") {
                                Text(
                                    text = obj.object_label,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                        .align(Alignment.Start)
                                )
                                val selectedValues =
                                    (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toMutableList()
                                        ?: mutableListOf()
                                MultiSelectCheckboxGroup(
                                    options = obj.available_values?.map { it.available_value }
                                        ?: emptyList(),
                                    selectedValues = selectedValues,
                                    onSelectionChanged = { updatedValues ->
                                        viewModel.saveAnswer(
                                            obj.id,
                                            EvaluationAnswer.MultiChoice(updatedValues)
                                        )
                                    }
                                )
                            }
                        }
                        // Toggle Button
                        5 -> {
                            // "on" / "off" with toggle
                            if (obj.style == "toggle button") {
                                Text(
                                    text = obj.object_label,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                        .align(Alignment.Start)
                                )
                                OnOffToggle(
                                    checked = (answers[obj.id] as? EvaluationAnswer.Toggle)?.value
                                        ?: false,
                                    onCheckedChange = { isChecked ->
                                        viewModel.saveAnswer(
                                            obj.id,
                                            EvaluationAnswer.Toggle(isChecked)
                                        )
                                    }
                                )
                            }
                        }
                        // Dynamic / Instruction Box (like Blood pressure, Sugar report, Parkinson report)
                        11 -> {
                            InstructionBox(
                                text = obj.object_label,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f) // Adjusted width for InstructionBox
                                    .align(Alignment.CenterHorizontally) // Center instruction box
                            )
                        }
                        // Slider / Scale
                        34 -> {
                            // "scale" with slider
                            if (obj.style == "scale" || obj.style == "slider") {
                                Text(
                                    text = obj.object_label,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                        .align(Alignment.Start)
                                )
                                val currentValue =
                                    (answers[obj.id] as? EvaluationAnswer.Number)?.value
                                        ?: (obj.available_values?.firstOrNull()?.available_value?.toFloatOrNull()
                                            ?: 0f)
                                val startValue = obj.available_values?.minOfOrNull {
                                    it.available_value.toFloatOrNull() ?: Float.MAX_VALUE
                                } ?: 0f
                                val endValue = obj.available_values?.maxOfOrNull {
                                    it.available_value.toFloatOrNull() ?: Float.MIN_VALUE
                                } ?: 5f // Default to 5 if no max value
                                RoundedFilledSlider(
                                    start = startValue,
                                    end = endValue,
                                    value = currentValue,
                                    onValueChanged = { newValue ->
                                        viewModel.saveAnswer(
                                            obj.id,
                                            EvaluationAnswer.Number(newValue)
                                        )
                                    }
                                )
                            }
                        }
                        // Drawing (Placeholder)
                        21 -> {
                            Text(
                                text = "${obj.object_label}: Drawing component placeholder",
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                            )
                            // Further implementation needed for drawing
                        }
                        // Default case for any unhandled object types
                        else -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    obj.object_label,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text(
                                    answers[obj.id]?.toRawString() ?: "",
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) // Display the saved answer
                            }
                        }
                    }
                }
            }
        }
    }
}