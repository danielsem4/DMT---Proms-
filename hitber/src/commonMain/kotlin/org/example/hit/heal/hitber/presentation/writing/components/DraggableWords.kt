package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.model.DraggableWordState
import org.jetbrains.compose.resources.stringResource

@Composable
fun DraggableWords(
    copiedWords: List<DraggableWordState>,
    screenSize: IntSize,
    eightQuestionViewModel: EightQuestionViewModel,
    density: Density
) {
    Box(modifier = Modifier.fillMaxSize().zIndex(1f)) {
        // Render each draggable word on the screen
        copiedWords.forEach { wordState ->
            DraggableWord(wordState, screenSize, eightQuestionViewModel, density)
        }
    }
}


@Composable
fun DraggableWord(
    wordState: DraggableWordState,
    screenSize: IntSize,
    eightQuestionViewModel: EightQuestionViewModel,
    density: Density
) {
    val wordOffset = remember { Animatable(wordState.offset, Offset.VectorConverter) }
    val coroutineScope = rememberCoroutineScope()
    var wordColor by remember { mutableStateOf(primaryColor) }
    var isOnSlot by remember { mutableStateOf<Int?>(null) }
    val word = stringResource(wordState.word)
    val widthPx = (screenSize.width * 0.1f)
    val heightPx = (screenSize.height * 0.15f)


    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    (wordOffset.value.x * screenSize.width).toInt(),
                    (wordOffset.value.y * screenSize.height).toInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { },
                    onDrag = { _, dragAmount ->
                        val newX = wordOffset.value.x + dragAmount.x / (screenSize.width)
                        val newY = wordOffset.value.y + dragAmount.y / (screenSize.height)

                        coroutineScope.launch {
                            wordOffset.snapTo(Offset(newX, newY))
                        }

                        // Check if dragged word is currently over a valid slot
                        isOnSlot = eightQuestionViewModel.isWordOnSlot(
                            wordOffset.value,
                            screenSize,
                            density
                        )
                        // Change color alpha to indicate valid drop target
                        wordColor =
                            if (isOnSlot != null) primaryColor.copy(alpha = 0.5f) else primaryColor

                    },

                    onDragEnd = {
                        wordColor = primaryColor
                        if (isOnSlot == null) {
                            // Animate word back to its initial position if not dropped on any slot
                            coroutineScope.launch {
                                wordOffset.animateTo(wordState.initialOffset)
                            }
                        } else {
                            // Snap word to initial position after successful drop
                            coroutineScope.launch {
                                wordOffset.snapTo(wordState.initialOffset)
                            }
                            // Notify that word was dropped on a valid slot
                            eightQuestionViewModel.updateWordInSlot(word, isOnSlot!!)
                        }
                    }

                )
            }
            .width(with(density) { widthPx.toDp() })
            .height(with(density) { heightPx.toDp() })
            .background(wordColor, shape = RoundedCornerShape(50.dp))
            .zIndex(1f)
    ) {
        Text(
            text = stringResource(wordState.word),
            color = Color.White,
            fontSize = EXTRA_MEDIUM,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }

    // Update animated position instantly when the source offset changes
    LaunchedEffect(wordState.offset) {
        wordOffset.snapTo(wordState.offset)
    }
}

