// generic component for donation/cart list
package com.example.finalprojectnew.stage2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.finalprojectnew.stage2.presentation.Stage2Colors

@Composable
fun ListPillButton(
    text: String, // what written on the button - donation/cart list
    iconPainter: Painter?, // the icon near the text on the button
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

    height: Dp = 56.dp,
    corner: Dp = 28.dp,
    borderWidth: Dp = 2.dp,
    iconSize: Dp = 24.dp,
    textSizeSp: Float = 20f,
    horizontalPadding: Dp = 22.dp,
    verticalPadding: Dp = 12.dp,

    edgeColor: Color = Stage2Colors.FrameGreen,
    fillColor: Color = Stage2Colors.TileWhite,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(corner),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = fillColor,
            contentColor   = edgeColor
        ),
        border = BorderStroke(borderWidth, edgeColor),
        contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = verticalPadding),
        modifier = modifier.height(height)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                fontSize = textSizeSp.sp,
                fontWeight = FontWeight.Bold,
                color = edgeColor
            )
            if (iconPainter != null) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = edgeColor,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}
