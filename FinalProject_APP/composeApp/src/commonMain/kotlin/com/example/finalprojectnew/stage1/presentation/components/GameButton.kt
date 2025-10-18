//A button component we can reuse across screens, with custom text, color, size, and click handling.

package com.example.finalprojectnew.presentation.stage1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF2E3C9A),
    textColor: Color = Color.White,
    isEnabled: Boolean = true,
    textSize: Int = 32
) {
    Box(
        modifier = modifier
            .background(
                color = if (isEnabled) backgroundColor else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = textSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
