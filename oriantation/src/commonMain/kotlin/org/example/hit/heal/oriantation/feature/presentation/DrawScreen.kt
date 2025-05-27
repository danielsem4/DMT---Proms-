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
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.TabletBaseScreen

class DrawScreen : Screen {
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
                        color = Colors.primaryColor,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
            }

            return bitmap
        }
        TabletBaseScreen(
            title = "שימוש בעת",
            question = 7,
            onNextClick = { navigator?.push(ShapesDragScreen()) },
            content = {
                Spacer(modifier = Modifier.height(16.dp))

                // Instruction text
                Text(
                    text = "יש לצייר צורה של איקס",
                    color = Colors.primaryColor,
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
                                    currentPath = Path().apply { moveTo(offset.x, offset.y) }
                                    currentPathPoints = listOf(offset)
                                },
                                onDrag = { change, dragAmount ->
                                    if (isDrawing) {
                                        val newOffset = change.position
                                        currentPath = currentPath?.apply { lineTo(newOffset.x, newOffset.y) }
                                        currentPathPoints = currentPathPoints + newOffset
                                    }
                                },
                                onDragEnd = {
                                    isDrawing = false
                                    currentPath?.let { paths.add(it) }
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
                            color = Colors.primaryColor,
                            style = Stroke(width = 4.dp.toPx())
                        )
                    }
                    // Draw current path
                    currentPath?.let { path ->
                        drawPath(
                            path = path,
                            color = Colors.primaryColor,
                            style = Stroke(width = 4.dp.toPx())
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


            }
        )




    }

    }
