package org.example.hit.heal.hitber.presentation.dragAndDrop.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun DraggableCanvas(
    circleOffsets: SnapshotStateList<Offset>,
    circleColors: List<Color>,
    radiusPx: Float,
    onScreenSizeChanged: (Pair<Float, Float>) -> Unit,
    onTargetBoxRangeCalculated: (ClosedFloatingPointRange<Float>, ClosedFloatingPointRange<Float>) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(0.85f)
            .onSizeChanged { size ->
                onScreenSizeChanged(size.width.toFloat() to size.height.toFloat())
            }
            .background(color = Color.White, shape = RoundedCornerShape(4))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val draggedIndex = circleOffsets.indexOfFirst { offset ->
                        offset.distanceTo(change.position) < 50f
                    }

                    if (draggedIndex != -1) {
                        val currentOffset = circleOffsets[draggedIndex]
                        val newOffset = currentOffset + dragAmount
                        circleOffsets[draggedIndex] = newOffset
                    }
                }
            }
    ) {
        val rectSizePx = 150.dp.toPx()
        val left = (size.width - rectSizePx) / 2
        val top = (size.height - rectSizePx) / 2

        onTargetBoxRangeCalculated(left..(left + rectSizePx), top..(top + rectSizePx))

        drawRect(
            color = Color.Red,
            topLeft = Offset(left, top),
            size = Size(rectSizePx, rectSizePx),
            style = Stroke(width = 10.dp.toPx())
        )

        circleOffsets.forEachIndexed { index, offset ->
            drawCircle(
                color = circleColors.getOrElse(index) { Color.Gray },
                center = offset,
                radius = radiusPx
            )
        }
    }
}



private fun Offset.distanceTo(other: Offset): Float {
    return kotlin.math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
}


