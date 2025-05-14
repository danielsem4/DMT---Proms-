package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.hit.heal.core.presentation.TabletBaseScreen
import androidx.compose.foundation.Image
import org.jetbrains.compose.resources.painterResource
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.bleach
import dmt_proms.oriantation.generated.resources.check
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.hash_tag
import dmt_proms.oriantation.generated.resources.rhomb_outline
import dmt_proms.oriantation.generated.resources.star



class ShapesDragScreen : Screen {
    @Composable
    override fun Content() {
        // Drag state for triangle
        var triangleOffset by remember { mutableStateOf(Offset.Zero) }
        var isTriangleDropped by remember { mutableStateOf(false) }

        // Red square position and size
        val redSquareSize = 300.dp
        val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

        // List of other shapes (diamond, star, hash, X, check)
        val shapeImages = listOf(
            Res.drawable.rhomb_outline,
            Res.drawable.star,
            Res.drawable.hash_tag,
            Res.drawable.close,
            Res.drawable.check
        )

        TabletBaseScreen(
            title = "גרירה",
            question = 4,
            onNextClick = { /* TODO: Navigate to next screen */ },
            content = {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "בפניך מספר צורות, עליך לגרור את המשולש לתוך הריבוע האדום משמאל",
                    color = Color(0xFF4EC3AF),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Red square (drop target)
                    Box(
                        modifier = Modifier
                            .size(redSquareSize)
                            .border(3.dp, Color.Red)
                            .background(Color(0xFFE0F7F1)),
                        contentAlignment = Alignment.Center
                    ) {
                        // If triangle is dropped, show it inside
                        if (isTriangleDropped) {
                            Image(
                                painter = painterResource(Res.drawable.bleach),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Draggable triangle (if not dropped)
                    if (!isTriangleDropped) {
                        DraggableTriangleIcon(
                            onDrop = { offset ->
                                // Check if dropped inside the red square
                                val dropX = offset.x
                                val dropY = offset.y
                                if (dropX in 0f..redSquarePx && dropY in 0f..redSquarePx) {
                                    isTriangleDropped = true
                                } else {
                                    triangleOffset = Offset.Zero
                                }
                            },
                            offset = triangleOffset,
                            onOffsetChange = { triangleOffset = it }
                        )
                    }
                    // Other shapes (not draggable, just displayed)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 32.dp)
                    ) {
                        shapeImages.forEach { res ->
                            Image(
                                painter = painterResource(res),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        // Add the triangle at the bottom (draggable)
                        if (isTriangleDropped) {
                            // Show a faded triangle to indicate it's been placed
                            Image(
                                painter = painterResource(Res.drawable.bleach),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                alpha = 0.3f
                            )
                        } else {
                            Image(
                                painter = painterResource(Res.drawable.bleach),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}

// Draggable triangle using the drawable icon
@Composable
fun DraggableTriangleIcon(
    onDrop: (Offset) -> Unit,
    offset: Offset,
    onOffsetChange: (Offset) -> Unit
) {
    Box(
        modifier = Modifier
            .offset { offset.toIntOffset() }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = { onDrop(offset) }
                ) { change, dragAmount ->
                    change.consume()
                    onOffsetChange(offset + Offset(dragAmount.x, dragAmount.y))
                }
            }
    ) {
        Image(
            painter = painterResource(Res.drawable.bleach),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
    }
}

private fun Offset.toIntOffset() = androidx.compose.ui.unit.IntOffset(x.toInt(), y.toInt())