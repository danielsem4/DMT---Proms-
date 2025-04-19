package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.evaluations.DrawingCanvasController

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    onDrawChanged: (Boolean) -> Unit = {}
): DrawingCanvasController {
    val density = LocalDensity.current
    var canvasSize by remember { mutableStateOf(Size(1f, 1f)) }

    val paths = remember { mutableStateListOf<Path>() }
    val currentStroke = remember { mutableStateListOf<Offset>() }
    var hasDrawn by remember { mutableStateOf(false) }

    fun notifyDrawn() {
        if (!hasDrawn) {
            hasDrawn = true
            onDrawChanged(true)
        }
    }

    val controller = remember {
        object : DrawingCanvasController {
            override fun drawPathsToBitmap(): ImageBitmap {
                val bitmap = ImageBitmap(
                    canvasSize.width.toInt().coerceAtLeast(1),
                    canvasSize.height.toInt().coerceAtLeast(1)
                )
                val canvas = Canvas(bitmap)
                val drawScope = CanvasDrawScope()

                drawScope.draw(
                    density = density,
                    layoutDirection = LayoutDirection.Ltr,
                    canvas = canvas,
                    size = canvasSize
                ) {
                    drawRect(Color.White, size = size)
                    paths.forEach {
                        drawPath(it, Colors.primaryColor, style = Stroke(width = 4.dp.toPx()))
                    }
                    if (currentStroke.size > 1) {
                        val path = Path().apply {
                            moveTo(currentStroke[0].x, currentStroke[0].y)
                            currentStroke.drop(1).forEach { lineTo(it.x, it.y) }
                        }
                        drawPath(path, Colors.primaryColor, style = Stroke(width = 4.dp.toPx()))
                    }
                }

                return bitmap
            }
        }
    }

    val cornerRadius = 32.dp
    val padding = 16.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.White)
            .onSizeChanged {
                canvasSize = Size(it.width.toFloat(), it.height.toFloat())
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentStroke.clear()
                        currentStroke.add(offset)
                        notifyDrawn()
                    },
                    onDrag = { change, _ ->
                        currentStroke.add(change.position)
                    },
                    onDragEnd = {
                        val path = Path().apply {
                            moveTo(currentStroke[0].x, currentStroke[0].y)
                            currentStroke.drop(1).forEach { lineTo(it.x, it.y) }
                        }
                        paths.add(path)
                        currentStroke.clear()
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            paths.forEach {
                drawPath(it, Colors.primaryColor, style = Stroke(width = 4f))
            }

            if (currentStroke.size > 1) {
                val path = Path().apply {
                    moveTo(currentStroke[0].x, currentStroke[0].y)
                    currentStroke.drop(1).forEach { lineTo(it.x, it.y) }
                }
                drawPath(path, Colors.primaryColor, style = Stroke(width = 4f))
            }
        }
    }

    return controller
}
