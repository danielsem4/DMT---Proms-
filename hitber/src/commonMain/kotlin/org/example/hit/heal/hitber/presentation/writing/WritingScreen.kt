package org.example.hit.heal.hitber.presentation.writing

import TabletBaseScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_icon
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.shapes.ActionShapesScreen
import org.example.hit.heal.hitber.presentation.writing.components.DraggableWordState
import org.example.hit.heal.hitber.presentation.writing.components.WordSlotState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = viewModel()
        val density = LocalDensity.current
        val isRTL = LocalLayoutDirection.current == LayoutDirection.Rtl
        val words by viewModel.words.collectAsState()
        val copiedWords by viewModel.copiedWords.collectAsState()
        val slots by viewModel.slotsWords.collectAsState()
        val allFinished by viewModel.allFinished.collectAsState()
        val sentencesResourceId by viewModel.answerSentences.collectAsState()
        val sentences = sentencesResourceId.map { stringResource(it) }


        TabletBaseScreen(
            title = "כתיבה",
            onNextClick = {
                if (allFinished) {navigator?.push(ActionShapesScreen(9))
                    val isCorrect = viewModel.checkSentence(sentences)
                    println("התוצאה: $isCorrect")}
            },
            buttonText = "המשך",
            question = 8,
            buttonColor = if (!allFinished) Color.Gray else primaryColor,
            content = {
                Text(
                    "בחלק זה עליך להרכיב משפט מהמילים המוצגות לפניך. לשיבוץ מילה במשפט יש לגרור אותה למשבצת שתבחר. אם תרצה לשנות את המיקום של מילה שכבר שובצה, צריך שוב לגרור אותה ממחסן המילים ולשבץ אותה מחדשץ בסיום לחץ על המשך.",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StaticWords(words, copiedWords, viewModel, density, isRTL)
                    WordSlots(slots, viewModel, density)
                }
            })
    }

    @Composable
    fun StaticWords(
        words: List<DraggableWordState>,
        copiedWords: List<DraggableWordState>,
        viewModel: ActivityViewModel,
        density: Density,
        isRTL: Boolean
    ) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize().onSizeChanged { newSize -> containerSize = newSize }) {
            DraggableWords(copiedWords, containerSize, viewModel, density, isRTL)
            words.forEach { wordState ->
                StaticWord(wordState = wordState, containerSize = containerSize, density)
            }
        }
    }

    @Composable
    fun StaticWord(wordState: DraggableWordState, containerSize: IntSize, density: Density) {
        val widthPx = (containerSize.width * 0.1f)
        val heightPx = (containerSize.height * 0.15f)
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (wordState.initialOffset.x * containerSize.width).toInt(),
                    (wordState.initialOffset.y * containerSize.height).toInt()
                )
            }
                .width(with(density) { widthPx.toDp() })
                .height(with(density) { heightPx.toDp() })
                .background(primaryColor, shape = RoundedCornerShape(50.dp))) {
            Text(
                text = stringResource(wordState.word),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun DraggableWords(
        copiedWords: List<DraggableWordState>,
        containerSize: IntSize,
        viewModel: ActivityViewModel,
        density: Density,
        isRTL: Boolean
    ) {
        Box(modifier = Modifier.fillMaxSize().zIndex(1f)) {
            copiedWords.forEach { wordState ->
                DraggableWord(wordState, containerSize, viewModel, density, isRTL)
            }
        }
    }


    @Composable
    fun DraggableWord(
        wordState: DraggableWordState,
        containerSize: IntSize,
        viewModel: ActivityViewModel,
        density: Density,
        isRTL: Boolean
    ) {
        val wordOffset = remember { Animatable(wordState.offset, Offset.VectorConverter) }
        val coroutineScope = rememberCoroutineScope()
        var wordColor by remember { mutableStateOf(primaryColor) }
        var isOnSlot by remember { mutableStateOf<Int?>(null) }
        val word = stringResource(wordState.word)
        val widthPx = (containerSize.width * 0.1f)
        val heightPx = (containerSize.height * 0.15f)

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

                            isOnSlot = viewModel.isWordOnSlot(
                                wordOffset.value,
                                containerSize,
                                density,
                                isRTL
                            )
                            wordColor =
                                if (isOnSlot != null) primaryColor.copy(alpha = 0.5f) else primaryColor

                        },

                        onDragEnd = {
                            wordColor = primaryColor
                            if (isOnSlot == null) {
                                coroutineScope.launch {
                                    wordOffset.animateTo(wordState.initialOffset)
                                }
                            } else {
                                coroutineScope.launch {
                                    wordOffset.snapTo(wordState.initialOffset)
                                }
                                viewModel.updateWordInSlot(word, isOnSlot!!)
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun WordSlots(
        slots: List<WordSlotState>,
        viewModel: ActivityViewModel,
        density: Density
    ) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { newSize -> containerSize = newSize }.zIndex(-1f)
        ) {
            slots.forEachIndexed { index, slot ->
                WordSlot(slot, index, containerSize = containerSize, viewModel = viewModel, density = density)
            }
        }
    }

    @Composable
    fun WordSlot(
        slot: WordSlotState,
        slotIndex: Int,
        containerSize: IntSize,
        viewModel: ActivityViewModel
        ,density: Density
    ) {
        val activeSlotIndex by viewModel.activeSlotIndex.collectAsState()
        val widthPx = (containerSize.width * 0.1f)
        val heightPx = (containerSize.width * 0.1f)
        LaunchedEffect(activeSlotIndex) {
            viewModel.updateSlotColor(activeSlotIndex ?: -1)
        }

        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (slot.initialOffset.x * containerSize.width).toInt(),
                    (slot.initialOffset.y * containerSize.height).toInt()
                )
            }
                .width(with(density) { widthPx.toDp() })
                .height(with(density) { heightPx.toDp() })
                .background(slot.color, shape = RoundedCornerShape(10.dp))
                .border(color = Color.White, width = 5.dp)
                .zIndex(-1f)
        ) {
            slot.word?.let {
                Image(painter = painterResource(Res.drawable.close_icon),
                    contentDescription = "Close icon",
                    modifier = Modifier.size(20.dp).align(Alignment.TopStart).padding(5.dp)
                        .clickable {
                            viewModel.resetSlot(slot.id)
                        })
                Text(
                    text = it,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }


}
