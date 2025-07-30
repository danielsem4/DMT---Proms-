package com.example.new_memory_test.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import org.example.hit.heal.core.presentation.Sizes.iconSize3Xl
import org.example.hit.heal.core.presentation.Sizes.widthLg


// Big buttons for Call Screen
@Composable
fun CircleWithPipeImage(
    modifier: Modifier = Modifier.size(widthLg),
    imagePainter: Painter,
    color: Color = Color.Red,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Pipe",
            modifier = Modifier.size(iconSize3Xl),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}