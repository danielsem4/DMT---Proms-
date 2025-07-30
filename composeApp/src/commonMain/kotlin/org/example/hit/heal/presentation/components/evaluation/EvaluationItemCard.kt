package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.data.model.evaluation.Evaluation
import org.example.hit.heal.core.presentation.OffWhite
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.spacingXs
import org.example.hit.heal.core.presentation.primaryColor

/**
 * EvaluationItemCard is a composable function that displays an evaluation item in a card format.
 * It shows the item's name, type, and additional minimal information like active status and repetition.
 */

@Composable
fun EvaluationItemCard(
    item: Evaluation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = spacingSm)
            .clickable(onClick = onClick)
            .background(OffWhite),
        shape = RoundedCornerShape(spacingMd),
        border = BorderStroke(1.dp, primaryColor),
        elevation = CardDefaults.cardElevation(elevationMd)
    ) {
        Column(
            modifier = Modifier.padding(spacingMd),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = item.measurement_name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(spacingXs))
            Text(
                text = if (item.is_active) "Status: Active" else "Status: Inactive",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(spacingXs))

            if (item.measurement_settings.is_repetitive) {
                Text(
                    text = "Repetitive: Yes, every ${item.measurement_settings.measurement_repeat_times} ${item.measurement_settings.measurement_repeat_period}",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "Repetitive: No",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}