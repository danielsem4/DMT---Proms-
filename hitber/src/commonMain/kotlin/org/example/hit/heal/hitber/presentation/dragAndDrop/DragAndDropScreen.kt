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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.seventh_question_hitbear_title
import io.github.suwasto.capturablecompose.Capturable
import io.github.suwasto.capturablecompose.rememberCaptureController
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.circleColors
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.circlePositions
import org.example.hit.heal.hitber.presentation.writing.WritingScreen
import org.example.hit.heal.hitber.utils.isObjectInsideTargetArea
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class DragAndDropScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val seventhQuestionViewModel: SeventhQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val captureController = rememberCaptureController()
        var screenSize by remember { mutableStateOf(0f to 0f) }
        val targetColor by seventhQuestionViewModel.targetCircleColor.collectAsState()
        val instructionsResourceId by seventhQuestionViewModel.instructionsResourceId.collectAsState()
        val instructions = instructionsResourceId?.let { stringResource(it) }
        var targetBoxXRange by remember { mutableStateOf(0f..0f) }
        var targetBoxYRange by remember { mutableStateOf(0f..0f) }

        val radiusPx = with(density) { 25.dp.toPx() }

        val circleOffsets = remember(screenSize) {
            mutableStateListOf<Offset>().apply {
                circlePositions.forEach { (xRatio, yRatio) ->
                    val x = screenSize.first * xRatio
                    val y = screenSize.second * yRatio
                    add(Offset(x, y))
                }
            }
        }

        TabletBaseScreen(
            title = stringResource(Res.string.seventh_question_hitbear_title),
            onNextClick = {
                captureController.capture()
            },
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

                Capturable(
                    captureController = captureController,
                    onCaptured = { imageBitmap ->
                        viewModel.uploadImage(
                            bitmap = imageBitmap,
                            date = getCurrentFormattedDateTime(),
                            currentQuestion = 7,
                            onSuccess = { },
                            onFailure = { }
                        )
                        targetColor?.let {
                            proceedToNext(
                                circleColors = circleColors,
                                circlePositions = circleOffsets,
                                targetColor = it,
                                targetBoxXRange = targetBoxXRange,
                                targetBoxYRange = targetBoxYRange,
                                radiusPx = radiusPx,
                                seventhQuestionViewModel = seventhQuestionViewModel
                            )
                            viewModel.setSeventhQuestion(
                                seventhQuestionViewModel.answer,
                                getCurrentFormattedDateTime()
                            )
                        }
                        navigator?.push(WritingScreen())
                    }
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize() .onSizeChanged { size ->
                                screenSize = size.width.toFloat() to size.height.toFloat()
                            }
                            .background(color = Color.White, shape = RoundedCornerShape(4))
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()

                                    val draggedIndex = circleOffsets.indexOfFirst { offset ->
                                        offset.distanceTo(change.position) < 50f
                                    }

                                    if (draggedIndex != -1) {
                                        val currentOffset = circleOffsets[draggedIndex]
                                        val newOffset = currentOffset + dragAmount
                                        circleOffsets[draggedIndex] = newOffset
                                    }
                                }
                            }
                    ) {
                        val rectSizePx = 150.dp.toPx()
                        val left = (size.width - rectSizePx) / 2
                        val top = (size.height - rectSizePx) / 2

                        targetBoxXRange = left..(left + rectSizePx)
                        targetBoxYRange = top..(top + rectSizePx)

                        drawRect(
                            color = Color.Red,
                            topLeft = Offset(left, top),
                            size = Size(rectSizePx, rectSizePx),
                            style = Stroke(width = 10.dp.toPx())
                        )

                        circleOffsets.forEachIndexed { index, offset ->
                            drawCircle(
                                color = circleColors.getOrElse(index) { Color.Gray },
                                center = offset,
                                radius = radiusPx
                            )
                        }
                    }

                }
            })
    }

private fun Offset.distanceTo(other: Offset): Float {
    return kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}

private fun proceedToNext(
    circleColors: List<Color>,
    circlePositions: List<Offset>,
    targetColor: Color,
    targetBoxXRange: ClosedFloatingPointRange<Float>,
    targetBoxYRange: ClosedFloatingPointRange<Float>,
    radiusPx: Float,
    seventhQuestionViewModel: SeventhQuestionViewModel
) {

    val diameter = 2 * radiusPx

        val correctCircleIndex = circleColors.indexOf(targetColor)
        val correctCirclePosition = circlePositions.getOrNull(correctCircleIndex)

        val draggablePosition = Offset(
            x = correctCirclePosition?.x ?: 0f,
            y = correctCirclePosition?.y ?: 0f
        )

        val isInside = isObjectInsideTargetArea(
            targetPosition = Offset(targetBoxXRange.start, targetBoxYRange.start),
            draggablePosition = draggablePosition,
            targetSize = (targetBoxXRange.endInclusive - targetBoxXRange.start) to (targetBoxYRange.endInclusive - targetBoxYRange.start),
            draggableSize = diameter to diameter,
            threshold = 50f,
            isCircle = true
        )
        seventhQuestionViewModel.seventhQuestionAnswer(isInside)

}}




