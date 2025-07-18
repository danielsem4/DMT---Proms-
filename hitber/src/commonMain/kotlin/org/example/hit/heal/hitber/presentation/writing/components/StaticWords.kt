package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.model.DraggableWordState
import org.jetbrains.compose.resources.stringResource

@Composable
fun StaticWords(
    words: List<DraggableWordState>,
    eightQuestionViewModel: EightQuestionViewModel,
    density: Density
) {
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize().onSizeChanged { newSize -> screenSize = newSize }) {
        // Render the draggable words
        DraggableWords(words, screenSize, eightQuestionViewModel, density)

        // Draw each static word in its initial position
        words.forEach { wordState ->
            StaticWord(wordState = wordState, screenSize = screenSize, density)
        }
    }
}

@Composable
fun StaticWord(wordState: DraggableWordState, screenSize: IntSize, density: Density) {
    // Calculate width and height of each word box as percentage of screen size in pixels
    val widthPx = (screenSize.width * 0.1f)
    val heightPx = (screenSize.height * 0.15f)
    Box(
        modifier = Modifier.offset {
            // Position each word by its initial offset scaled to current screen size
            IntOffset(
                (wordState.initialOffset.x * screenSize.width).toInt(),
                (wordState.initialOffset.y * screenSize.height).toInt()
            )
        }
            .width(with(density) { widthPx.toDp() })
            .height(with(density) { heightPx.toDp() })
            .background(primaryColor, shape = RoundedCornerShape(50.dp))
    ) {
        Text(
            text = stringResource(wordState.word),
            color = Color.White,
            fontSize = EXTRA_MEDIUM,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}