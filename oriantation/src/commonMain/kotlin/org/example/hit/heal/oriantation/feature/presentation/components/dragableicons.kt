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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.bleach
import dmt_proms.oriantation.generated.resources.check
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.hash_tag
import dmt_proms.oriantation.generated.resources.rhomb_outline
import dmt_proms.oriantation.generated.resources.star
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class DraggableShape(
    val id: Int,
    val drawableRes: DrawableResource,
    var offset: Offset = Offset.Zero,
    var isDroppedInSquare: Boolean = false
)

@Composable
fun DraggableShapeIcon(
//    icons: List<DraggableShape>,
    drawableRes: DrawableResource,
    offset: Offset,
    onOffsetChange: (Offset) -> Unit,
    onDrop: (Offset) -> Unit
) {
    var currentOffset by remember { mutableStateOf(offset) }
    
    Box(
        modifier = Modifier
            .offset { IntOffset(currentOffset.x.toInt(), currentOffset.y.toInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { /* Optional: handle drag start */ },
                    onDragEnd = { onDrop(currentOffset) }
                ) { change, dragAmount ->
                    change.consume()
                    currentOffset = currentOffset + Offset(dragAmount.x, dragAmount.y)
                    onOffsetChange(currentOffset)
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