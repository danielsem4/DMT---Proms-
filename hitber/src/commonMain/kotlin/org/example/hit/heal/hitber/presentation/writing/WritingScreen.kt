package org.example.hit.heal.hitber.presentation.writing

import TabletBaseScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = viewModel()

        val words by viewModel.words.collectAsState()
        val copiedWords by viewModel.copiedWords.collectAsState()
        val slots by viewModel.slotsWords.collectAsState()


        TabletBaseScreen(
            title = "כתיבה",
            onNextClick = {},
            buttonText = "המשך",
            question = 8,
            buttonColor = Color.Gray,
            content = {
                Text(
                    "בחלק זה עליך להרכיב משפט מהמילים המוצגות לפניך. לשיבוץ מילה במשפט יש לגרור אותה למשבצת שתבחר.",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                        Box(
                            modifier = Modifier.fillMaxWidth() .weight(1f)){
                            StaticWords(words)
                            DraggableWords(copiedWords)}

                        Box(
                            modifier = Modifier.fillMaxWidth() .weight(1f)){
                            WordSlots(slots) { slotIndex, word ->
                                viewModel.updateSlot(slotIndex, word)
                            }}

            })
    }

    @Composable
    fun StaticWords(words: List<DraggableWordState>) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
                .onSizeChanged { newSize -> containerSize = newSize } // לוקח את גודל המסך
        ) {
            words.forEach { wordState ->
                StaticWord(wordState = wordState, containerSize = containerSize)
            }
        }
    }

    @Composable
    fun StaticWord(wordState: DraggableWordState, containerSize: IntSize) {
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (wordState.initialOffset.x * containerSize.width).toInt(),
                    (wordState.initialOffset.y * containerSize.height).toInt()
                )}
                .width(70.dp)
                .height(40.dp)
                .background(primaryColor, shape = RoundedCornerShape(50.dp))

        ) {
            Text(
                text = wordState.word,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun DraggableWords(copiedWords: List<DraggableWordState>) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
                .onSizeChanged { newSize -> containerSize = newSize }
        ) {
            copiedWords.forEach { wordState ->
                DraggableWord(wordState, containerSize)
            }
        }
    }



    @Composable
    fun DraggableWord(wordState: DraggableWordState, containerSize: IntSize) {
        val wordOffset = remember { Animatable(wordState.offset, Offset.VectorConverter) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(wordState.offset) {
            wordOffset.snapTo(wordState.offset)
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (wordOffset.value.x * containerSize.width).toInt(),
                        (wordOffset.value.y * containerSize.height).toInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { },
                        onDrag = { _, dragAmount ->
                            val newX = wordOffset.value.x - dragAmount.x / (containerSize.width)
                            val newY = wordOffset.value.y + dragAmount.y / (containerSize.height)
                            coroutineScope.launch {
                                wordOffset.snapTo(Offset(newX, newY))
                            }
                        },
                            onDragEnd = {
                                coroutineScope.launch {

                                    wordOffset.animateTo(wordState.initialOffset) // החזרה למיקום ההתחלתי עם אנימציה
                                }
                            }
                    )
                }
                .width(70.dp)
                .height(40.dp)
                .background(primaryColor, shape = RoundedCornerShape(50.dp))
                .zIndex(1f)
        ) {
            Text(
                text = wordState.word,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }



    @Composable
    fun WordSlots(
        slots: List<WordSlotState>,
        onWordDropped: (Int, String?) -> Unit
    ) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
                .onSizeChanged { newSize -> containerSize = newSize } // לוקח את גודל המסך
        ) {
            slots.forEachIndexed { index, slot ->
                WordSlot(slot, onWordDropped, index, containerSize = containerSize)
            }
        }
    }

    @Composable
    fun WordSlot(
        slot: WordSlotState,
        onWordDropped: (Int, String?) -> Unit,
        slotIndex: Int,
        containerSize: IntSize
    ) {
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (slot.initialOffset.x * containerSize.width).toInt(),
                    (slot.initialOffset.y * containerSize.height).toInt()
                )}
                .width(150.dp)
                .height(100.dp)
                .background(if (slot.isOccupied) Color.Green else Color.Gray, shape = RoundedCornerShape(10.dp))
                .pointerInput(Unit) {
                    detectDragGestures { _, _ ->
                        // Handle drop logic
                        onWordDropped(slotIndex, slot.word)
                    }
                }.zIndex(-1f)
        ) {
            slot.word?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
