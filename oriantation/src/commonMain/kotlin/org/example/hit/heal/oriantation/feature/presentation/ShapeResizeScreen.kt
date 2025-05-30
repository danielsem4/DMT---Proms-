package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

data class Item(
    val id: Int,
    val resId: DrawableResource,
    var isPlaced: Boolean = false,
    var position: Offset = Offset.Zero
)

class ShapeResizeScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        // State for triangle scale
        var scale by remember { mutableStateOf(1f) }

        // Red square size
        val redSquareSize = 400.dp

        TabletBaseScreen(
            title = "שינוי גודל",
            question = 5,
            onNextClick = { /* TODO: Navigate to next screen */ },
            content = {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "כעת, עליך להגדיל את המשולש באמצעות הרחקה של 2 אצבעות על הצורה",
                    color = Color(0xFF4EC3AF),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Red square (drop target)
                    Box(
                        modifier = Modifier
                            .size(redSquareSize)
                            .border(3.dp, Color.Red)
                            .background(Color(0xFFE0F7F1))
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Triangle with pinch-to-zoom
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, _, zoom, _ ->
                                        scale = (scale * zoom).coerceIn(0.5f, 3f)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize(0.6f)
                            ) {
                                val width = size.width * scale
                                val height = size.height * scale
                                val centerX = size.width / 2
                                val centerY = size.height / 2
                                val path = Path().apply {
                                    moveTo(centerX, centerY - height / 3)
                                    lineTo(centerX - width / 2.5f, centerY + height / 3)
                                    lineTo(centerX + width / 2.5f, centerY + height / 3)
                                    close()
                                }
                                drawPath(
                                    path = path,
                                    color = Color.Black,
                                    style = Stroke(width = 10f)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}