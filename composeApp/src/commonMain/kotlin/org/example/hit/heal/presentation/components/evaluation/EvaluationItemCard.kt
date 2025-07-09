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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
 * It shows the item's name, date, and frequency.
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
        elevation = elevationMd
    ) {
        Column(modifier = Modifier.padding(spacingMd)) {
            Text(text = item.measurement_name, style = androidx.compose.material.MaterialTheme.typography.subtitle1)
            Spacer(Modifier.height(spacingXs))
            Text(text = item.measurement_name, style = androidx.compose.material.MaterialTheme.typography.body2)
            Spacer(Modifier.height(spacingXs))
            Text(text = item.measurement_name, style = androidx.compose.material.MaterialTheme.typography.body2)
        }
    }
}