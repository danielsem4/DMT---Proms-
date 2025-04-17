package com.clock.test.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clock.test.presentation.components.ClockTime
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clear_all_button_text
import dmt_proms.clock_test.generated.resources.draw_instruction
import dmt_proms.clock_test.generated.resources.draw_mode
import dmt_proms.clock_test.generated.resources.draw_screen_title
import dmt_proms.clock_test.generated.resources.drawing_instruction
import dmt_proms.clock_test.generated.resources.erase_mode
import dmt_proms.clock_test.generated.resources.finish_button_text
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

class DrawClockScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<ClockTestViewModel>()

        viewModel.startDrawingTimer()
        viewModel.updateFirstTime(ClockTime(12, 0))

        val currentTime by viewModel.drawTime.collectAsState()
        val formattedTitle = stringResource(Res.string.draw_screen_title, currentTime.toString())
        val instructions = stringResource(Res.string.draw_instruction, currentTime.toString())

        val paths = remember { mutableStateListOf<Path>() }
        var currentPath by remember { mutableStateOf<Path?>(null) }
        var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
        var isEraseMode by remember { mutableStateOf(false) }
        var isDrawing by remember { mutableStateOf(false) }
        var canvasSize by remember { mutableStateOf(Size.Zero) }
        val density = LocalDensity.current

        fun updatePathsInViewModel() {
            viewModel.updateDrawnPaths(paths.toList())
        }

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
            title = formattedTitle,
            topRightText = "1/3",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.08f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = instructions,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Colors.primaryColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.75f)
                            .border(2.dp, Colors.primaryColor, RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .onSizeChanged {
                                canvasSize = it.toSize()
                            }
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            if (!isEraseMode) {
                                                currentPathPoints = listOf(offset)
                                                currentPath = Path().apply {
                                                    moveTo(offset.x, offset.y)
                                                }
                                                isDrawing = true
                                            }
                                        },
                                        onDrag = { change, _ ->
                                            if (isEraseMode) {
                                                val touchPoint = change.position
                                                paths.removeAll { it.getBounds().contains(touchPoint) }
                                            } else {
                                                currentPathPoints = currentPathPoints + change.position
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
                                                updatePathsInViewModel()
                                            }
                                            isDrawing = false
                                        },
                                        onDragCancel = {
                                            currentPath = null
                                            currentPathPoints = emptyList()
                                            isDrawing = false
                                        }
                                    )
                                }
                                .clipToBounds()
                        ) {
                            paths.forEach { path ->
                                drawPath(
                                    path = path,
                                    color = Colors.primaryColor,
                                    style = Stroke(width = 4.dp.toPx())
                                )
                            }
                            currentPath?.let { path ->
                                drawPath(
                                    path = path,
                                    color = Colors.primaryColor,
                                    style = Stroke(width = 4.dp.toPx())
                                )
                            }
                        }

                        if (!isDrawing) {
                            Text(
                                text = stringResource(Res.string.drawing_instruction),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Colors.primaryColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RoundedButton(
                            text = stringResource(Res.string.clear_all_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                paths.clear()
                                viewModel.clearDrawnPaths()
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        RoundedButton(
                            text = if (isEraseMode) Res.string.draw_mode else Res.string.erase_mode,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isEraseMode = !isEraseMode
                            },
                            fontSize = 24.sp,
                            icon = if (isEraseMode) Icons.Default.Create else Icons.Default.Delete
                        )

                        Spacer(Modifier.width(8.dp))

                        RoundedButton(
                            text = stringResource(Res.string.finish_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.saveBitmap(drawPathsToBitmap())
                                viewModel.stopDrawingTimer()
                                navigator.replace(CompletionScreen())
                            }
                        )
                    }
                }
            }
        )
    }
}