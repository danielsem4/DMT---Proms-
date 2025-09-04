package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Sizes.iconSizeMd
import org.example.hit.heal.core.presentation.Sizes.paddingNone
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoundedButton(
    text: Any,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = LARGE,
    icon: DrawableResource? = null,
    enabled: Boolean = true,
    buttonColor: Color = primaryColor,
    contentColor: Color = Color.White,
    iconColor: Color = Color.White,
    onClick: () -> Unit
) {
    val buttonText = when (text) {
        is StringResource -> stringResource(text)
        is String -> text
        else -> error("Text must be either String or StringResource")
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(buttonColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = buttonText,
                    tint = iconColor,
                    modifier = Modifier
                        .size(iconSizeMd)
                )
                Spacer(Modifier.width(spacingSm))
            }
            Text(
                text = buttonText,
                color = contentColor,
                fontSize = fontSize,
                modifier = Modifier.padding(horizontal = if (icon == null) paddingSm else paddingNone)
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