package org.example.hit.heal.oriantation.feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.bleach
import dmt_proms.oriantation.generated.resources.check
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.hash_tag
import dmt_proms.oriantation.generated.resources.rhomb_outline
import dmt_proms.oriantation.generated.resources.star
import org.jetbrains.compose.resources.painterResource


data class DraggableShape(
    val id: Int,
    val drawableRes: Any, // Use the correct type for your painterResource
    var offset: Offset = Offset.Zero,
    var isDropped: Boolean = false
)

@Composable
fun ShapesDragScreen() {
    val redSquareSize = 300.dp
    val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

    // List of all shapes (add your PNGs here)
    val shapeImages = listOf(
        Res.drawable.bleach,         // triangle
        Res.drawable.rhomb_outline,  // diamond
        Res.drawable.star,           // star
        Res.drawable.hash_tag,       // hash
        Res.drawable.close,          // X
        Res.drawable.check           // check
    )

    var shapes by remember {
        mutableStateOf(
            shapeImages.mapIndexed { idx, res ->
                DraggableShape(id = idx, drawableRes = res)
            }
        )
    }
    var droppedShapeId by remember { mutableStateOf<Int?>(null) }

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
            // Show the dropped shape in the center
            droppedShapeId?.let { id ->
                val shape = shapes.firstOrNull { it.id == id }
                shape?.let {
                    Image(
                        painter = painterResource(it.drawableRes),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // Draggable shapes column
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 32.dp)
        ) {
            shapes.forEach { shape ->
                if (!shape.isDropped) {
                    DraggableShapeIcon(
                        drawableRes = shape.drawableRes,
                        offset = shape.offset,
                        onOffsetChange = { newOffset ->
                            shapes = shapes.map {
                                if (it.id == shape.id) it.copy(offset = newOffset) else it
                            }
                        },
                        onDrop = { offset ->
                            val dropX = offset.x
                            val dropY = offset.y
                            if (dropX in 0f..redSquarePx && dropY in 0f..redSquarePx) {
                                droppedShapeId = shape.id
                                shapes = shapes.map {
                                    if (it.id == shape.id) it.copy(isDropped = true) else it
                                }
                            } else {
                                shapes = shapes.map {
                                    if (it.id == shape.id) it.copy(offset = Offset.Zero) else it
                                }
                            }
                        }
                    )
                } else {
                    // Show faded icon if dropped
                    Image(
                        painter = painterResource(shape.drawableRes),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        alpha = 0.3f
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableShapeIcon(
    drawableRes: Any, // Use the correct type for your painterResource
    offset: Offset,
    onOffsetChange: (Offset) -> Unit,
    onDrop: (Offset) -> Unit
) {
    Box(
        modifier = Modifier
            .offset { androidx.compose.ui.unit.IntOffset(offset.x.toInt(), offset.y.toInt()) }
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
            painter = painterResource(drawableRes),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
    }
}