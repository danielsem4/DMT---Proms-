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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clear_all_button_text
import dmt_proms.clock_test.generated.resources.draw_instruction
import dmt_proms.clock_test.generated.resources.draw_mode
import dmt_proms.clock_test.generated.resources.draw_screen_title
import dmt_proms.clock_test.generated.resources.erase_mode
import dmt_proms.clock_test.generated.resources.finish_button_text
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.jetbrains.compose.resources.stringResource

data class DrawClockScreen(
    private val onFinishClick: () -> Unit
) : Screen {
    private val time: String = "11:10"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val formattedTitle = stringResource(Res.string.draw_screen_title, time)
        val instructions = stringResource(Res.string.draw_instruction, time)

        val paths = remember { mutableStateListOf<Path>() }
        var currentPath by remember { mutableStateOf<Path?>(null) }
        var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
        var isEraseMode by remember { mutableStateOf(false) }

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
                    // Instructions Text
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

                    // Drawing Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.85f)
                            .border(2.dp, Colors.primaryColor, RoundedCornerShape(8.dp))
                            .background(Color.White)
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
                                            }
                                        },
                                        onDrag = { change, _ ->
                                            if (isEraseMode) {
                                                // Find and remove paths near the touch point
                                                val touchPoint = change.position
                                                paths.removeAll { path ->
                                                    // Check if the touch point is near the path
                                                    val bounds = path.getBounds()
                                                    bounds.contains(touchPoint)
                                                }
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
                                            }
                                        },
                                        onDragCancel = {
                                            currentPath = null
                                            currentPathPoints = emptyList()
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
                    }

                    Spacer(Modifier.height(8.dp))

                    // Buttons Row
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
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        // Toggle Draw/Erase Mode Button
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
                                navigator.push(CompletionScreen(
                                    onNextClick = {
                                        // Here you can navigate to the next screen in the flow
                                    }
                                ))
                            }
                        )
                    }
                }
            }
        )
    }
}
