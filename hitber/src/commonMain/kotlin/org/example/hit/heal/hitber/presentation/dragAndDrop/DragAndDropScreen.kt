package org.example.hit.heal.hitber.presentation.dragAndDrop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import org.example.hit.heal.hitber.presentation.writing.WritingScreen
import kotlin.math.roundToInt

class DragAndDropScreen : Screen {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.current

            var circleGreenPosition by remember { mutableStateOf(Offset(150f, 50f)) }  // למעלה
            var circleYellowPosition by remember { mutableStateOf(Offset(50f, 150f)) } // שמאל
            var circleBlackPosition by remember { mutableStateOf(Offset(150f, 250f)) } // למטה
            var circleBluePosition by remember { mutableStateOf(Offset(250f, 150f)) } // ימינה

            BaseScreen(title = "גרור ושחרר", onPrevClick = null, onNextClick = null, content = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // ריבוע אדום
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .border(10.dp, Color.Red)
                            .align(Alignment.Center)
                    )

                    // עיגול ירוק
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Green, CircleShape)
                            .offset { IntOffset(circleGreenPosition.x.roundToInt(), circleGreenPosition.y.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    circleGreenPosition = Offset(
                                        circleGreenPosition.x + dragAmount.x,
                                        circleGreenPosition.y + dragAmount.y
                                    )
                                }
                            }
                    )

                    // עיגול צהוב
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Yellow, CircleShape)
                            .offset { IntOffset(circleYellowPosition.x.roundToInt(), circleYellowPosition.y.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    circleYellowPosition = Offset(
                                        circleYellowPosition.x + dragAmount.x,
                                        circleYellowPosition.y + dragAmount.y
                                    )
                                }
                            }
                    )

                    // עיגול שחור
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Black, CircleShape)
                            .offset { IntOffset(circleBlackPosition.x.roundToInt(), circleBlackPosition.y.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    circleBlackPosition = Offset(
                                        circleBlackPosition.x + dragAmount.x,
                                        circleBlackPosition.y + dragAmount.y
                                    )
                                }
                            }
                    )

                    // עיגול כחול
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Blue, CircleShape)
                            .offset { IntOffset(circleBluePosition.x.roundToInt(), circleBluePosition.y.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    circleBluePosition = Offset(
                                        circleBluePosition.x + dragAmount.x,
                                        circleBluePosition.y + dragAmount.y
                                    )
                                }
                            }
                    )

                    // כפתור "המשך"
                    Button(
                        modifier = Modifier
                            .width(200.dp)
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        onClick = { navigator?.push(WritingScreen())},
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