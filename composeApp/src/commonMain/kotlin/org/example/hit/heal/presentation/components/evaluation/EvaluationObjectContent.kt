package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.EvaluationObject
import core.data.model.evaluation.toRawString
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.slider_value_prefix
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.InstructionBox
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.PillSelectionGroup
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SelectionMode
import org.example.hit.heal.core.presentation.formatLabel
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun EvaluationObjectContent(
    obj: EvaluationObject,
    answers: Map<Int, EvaluationAnswer>,
    onSaveAnswer: (Int, EvaluationAnswer) -> Unit,
    onDrawingControllerReady: ((DrawingCanvasController) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (obj.object_type != 11)
            Text(
                text = obj.object_label,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        when (obj.object_type) {
            // Open question / Text Input
            1 -> {
                CustomMultilineTextField(
                    value = (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: "",
                    onValueChange = { newValue ->
                        onSaveAnswer(obj.id, EvaluationAnswer.Text(newValue))
                    },
                    hintText = obj.object_label,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                )
            }
            // Radio Button
            2 -> {
                if (obj.style == "radio styled" || obj.style == "none") {
                    PillSelectionGroup(
                        selectionMode = SelectionMode.SINGLE,
                        options = obj.available_values?.map { it.available_value } ?: emptyList(),
                        selectedValues = listOf(
                            (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: ""
                        ),
                        onSelectionChanged = { selectedOption ->
                            val value = selectedOption.firstOrNull() ?: return@PillSelectionGroup
                            onSaveAnswer(obj.id, EvaluationAnswer.Text(value))
                        },
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
            // Checkbox / Multi-select
            4 -> {
                if (obj.style == "checkbox styled" || obj.style == "none") {
                    val selectedValues =
                        (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toMutableList()
                            ?: mutableListOf()
                    PillSelectionGroup(
                        options = obj.available_values?.map { it.available_value } ?: emptyList(),
                        selectedValues = selectedValues,
                        onSelectionChanged = { updatedValues ->
                            onSaveAnswer(obj.id, EvaluationAnswer.MultiChoice(updatedValues))
                        }
                    )
                }
            }
            // Toggle Button
            5 -> {
                if (obj.style == "toggle button") {
                    val onLabel = obj.available_values?.getOrNull(0)?.available_value ?: "On"
                    val offLabel = obj.available_values?.getOrNull(1)?.available_value ?: "Off"

                    OnOffToggle(
                        checked = (answers[obj.id] as? EvaluationAnswer.Toggle)?.value,
                        onCheckedChange = { isChecked ->
                            onSaveAnswer(obj.id, EvaluationAnswer.Toggle(isChecked))
                        },
                        onText = onLabel,
                        offText = offLabel
                    )
                }
            }
            // Slider / Scale
            34 -> {
                if (obj.style == "scale" || obj.style == "slider") {
                    val currentValue = (answers[obj.id] as? EvaluationAnswer.Number)?.value
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
                            onSaveAnswer(obj.id, EvaluationAnswer.Number(newValue))
                        }
                    )

                    Text(
                        text = stringResource(Res.string.slider_value_prefix) + currentValue.formatLabel(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                    )
                }
            }
            // Drawing
            21 -> {
                val controller = drawingCanvasWithControls(modifier = Modifier.fillMaxSize())
                onDrawingControllerReady?.invoke(controller)
            }
            // Dynamic / Instruction Box (like Blood pressure, Sugar report, Parkinson report)
            11 -> {
                InstructionBox(
                    text = obj.object_label,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                )
            }
            // Default case for any unhandled object types
            else -> {
                println("Unhandled object type: ${obj.object_type}")
                answers[obj.id]?.toRawString()?.let {
                    Text(it, fontSize = 24.sp)
                }
            }
        }
    }
}