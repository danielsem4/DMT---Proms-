package org.example.hit.heal.cdt.presentation

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.domain.use_case.DrawPathsToBitmapUseCase
import dmt_proms.clock_test.generated.resources.Res
import dmt_proms.clock_test.generated.resources.clear_all_button_text
import dmt_proms.clock_test.generated.resources.clear_all_dialog_message
import dmt_proms.clock_test.generated.resources.clear_all_dialog_title
import dmt_proms.clock_test.generated.resources.draw_instruction
import dmt_proms.clock_test.generated.resources.draw_mode
import dmt_proms.clock_test.generated.resources.draw_screen_title
import dmt_proms.clock_test.generated.resources.drawing_instruction
import dmt_proms.clock_test.generated.resources.erase_mode
import dmt_proms.clock_test.generated.resources.finish_button_text
import org.example.hit.heal.cdt.data.ClockTime
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class DrawClockScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ClockTestViewModel>()

        viewModel.startDrawingTimer()
        viewModel.updateFirstTime(ClockTime(12, 0))

        val currentTime = viewModel.getCurrentClockSetTime().value
        val formattedTitle = stringResource(Res.string.draw_screen_title, currentTime.toString())
        val instructions = stringResource(Res.string.draw_instruction, currentTime.toString())

        val paths = remember { mutableStateListOf<Path>() }
        var currentPath by remember { mutableStateOf<Path?>(null) }
        var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
        var isEraseMode by remember { mutableStateOf(false) }
        var isDrawing by remember { mutableStateOf(false) }
        var canvasSize by remember { mutableStateOf(Size.Zero) }
        var isButtonEnabled by remember { mutableStateOf(true) }
        var showClearAllDialog by remember { mutableStateOf(false) } // State for dialog visibility

        fun updatePathsInViewModel() {
            viewModel.updateDrawnPaths(paths.toList())
        }

        TabletBaseScreen(
            title = formattedTitle,
            topRightText = "1/3",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize() // Use fillMaxSize for the Column to take all available space
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.08f), // This box takes 8% of the remaining height
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = instructions,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Canvas Box takes the majority of the remaining space
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Assign weight to this Box to make it fill available space
                            .border(2.dp, primaryColor, RoundedCornerShape(8.dp))
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
                                                paths.removeAll {
                                                    it.getBounds().contains(touchPoint)
                                                }
                                            } else {
                                                currentPathPoints =
                                                    currentPathPoints + change.position
                                                currentPath = Path().apply {
                                                    moveTo(
                                                        currentPathPoints.first().x,
                                                        currentPathPoints.first().y
                                                    )
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
                                    color = primaryColor,
                                    style = Stroke(width = 4.dp.toPx())
                                )
                            }
                            currentPath?.let { path ->
                                drawPath(
                                    path = path,
                                    color = primaryColor,
                                    style = Stroke(width = 4.dp.toPx())
                                )
                            }
                        }

                        if (!isDrawing) {
                            Text(
                                text = stringResource(Res.string.drawing_instruction),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), // Removed .height(50.dp) and fillMaxHeight
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RoundedButton(
                            text = stringResource(Res.string.clear_all_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                showClearAllDialog = true // Show dialog instead of clearing directly
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
                            icon = if (isEraseMode) Resources.Icon.pencilIcon else Resources.Icon.binIcon
                        )

                        Spacer(Modifier.width(8.dp))

                        val density = LocalDensity.current

                        RoundedButton(
                            text = stringResource(Res.string.finish_button_text),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (!isButtonEnabled) return@RoundedButton
                                isButtonEnabled = false
                                val image = DrawPathsToBitmapUseCase.drawPathsToBitmap(
                                    canvasSize,
                                    paths,
                                    density
                                )


                                viewModel.saveBitmap(image)
                                viewModel.stopDrawingTimer()
                                navigator.replace(InfoScreen())
                            },
                            enabled = isButtonEnabled
                        )
                    }
                }
            }
        )

        // Clear All Confirmation Dialog
        if (showClearAllDialog) {
            BaseYesNoDialog(
                onDismissRequest = { showClearAllDialog = false },
                title = stringResource(Res.string.clear_all_dialog_title),
//                icon = Icons.Default.Warning, // Using a default warning icon
                message = stringResource(Res.string.clear_all_dialog_message),
                onConfirm = {
                    paths.clear()
                    viewModel.clearDrawnPaths()
                    showClearAllDialog = false
                },
                onDismissButtonClick = { showClearAllDialog = false }
            )
        }
    }
}