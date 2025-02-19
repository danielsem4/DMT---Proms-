package org.example.hit.heal.evaluations.presentaion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.example.hit.heal.evaluations.presentaion.primaryColor

@Composable
fun SimpleRadioButtonGroup(
    modifier: Modifier = Modifier,
    textColor: Color = primaryColor,
    textSize: TextUnit = 24.sp,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    colors: RadioButtonColors = RadioButtonDefaults.colors(selectedColor = primaryColor),
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = {
                        onOptionSelected(option)
                    },
                    colors = colors
                )
                Text(
                    text = option,
                    color = textColor,
                    fontSize = textSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = maxLines,
                    minLines = minLines
                )
            }
        }
    }
}