package org.example.hit.heal.hitber.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.Text
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Sizes.paddingLg

import androidx.compose.ui.text.style.TextAlign

@Composable
fun InstructionText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = paddingLg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = LARGE,
            lineHeight = EXTRA_LARGE,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


