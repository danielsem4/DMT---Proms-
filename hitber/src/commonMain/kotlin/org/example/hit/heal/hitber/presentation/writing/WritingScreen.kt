package org.example.hit.heal.hitber.presentation.writing

import TabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = viewModel()

        val words by viewModel.words.collectAsState()
        val copiedWords by viewModel.copiedWords.collectAsState()

        TabletBaseScreen(
            title = "כתיבה",
            onNextClick = {},
            buttonText = "המשך",
            question = 8,
            buttonColor = primaryColor,
            content = {
                Text(
                    "בחלק זה עליך להרכיב משפט מהמילים המוצגות לפניך. לשיבוץ מילה במשפט יש לגרור אותה למשבצת שתבחר.",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                ) {

                    WordsLayout(viewModel = viewModel, words = words, copiedWords = copiedWords) { word ->
                        viewModel.addCopiedWord(word) // הוספת מילה לגרירה
                    }
                }
            })
    }

    @Composable
    fun WordsLayout(
        viewModel: ActivityViewModel,
        words: List<DraggableWordState>,
        copiedWords: List<DraggableWordState>,
        onWordDragged: (String) -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StaticWords(words = words, viewModel = viewModel, onWordDragged = onWordDragged)

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                copiedWords.forEach { word ->
//                    DraggableWord(word = word, viewModel = viewModel)
//                }
//            }

            WordSlots()
        }
    }

    @Composable
    fun StaticWords(
        words: List<DraggableWordState>, // כאן אתה מקבל את הרשימה של DraggableWordState
        onWordDragged: (String) -> Unit,
        viewModel: ActivityViewModel // הוספתי את ה-ViewModel
    ) {
        val draggingWords = remember { mutableStateListOf<String>() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            words.forEach { word ->
                StaticWord(
                    word = word.word, // השתמש במילה מתוך ה-DraggableWordState
                    viewModel = viewModel,
                    onWordDragged = onWordDragged,
                    onDragStart = { draggedWord ->
                        if (draggedWord !in draggingWords) {
                            draggingWords.add(draggedWord)
                        }
                    }
                )
            }
        }

        // הצגת המילים שנגררות כרגע
        draggingWords.forEach { draggedWord ->
            DraggableWord(word = DraggableWordState(draggedWord), viewModel = viewModel)
        }
    }

    @Composable
    fun StaticWord(
        word: String,
        onWordDragged: (String) -> Unit,
        onDragStart: (String) -> Unit,
        viewModel: ActivityViewModel
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(60.dp)
                .background(primaryColor, shape = RoundedCornerShape(50.dp))
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        onDragStart(word) // מתחילים לגרור את המילה
                        onWordDragged(word) // מעדכנים את המילה שנגררה
                    }
                }
                .padding(16.dp)
        ) {
            Text(
                text = word,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun DraggableWord(word: DraggableWordState, viewModel: ActivityViewModel) {
        var offset by remember { mutableStateOf(Offset(0f, 0f)) }

        Box(
            modifier = Modifier
                .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) } // עדכון ה-offset של ה-Box
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        // עדכון המיקום של ה-Box לפי תנועת הגרירה
                        offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                        viewModel.updateWordOffset(word.word, offset) // עדכון המיקום במודל
                    }
                }
                .background(primaryColor, shape = RoundedCornerShape(50.dp))
                .padding(16.dp)
        ) {
            Text(
                text = word.word,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center) // טקסט במרכז ה-Box
            )
        }
    }

    @Composable
    fun WordSlots() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                WordSlot()
            }
        }
    }

    @Composable
    fun WordSlot() {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(100.dp)
                .background(Color.Gray, shape = RoundedCornerShape(10.dp))
        )
    }}
