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
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm


//Text in the start of modules (MemoryScreen)
@Composable
fun BulletPointText(
    text: String,
    fontSize: TextUnit = EXTRA_MEDIUM_LARGE ,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.Gray
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingMd, vertical = paddingSm),
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
        //Only for bullet point
        Text(
            text = "•",
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            modifier = Modifier.padding(start = paddingSm)
        )
    }
}