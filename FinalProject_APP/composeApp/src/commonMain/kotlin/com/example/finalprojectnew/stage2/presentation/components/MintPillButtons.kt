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

// צבעי ברירת־מחדל תואמי שלב 2
private val MintPill   = Color(0xFFEFF8F2)
private val FrameGreen = Color(0xFF0A4E2A)

/** כפתור “לחצו לצפייה בסל הקניות” */
@Composable
fun CartPillButtonMint(
    textLine1: String = "לחצו לצפייה",
    textLine2: String = "בסל הקניות",
    icon: Painter,
    onClick: () -> Unit,
    labelColor: Color = FrameGreen,
    modifier: Modifier = Modifier
) {
    PillButtonMint(
        textLine1 = textLine1,
        textLine2 = textLine2,
        icon = icon,
        onClick = onClick,
        labelColor = labelColor,
        modifier = modifier
    )
}

/** כפתור “לחצו לצפייה בסל התרומה” */
@Composable
fun DonationPillButtonMint(
    textLine1: String = "לחצו לצפייה",
    textLine2: String = "בסל התרומה",
    icon: Painter,
    onClick: () -> Unit,
    labelColor: Color = Color(0xFF1E4DD8), // כחול
    modifier: Modifier = Modifier
) {
    PillButtonMint(
        textLine1 = textLine1,
        textLine2 = textLine2,
        icon = icon,
        onClick = onClick,
        labelColor = labelColor,
        modifier = modifier
    )
}

@Composable
private fun PillButtonMint(
    textLine1: String,
    textLine2: String,
    icon: Painter,
    onClick: () -> Unit,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MintPill,
            contentColor   = labelColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(textLine1, fontSize = 20.sp)
                Text(textLine2, fontSize = 20.sp)
            }
            Spacer(Modifier.width(10.dp))
            Image(painter = icon, contentDescription = null, modifier = Modifier.size(50.dp))
        }
    }
}
