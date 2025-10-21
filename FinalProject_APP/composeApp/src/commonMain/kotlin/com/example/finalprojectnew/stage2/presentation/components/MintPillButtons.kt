// shopping cart - component button

package com.example.finalprojectnew.stage2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MintPill = Color(0xFFEFF8F2)
private val FrameGreen = Color(0xFF0A4E2A)

@Composable
fun CartPillButtonMint(
    textLine1: String = "לחצו לצפייה\nבסל הקניות",
    icon: Painter,
    onClick: () -> Unit,
    labelColor: Color = FrameGreen,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MintPill,
            contentColor = labelColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(textLine1, fontSize = 20.sp)
            }
            Spacer(Modifier.width(10.dp))
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
