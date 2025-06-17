package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoundedButton(
    text: Any, // Can be either String or StringResource
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    fontSize: TextUnit = 32.sp,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    val buttonText = when (text) {
        is StringResource -> stringResource(text)
        is String -> text
        else -> error("Text must be either String or StringResource")
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(primaryColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = buttonText,
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = buttonText,
                color = Color.White,
                fontSize = fontSize,
                modifier = Modifier.padding(horizontal = if (icon == null) 8.dp else 0.dp)
            )
        }
    }
    @Composable
fun RoundedButton(text: String, modifier: Modifier, onclick: () -> Unit) {
        Button(
            onClick = onclick, colors = ButtonDefaults.buttonColors(primaryColor),
            shape = RoundedCornerShape(50),
            modifier = modifier,
        ) {
            Text(text, color = Color.White, fontSize = 32.sp, modifier = Modifier.padding(horizontal = 8.dp))
        }
}

@Composable
fun RoundedButton(stringResource: StringResource, modifier: Modifier, onclick: () -> Unit) {
    RoundedButton(stringResource(stringResource), modifier, onclick)
}}