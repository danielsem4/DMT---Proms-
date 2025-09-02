package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.data.model.evaluation.EvaluationAnswer
import core.data.model.evaluation.EvaluationObject
import core.data.model.evaluation.EvaluationValue
import core.data.model.evaluation.toRawString
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.core.presentation.components.DropDownQuestionField
import org.example.hit.heal.core.presentation.components.InstructionBox
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.OptionStyle
import org.example.hit.heal.core.presentation.components.PillSelectionGroup
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SelectionMode
import org.example.hit.heal.core.presentation.components.dialogs.MultiSelectPillDialog
import org.example.hit.heal.core.presentation.formatLabel

/**
 *
 */

@Composable
fun EvaluationObjectContent(
    obj: EvaluationObject,
    answers: Map<Int, EvaluationAnswer>,
    showLabel: Boolean,
    modifier: Modifier = Modifier,
    onSaveAnswer: (Int, EvaluationAnswer) -> Unit,
    onDrawingControllerReady: ((DrawingCanvasController) -> Unit)? = null
) {

    var showDialogForBodyPart by remember { mutableStateOf<EvaluationValue?>(null) }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val availableValues: List<EvaluationValue> = obj.available_values.orEmpty()
        val options = availableValues.map { it.available_value }
        val type = EvaluationObjectType.fromInt(obj.object_type)

        if (showLabel && obj.object_label.isNotEmpty() && type != EvaluationObjectType.INSTRUCTION)
            Text(
                text = obj.object_label,
                fontSize = EXTRA_MEDIUM_LARGE,
                modifier = Modifier.padding(bottom = paddingSm).align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

        when (type) {
            EvaluationObjectType.OPEN_QUESTION -> {
                CustomMultilineTextField(
                    value = (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: "",
                    onValueChange = { newValue ->
                        onSaveAnswer(obj.id, EvaluationAnswer.Text(newValue))
                    },
                    hintText = obj.object_label,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                )
            }

            EvaluationObjectType.RADIO -> {
                val selectedValues = listOf(
                    (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: ""
                )

                val onSelectionChanged: (List<String>) -> Unit = {
                    it.firstOrNull()?.let { value ->
                        onSaveAnswer(obj.id, EvaluationAnswer.Text(value))
                    }
                }

                val style =
                    if (obj.style == "radio styled") OptionStyle.STYLED else OptionStyle.PLAIN

                PillSelectionGroup(
                    style = style,
                    selectionMode = SelectionMode.SINGLE,
                    options = options,
                    selectedValues = selectedValues,
                    onSelectionChanged = onSelectionChanged,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            EvaluationObjectType.DROPDOWN -> {
                val onItemClicked: (DropDownItem) -> Unit = {
                    onSaveAnswer(obj.id, EvaluationAnswer.Text(it.text))
                }
                val dropDownItems = availableValues.map { DropDownItem(it.available_value) }

                DropDownQuestionField(
                    question = obj.object_label,
                    dropDownItems = dropDownItems,
                    onItemClick = onItemClicked,
                    selectedText = (answers[obj.id] as? EvaluationAnswer.Text)?.value ?: ""
                )
            }

            EvaluationObjectType.CHECKBOX -> {
                val selectedValues =
                    (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toList()
                        ?: emptyList()

                val style = if (obj.style == "checkbox styled") OptionStyle.STYLED
                else OptionStyle.PLAIN

                val onSelectionChanged: (List<String>) -> Unit = {
                    onSaveAnswer(obj.id, EvaluationAnswer.MultiChoice(it))
                }

                PillSelectionGroup(
                    style = style,
                    options = options,
                    selectionMode = SelectionMode.MULTIPLE,
                    selectedValues = selectedValues,
                    onSelectionChanged = onSelectionChanged
                )
            }

            EvaluationObjectType.TOGGLE -> {
                if (obj.style == "toggle button") {
                    val onLabel = availableValues.getOrNull(0)?.available_value ?: "On"
                    val offLabel = availableValues.getOrNull(1)?.available_value ?: "Off"

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

            EvaluationObjectType.INSTRUCTION, EvaluationObjectType.REPORT -> {
                InstructionBox(
                    text = obj.object_label,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                )
                onSaveAnswer(obj.id, EvaluationAnswer.Answered)
            }

            EvaluationObjectType.DRAWING -> {
                val controller = drawingCanvasWithControls(modifier = Modifier.fillMaxSize())
                onDrawingControllerReady?.invoke(controller)
                if (controller.hasPaths())
                    onSaveAnswer(obj.id, EvaluationAnswer.Image(controller.getBitmap()))
                else
                    onSaveAnswer(obj.id, EvaluationAnswer.Unanswered)
            }

            EvaluationObjectType.SLIDER -> CreateSlider(obj, availableValues, onSaveAnswer)

            EvaluationObjectType.HUMAN_BODY -> {
                val currentSelectedHumanBodyParts =
                    (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toList()
                        ?: emptyList()

                HumanBodyModelSelector(
                    availableValues = availableValues,
                    selectedValues = currentSelectedHumanBodyParts,
                    onBodyPartClicked = { clickedPart ->
                        showDialogForBodyPart = clickedPart
                    }
                )
            }

            null -> {
                println("Unhandled object type: ${obj.object_type}")
                answers[obj.id]?.toRawString()?.let {
                    Text(it, fontSize = 24.sp)
                }
            }
        }
    }

    // MultiSelectPillDialog for Human Body Model
    showDialogForBodyPart?.let { clickedPart ->
        val dialogTitle = parseBodyPartTitle(clickedPart.available_value)
        val dialogOptions = parseBodyPartSubOptions(clickedPart.available_value)

        // Get current overall selections to set initial selection for this specific dialog
        val currentOverallSelectedParts =
            (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toSet() ?: emptySet()
        val initialDialogSelection =
            dialogOptions.filter { currentOverallSelectedParts.contains(it) }

        MultiSelectPillDialog(
            dialogTitle = dialogTitle,
            options = dialogOptions, // Pass granular sub-options
            initialSelection = initialDialogSelection,
            onDismiss = { showDialogForBodyPart = null },
            onSelectionConfirmed = { newlySelectedSubOptions ->
                // Get the current overall selected parts (mutable copy)
                val updatedOverallSelectedParts =
                    (answers[obj.id] as? EvaluationAnswer.MultiChoice)?.values?.toMutableSet()
                        ?: mutableSetOf()

                // Get the sub-options that belong to the clicked main body part's group
                val clickedPartSubOptions = parseBodyPartSubOptions(clickedPart.available_value)

                // Remove all old selections that belong to this clicked main body part's group
                updatedOverallSelectedParts.removeAll(clickedPartSubOptions.toSet())

                // Add the newly selected sub-options for this main body part
                updatedOverallSelectedParts.addAll(newlySelectedSubOptions)

                // Save the updated list of all granular selections
                onSaveAnswer(
                    obj.id,
                    EvaluationAnswer.MultiChoice(updatedOverallSelectedParts.toList())
                )
                showDialogForBodyPart = null
            }
        )
    }
}

@Composable
private fun CreateSlider(
    obj: EvaluationObject,
    availableValues: List<EvaluationValue>,
    onSaveAnswer: (Int, EvaluationAnswer) -> Unit
) {
    if (obj.style == "scale" || obj.style == "slider") {

        val regex = Regex("""(\d+)(?:\(\?\))?(.*)""")
        val (startValue, startText) = regex.find(availableValues[0].available_value)!!
            .destructured
            .let { it.component1().toFloat() to it.component2().trim() }

        val (endValue, endText) = regex.find(availableValues[1].available_value)!!
            .destructured
            .let { it.component1().toFloat() to it.component2().trim() }

        val step: Float = availableValues.getOrNull(2)
            ?.available_value
            ?.toFloatOrNull()
            ?.coerceAtLeast(0f) ?: 0f


        var currentValue by remember { mutableStateOf(0f) }


        println("Slider $obj")

        val numbers = floatRange(startValue, endValue, step)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RoundedFilledSlider(
                start = startValue,
                end = endValue,
                value = currentValue.coerceIn(startValue, endValue),
                availableValues = numbers,
                startText = startText.ifBlank { startValue.formatLabel() },
                endText = endText.ifBlank { endValue.formatLabel() },
                onValueChanged = { newValue ->
                    currentValue = newValue
                    onSaveAnswer(obj.id, EvaluationAnswer.Number(newValue))
                }
            )

        }
    }
}

fun floatRange(start: Float, end: Float, step: Float): List<Float> {
    val list = mutableListOf<Float>()
    var current = start
    while (current <= end) {
        list.add(current)
        current += step
    }
    return list
}