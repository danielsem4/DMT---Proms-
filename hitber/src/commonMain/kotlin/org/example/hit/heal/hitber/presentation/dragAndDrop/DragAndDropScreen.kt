package org.example.hit.heal.hitber.presentation.dragAndDrop

import TabletBaseScreen
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.writing.WritingScreen
import org.jetbrains.compose.resources.stringResource

class DragAndDropScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ActivityViewModel = viewModel()
        var screenSize by remember { mutableStateOf(0f to 0f) }
        val circlePositions by viewModel.circlePositions.collectAsState()
        val circleColors = listOf(Color.Black, Color.Green, Color.Blue, Color.Yellow)
        val instructionsResourceId by viewModel.instructionsResourceId.collectAsState()
        val instructions = instructionsResourceId?.let { stringResource(it) }

        LaunchedEffect(Unit) {
            viewModel.setRandomInstructions()
        }
        TabletBaseScreen(
            title = "גרור ושחרר",
            onNextClick = { navigator?.push(WritingScreen()) },
            buttonText = "המשך",
            buttonColor = primaryColor,
            question = 7,
            content = {
                if (instructions != null) {
                    Text(
                        instructions,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 30.dp)
                    )
                }
                Canvas(
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.White, shape = RoundedCornerShape(4))
                        .onSizeChanged { size ->
                            screenSize = size.width.toFloat() to size.height.toFloat()
                        }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()

                                val draggedIndex = circlePositions.indexOfFirst { (x, y) ->
                                    val center = Offset(x * screenSize.first, y * screenSize.second)
                                    center.distanceTo(change.position) < 50f  // משתמשים בפונקציה המתוקנת
                                }

                                if (draggedIndex != -1) {
                                    viewModel.updateCirclePosition(
                                        draggedIndex,
                                        Offset(
                                            dragAmount.x / screenSize.first,
                                            dragAmount.y / screenSize.second
                                        )
                                    )
                                }
                            }
                        }


                ) {
                    val (screenWidth, screenHeight) = screenSize

                    // ריבוע יעד אדום
                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(
                            (screenWidth - 150.dp.toPx()) / 2,  // הזזה שמאלה ב- 75.dp
                            (screenHeight - 150.dp.toPx()) / 2  // הזזה למעלה ב- 75.dp
                        ),
                        size = androidx.compose.ui.geometry.Size(150.dp.toPx(), 150.dp.toPx()),
                        style = Stroke(width = 10.dp.toPx()) // עובי המסגרת
                    )


                    // ציור עיגולים דינמי
                    circlePositions.forEachIndexed { index, (x, y) ->
                        drawCircle(
                            color = circleColors.getOrElse(index) { Color.Gray },
                            center = Offset(x * screenWidth, y * screenHeight),
                            radius = 25.dp.toPx()
                        )
                    }
                }
            })
    }

}

fun Offset.distanceTo(other: Offset): Float {
    return kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}
