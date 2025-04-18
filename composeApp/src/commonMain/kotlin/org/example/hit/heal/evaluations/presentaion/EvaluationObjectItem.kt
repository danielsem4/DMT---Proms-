package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField
import org.example.hit.heal.core.presentation.components.OnOffToggle
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider
import org.example.hit.heal.core.presentation.components.SimpleRadioButtonGroup
import org.example.hit.heal.evaluations.domain.EvaluationObject

@Composable
fun EvaluationObjectItem(obj: EvaluationObject) {
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
                var text by remember { mutableStateOf(obj.answer) }
                CustomMultilineTextField(
                    value = text,
                    onValueChange = { text = it },
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
                    onOptionSelected = { selectedOption = it }
                )
            }

            // 4 = Multiple choice (checkboxes)
            4 -> {
                MultiSelectCheckboxGroup(
                    options = obj.availableValues?.map { it.value } ?: listOf(),
                    selectedValues = remember { mutableStateListOf() },
                    onSelectionChanged = {}
                )
            }

            // 5 = Toggle button
            5 -> {
                OnOffToggle()
            }

            // 11 = Human body model
            11 -> {
                // Placeholder to invoke actual screen or component if needed
                Text("Body Model Question (Tap to open full screen)", modifier = Modifier.padding(4.dp))
            }

            // 21 = Drawing area
            21 -> {
                Text("Drawing Canvas Placeholder", modifier = Modifier.padding(4.dp))
            }

            // 34 = Scale (slider)
            34 -> {
                var sliderValue by remember { mutableStateOf(5f) }
                RoundedFilledSlider(
                    start = 0f,
                    end = 10f,
                    onValueChanged = { sliderValue = it }
                )
                Text("Value: ${sliderValue.toInt()}")
            }

            else -> {
                Text("Unsupported question type: ${obj.objectType}")
            }
        }
    }
}
