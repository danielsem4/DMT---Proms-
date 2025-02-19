package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.evaluations.presentaion.components.OnOffToggle
import org.example.hit.heal.evaluations.presentaion.components.SimpleRadioButtonGroup

@Composable
fun ReportExample(): @Composable() (ColumnScope.() -> Unit) {
    var selectedOption by remember { mutableStateOf("") }
    val options = listOf("Hallucination", "Fall", "Dyskinesia")

    return {
        OnOffToggle()

        Spacer(modifier = Modifier.height(8.dp))

        SimpleRadioButtonGroup(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
            }
        )
    }
}