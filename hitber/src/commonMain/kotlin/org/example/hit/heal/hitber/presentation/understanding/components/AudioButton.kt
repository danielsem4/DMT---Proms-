package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_listen
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_volume_icon
import dmt_proms.hitber.generated.resources.speaker
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AudioButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(primaryColor),
        shape = RoundedCornerShape(30),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.speaker),
                contentDescription = stringResource(Res.string.sixth_question_hitbear_volume_icon),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(30.dp)
                    .background(Color.Transparent)
            )
            Text(
                text = stringResource(Res.string.sixth_question_hitbear_listen),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

