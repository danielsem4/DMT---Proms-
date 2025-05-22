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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_shape_model
import dmt_proms.hitber.generated.resources.tenth_question_hitbear_shapes
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopRowLabels() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(Res.string.tenth_question_hitbear_shape_model),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            stringResource(Res.string.tenth_question_hitbear_shapes),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}