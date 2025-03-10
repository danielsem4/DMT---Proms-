package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun RoundedButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(Colors.primaryColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
    ) {
        Text(text, color = Color.White, fontSize = 32.sp, modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun RoundedButton(stringResource: StringResource, modifier: Modifier = Modifier, onClick: () -> Unit) {
    RoundedButton(stringResource(stringResource), modifier, onClick)
}