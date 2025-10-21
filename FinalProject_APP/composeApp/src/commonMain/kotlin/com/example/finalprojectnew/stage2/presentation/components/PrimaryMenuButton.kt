// buttons that the user goes to categories, the shopping cart, search, or lists.
package com.example.finalprojectnew.stage2.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryMenuButton(
    text: String,
    icon: Painter? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 72.dp,
    corner: Dp = 22.dp,
    containerColor: Color = Color(0xFF046030),
    contentColor: Color = Color(0xFFF1FFF6),
    textSizeSp: Float = 24f,
    iconSize: Dp = 28.dp,
    spacing: Dp = 10.dp
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(corner),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier.height(height)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = textSizeSp.sp, fontWeight = FontWeight.Bold)
            if (icon != null) {
                Spacer(Modifier.width(spacing))
                Icon(painter = icon, contentDescription = null, modifier = Modifier.size(iconSize))
            }
        }
    }
}
