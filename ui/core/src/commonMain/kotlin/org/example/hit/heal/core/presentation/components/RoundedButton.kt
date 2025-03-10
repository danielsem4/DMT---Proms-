package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Colors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoundedButton(
    text: Any, // Can be either String or StringResource
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    fontSize: TextUnit = 32.sp
) {
    val buttonText = when (text) {
        is StringResource -> stringResource(text)
        is String -> text
        else -> error("Text must be either String or StringResource")
    }
    
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Colors.primaryColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = fontSize,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}