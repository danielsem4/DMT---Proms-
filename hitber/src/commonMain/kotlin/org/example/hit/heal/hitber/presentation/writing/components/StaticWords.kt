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
import androidx.compose.ui.unit.sp
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
        DraggableWords(words, screenSize, eightQuestionViewModel, density)
        words.forEach { wordState ->
            StaticWord(wordState = wordState, screenSize = screenSize, density)
        }
    }
}

@Composable
fun StaticWord(wordState: DraggableWordState, screenSize: IntSize, density: Density) {
    val widthPx = (screenSize.width * 0.1f)
    val heightPx = (screenSize.height * 0.15f)
    Box(
        modifier = Modifier.offset {
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
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}