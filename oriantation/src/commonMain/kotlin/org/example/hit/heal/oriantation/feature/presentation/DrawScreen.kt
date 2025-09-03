package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.trialDrawInstructions
import org.example.hit.heal.core.presentation.Resources.String.trialDrawTitle
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource

/**
 * Base screen where user can draw a shape
 */

class DrawScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val paths = remember { mutableStateListOf<Path>() }
        var canvasSize by remember { mutableStateOf(Size.Zero) }
        val density = LocalDensity.current
        var currentPath by remember { mutableStateOf<Path?>(null) }
        var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
        var isEraseMode by remember { mutableStateOf(false) }
        var isDrawing by remember { mutableStateOf(false) }

        fun drawPathsToBitmap(): ImageBitmap {
            val bitmap = ImageBitmap(canvasSize.width.toInt(), canvasSize.height.toInt())
            val canvas = Canvas(bitmap)
            val drawScope = CanvasDrawScope()

            drawScope.draw(
                density = density,
                layoutDirection = LayoutDirection.Ltr,
                canvas = canvas,
                size = canvasSize
            ) {
                drawRect(
                    color = Color.White,
                    size = size
                )
                paths.forEach { path ->
                    drawPath(
                        path = path,
                        color = primaryColor,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }
            return bitmap
        }
        TabletBaseScreen(
            title = stringResource(trialDrawTitle),
            question = 7,
            onNextClick = {
                val bitmap = drawPathsToBitmap()
                viewModel.updateDrawnShape(bitmap)
                navigator?.push(FeedbackScreen()) },
            content = {
                Spacer(modifier = Modifier.height(16.dp))

                // Instruction text
                Text(
                    text = (stringResource(trialDrawInstructions)),
                    color = primaryColor,
                    fontSize = LARGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center

                )
                // Drawing area
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                        .background(Color.White)
                        .border(1.dp, Color.Gray)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    isDrawing = true
                                    currentPathPoints = listOf(offset)
                                    currentPath = Path().apply { moveTo(offset.x, offset.y) }
                                },
                                onDrag = { change, _ ->
                                    if (isDrawing) {
                                        currentPathPoints = currentPathPoints + change.position
                                        currentPath = Path().apply {
                                            moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                                            currentPathPoints.drop(1).forEach { point ->
                                                lineTo(point.x, point.y)
                                            }
                                        }
                                    }
                                },
                                onDragEnd = {
                                    isDrawing = false
                                    currentPath?.let { paths.add(it) }
                                    currentPath = null
                                    currentPathPoints = emptyList()
                                },
                                onDragCancel = {
                                    isDrawing = false
                                    currentPath = null
                                    currentPathPoints = emptyList()
                                }
                            )
                        }
                        .onSizeChanged { size ->
                            canvasSize = Size(size.width.toFloat(), size.height.toFloat())
                        }
                ) {
                    // Draw all paths
                    paths.forEach { path ->
                        drawPath(
                            path = path,
                            color = primaryColor,
                            style = Stroke(width = 4.dp.toPx())
                        )
                    }
                    // Draw current path
                    currentPath?.let { path ->
                        drawPath(
                            path = path,
                            color = primaryColor,
                            style = Stroke(width = 4.dp.toPx())
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        )
        RegisterBackHandler(this) {
            navigator?.popUntilRoot()
        }
    }

}
