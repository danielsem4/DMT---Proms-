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
import dmt_proms.oriantation.generated.resources.check
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.hash_tag
import dmt_proms.oriantation.generated.resources.rhomb_outline
import dmt_proms.oriantation.generated.resources.star

@Composable
private fun TriangleShape(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path, color)
    }
}

class ShapesDragScreen : Screen {
    @Composable
    override fun Content() {
        // Drag state for triangle
        var triangleOffset by remember { mutableStateOf(Offset.Zero) }
        var isTriangleDropped by remember { mutableStateOf(false) }

        // Red square position and size
        val redSquareSize = 300.dp
        val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

        // List of other shapes (star, check, close, hash-tag, rhomb-outline)
        val shapeImages = listOf(
            Res.drawable.star,
            Res.drawable.check,
            Res.drawable.close,
            Res.drawable.hash_tag,
            Res.drawable.rhomb_outline
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
                            TriangleShape(
                                modifier = Modifier.size(80.dp),
                                color = Color(0xFF4EC3AF)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Draggable triangle (if not dropped)
                    if (!isTriangleDropped) {
                        Box(
                            modifier = Modifier
                                .offset { triangleOffset.toIntOffset() }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        triangleOffset += Offset(dragAmount.x, dragAmount.y)
                                    }
                                }
                        ) {
                            TriangleShape(
                                modifier = Modifier.size(80.dp),
                                color = Color.Black
                            )
                        }
                    }
                    // Other shapes (not draggable for now, just displayed)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        shapeImages.forEach { res ->
                            Image(
                                painter = painterResource(res),
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

private fun Offset.toIntOffset() = androidx.compose.ui.unit.IntOffset(x.toInt(), y.toInt())