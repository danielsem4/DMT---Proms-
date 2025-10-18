//A button used to choose a recipe

package com.example.finalprojectnew.presentation.stage1.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeButton(
    label: String, // (button - סלט\עוגה\פשטידה)
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFF3FF)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(220.dp)
            .height(90.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF2E3C9A),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
