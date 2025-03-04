package org.example.hit.heal.hitber.writing

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.shapes.ActionShapesScreen

class WritingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseScreen(title = "כתיבה", onPrevClick = null, onNextClick = null, content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "בחלק זה עליך להרכיב משפט מהמילים המוצגות לפניך. לשיבוץ מילה במשפט יש לגרור אותה למשבצת שתבחר, אם תרצה לשנות את המיקום של מילה שכבר שובצה צריך שוב לגרור אותה מהמחסן מילים ולשבץ אותה מחדש",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 120.dp)
                    )

                    val words = "סבתא, ארוחת, עם, אתמול, אכלתי, ערב".split(", ")
                    DraggableWords(words = words)
                    WordSlots()

                }
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    onClick = {navigator?.push(ActionShapesScreen()) },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6FCF97)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "המשך", color = Color.White, fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        })

    }
}

@Composable
fun DraggableWords(words: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        words.forEach { word ->
            DraggableWord(word = word)
        }
    }
}

@Composable
fun DraggableWord(word: String) {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                }
            }
    ) {
        Button(
            onClick = { /* פעולה כשלוחצים על המילה (כפתור) */ },
            modifier = Modifier
                .width(120.dp)
                .height(60.dp)
                .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) },
            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = word,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
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
}

