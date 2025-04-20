package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.primaryColor

/**
 * Simple component for a multi-select question.
 * This component allows users to select multiple options from a list.
 * The ui is for simple checkboxes.
 */

@Composable
fun MultiSelectQuestion(
    options: List<String>,
    selectedValues: MutableList<String>,
    onSelectionChanged: (List<String>) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = selectedValues.contains(option)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSelected) primaryColor.copy(alpha = 0.1f) else Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        if (isSelected) {
                            selectedValues.remove(option)
                        } else {
                            selectedValues.add(option)
                        }
                        onSelectionChanged(selectedValues)
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedValues.contains(option),
                    onCheckedChange = { isChecked ->
                        val updated = selectedValues.toMutableList()
                        if (isChecked) updated.add(option) else updated.remove(option)
                        onSelectionChanged(updated)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option,
                    fontSize = EXTRA_REGULAR,
                    color = if (isSelected) primaryColor else Black
                )
            }
        }
    }
}