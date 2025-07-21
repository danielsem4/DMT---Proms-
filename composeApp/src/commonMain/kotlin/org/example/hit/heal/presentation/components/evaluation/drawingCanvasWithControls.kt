package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import core.domain.use_case.DrawPathsToBitmapUseCase
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.primaryColor

@Composable
fun drawingCanvasWithControls(
    modifier: Modifier = Modifier
): DrawingCanvasController {
    val paths = remember { mutableStateListOf<Path>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var isEraseMode by remember { mutableStateOf(false) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    val density = LocalDensity.current

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(1f)
                .clipToBounds()
                .border(2.dp, primaryColor, RoundedCornerShape(8.dp))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            if (!isEraseMode) {
                                currentPathPoints = listOf(offset)
                                currentPath = Path().apply {
                                    moveTo(offset.x, offset.y)
                                }
                            }
                        },
                        onDrag = { change, _ ->
                            if (isEraseMode) {
                                val touchPoint = change.position
                                paths.removeAll {
                                    it.getBounds().contains(touchPoint)
                                }
                            } else {
                                currentPathPoints += change.position
                                currentPath = Path().apply {
                                    moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                                    currentPathPoints.forEach { point ->
                                        lineTo(point.x, point.y)
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            if (!isEraseMode) {
                                currentPath?.let { paths.add(it) }
                                currentPath = null
                                currentPathPoints = emptyList()
                            }
                        },
                        onDragCancel = {
                            currentPath = null
                            currentPathPoints = emptyList()
                        }
                    )
                }
                .onSizeChanged { layoutSize ->
                    canvasSize = layoutSize.toSize()
                }
        ) {
            val draw: (Path) -> Unit = { path ->
                drawPath(path, primaryColor, style = Stroke(width = 4.dp.toPx()))
            }
            paths.forEach(draw)
            currentPath?.let(draw)
        }


        Spacer(Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(
                text = "Clear",
                onClick = {
                    paths.clear()
                    currentPath = null
                    currentPathPoints = emptyList()
                }
            )

            RoundedButton(
                text = if (isEraseMode) "Draw" else "Erase",
                onClick = { isEraseMode = !isEraseMode }
            )
        }
    }

    return remember {
        DrawingCanvasController(getBitmap = {
            println("Canvas size: $canvasSize")
            DrawPathsToBitmapUseCase.drawPathsToBitmap(canvasSize, paths, density)
        }, hasPaths = { paths.isNotEmpty() })
    }
}

class DrawingCanvasController(
    val getBitmap: () -> ImageBitmap,
    val hasPaths: () -> Boolean
)
