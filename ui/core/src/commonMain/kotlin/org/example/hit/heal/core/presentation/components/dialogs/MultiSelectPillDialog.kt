package org.example.hit.heal.core.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.OptionStyle
import org.example.hit.heal.core.presentation.components.PillSelectionGroup
import org.example.hit.heal.core.presentation.components.SelectionMode
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource


@Composable
fun MultiSelectPillDialog(
    dialogTitle: String,
    options: List<String>,
    initialSelection: List<String>,
    onDismiss: () -> Unit,
    onSelectionConfirmed: (List<String>) -> Unit
) {
    var selectedOptionTexts by remember { mutableStateOf(initialSelection.toSet()) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = dialogTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = primaryColor
                )

                PillSelectionGroup(
                    style = OptionStyle.STYLED,
                    options = options,
                    selectionMode = SelectionMode.MULTIPLE,
                    selectedValues = selectedOptionTexts.toList(),
                    onSelectionChanged = { newSelection ->
                        selectedOptionTexts = newSelection.toSet()
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(stringResource(Resources.String.cancel))
                    }
                    Button(
                        onClick = { onSelectionConfirmed(selectedOptionTexts.toList()) }
                    ) {
                        Text(stringResource(Resources.String.ok))
                    }
                }
            }
        }
    }
}