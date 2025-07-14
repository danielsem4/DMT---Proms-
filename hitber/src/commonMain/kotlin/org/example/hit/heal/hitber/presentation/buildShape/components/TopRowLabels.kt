package org.example.hit.heal.hitber.presentation.buildShape.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberShapeModel
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberShapes
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopRowLabels() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingXl),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(tenthQuestionHitberShapeModel),
            color = Color.Black,
            fontSize = EXTRA_MEDIUM,
            fontWeight = FontWeight.Bold
        )
        Text(
            stringResource(tenthQuestionHitberShapes),
            color = Color.Black,
            fontSize = EXTRA_MEDIUM,
            fontWeight = FontWeight.Bold
        )
    }
}