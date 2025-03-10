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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clear_all_button_text
import dmt_proms.clock_test.generated.resources.draw_instruction
import dmt_proms.clock_test.generated.resources.draw_screen_title
import dmt_proms.clock_test.generated.resources.erase_button_text
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
        // Title with time placeholder
        val formattedTitle = stringResource(Res.string.draw_screen_title, time)
        // Instructions with time placeholder
        val instructions = stringResource(Res.string.draw_instruction, time)

        // Manage drawn paths
        val paths = remember { mutableStateListOf<Path>() }
        var currentPath by remember { mutableStateOf<Path?>(null) }
        var currentPathPoints by remember { mutableStateOf<List<androidx.compose.ui.geometry.Offset>>(emptyList()) }

        TabletBaseScreen(
            title = formattedTitle,
            topRightText = "1/3",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Instructions Text
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = instructions,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Colors.primaryColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }

                    // Drawing Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.8f)
                            .border(2.dp, Colors.primaryColor, RoundedCornerShape(8.dp))
                            .background(Color.White)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            currentPathPoints = listOf(offset)
                                            currentPath = Path().apply {
                                                moveTo(offset.x, offset.y)
                                            }
                                        },
                                        onDrag = { change, _ ->
                                            currentPathPoints = currentPathPoints + change.position
                                            currentPath = Path().apply {
                                                moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                                                currentPathPoints.forEach { point ->
                                                    lineTo(point.x, point.y)
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            currentPath?.let { paths.add(it) }
                                            currentPath = null
                                            currentPathPoints = emptyList()
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

                    Spacer(Modifier.height(16.dp))

                    // Buttons Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RoundedButton(
                            text = stringResource(Res.string.clear_all_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                // Clear all paths
                                paths.clear()
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        RoundedButton(
                            text = stringResource(Res.string.erase_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                // Remove the last path if it exists
                                if (paths.isNotEmpty()) {
                                    paths.removeLast()
                                }
                            }
                        )

                        Spacer(Modifier.width(8.dp))

                        RoundedButton(
                            text = stringResource(Res.string.finish_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = onFinishClick
                        )
                    }
                }
            }
        )
    }
}
