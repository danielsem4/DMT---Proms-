package com.example.new_memory_test.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BulletPointText(
    text: String,
    fontSize: TextUnit = 26.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.Gray
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {

        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )
        Text(
            text = "•",
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}