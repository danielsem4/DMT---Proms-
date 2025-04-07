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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.seventh_question_hitbear_title
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.writing.WritingScreen
import org.jetbrains.compose.resources.stringResource

class DragAndDropScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val viewModel: ActivityViewModel = viewModel()
        var screenSize by remember { mutableStateOf(0f to 0f) }
        val circlePositions by viewModel.circlePositions.collectAsState()
        val circleColors = listOf(Color.Black, Color.Green, Color.Blue, Color.Yellow)
        val instructionsResourceId by viewModel.instructionsResourceId.collectAsState()
        val instructions = instructionsResourceId?.let { stringResource(it) }
        var targetBoxXRange by remember { mutableStateOf(0f..0f) }
        var targetBoxYRange by remember { mutableStateOf(0f..0f) }

        LaunchedEffect(Unit) {
            viewModel.setRandomInstructions()
        }
        TabletBaseScreen(
            title = stringResource(Res.string.seventh_question_hitbear_title),
            onNextClick = { checkIfCorrectCircleInBox(viewModel, screenSize, density)
                navigator?.push(WritingScreen()) },
            buttonText = stringResource(Res.string.hitbear_continue),
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

                            with(density) {
                                val targetBoxSize = 150.dp.toPx()
                                val targetBoxXStart = (screenSize.first - targetBoxSize) / 2
                                val targetBoxYStart = (screenSize.second - targetBoxSize) / 2
                                targetBoxXRange = targetBoxXStart..(targetBoxXStart + targetBoxSize)
                                targetBoxYRange = targetBoxYStart..(targetBoxYStart + targetBoxSize)
                            }}
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()

                                val draggedIndex = circlePositions.indexOfFirst { (x, y) ->
                                    val center = Offset(x * screenSize.first, y * screenSize.second)
                                    center.distanceTo(change.position) < 50f
                                }

                                if (draggedIndex != -1) {
                                    val newPosition = Offset(
                                        dragAmount.x / screenSize.first,
                                        dragAmount.y / screenSize.second
                                    )
                                    viewModel.updateCirclePosition(draggedIndex, newPosition)
                                }
                            }
                        }


                ) {
                    val (screenWidth, screenHeight) = screenSize

                    drawRect(
                        color = Color.Red,
                        topLeft = Offset(
                            (screenWidth - 150.dp.toPx()) / 2,
                            (screenHeight - 150.dp.toPx()) / 2
                        ),
                        size = androidx.compose.ui.geometry.Size(150.dp.toPx(), 150.dp.toPx()),
                        style = Stroke(width = 10.dp.toPx())
                    )

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

fun checkIfCorrectCircleInBox(viewModel: ActivityViewModel, screenSize: Pair<Float, Float>?, density: Density){
    if (screenSize == null) return

    val (screenWidth, screenHeight) = screenSize
    val circlePositions = viewModel.circlePositions.value
    val targetColor = viewModel.targetCircleColor.value
    val circleColors = listOf(Color.Black, Color.Green, Color.Blue, Color.Yellow)

    density.run {
        val targetBoxSize = 150.dp.toPx()
        val targetBoxXStart = (screenWidth - targetBoxSize) / 2
        val targetBoxYStart = (screenHeight - targetBoxSize) / 2
        val targetBoxXRange = targetBoxXStart..(targetBoxXStart + targetBoxSize)
        val targetBoxYRange = targetBoxYStart..(targetBoxYStart + targetBoxSize)

        val correctCircleIndex = circleColors.indexOf(targetColor)
        val correctCirclePosition = circlePositions.getOrNull(correctCircleIndex) ?: return

        val circleCenterX = correctCirclePosition.first * screenWidth
        val circleCenterY = correctCirclePosition.second * screenHeight

        if (circleCenterX in targetBoxXRange && circleCenterY in targetBoxYRange) {
            viewModel.setAnswerDragAndDrop()
        }
    }}

