package org.example.hit.heal.hitber.presentation.buildShape.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Density
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberShapeImage
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Displays draggable shapes based on triangle size, updating their positions on drag

@Composable
fun DraggableShapesArea(
    triangleHeight: Float,
    draggableShapesItem: List<BuildShapes>,
    itemPositions: MutableList<Offset>,
    density: Density
) {
    draggableShapesItem.forEachIndexed { index, shape ->
        val itemWidthPx = triangleHeight * shape.width
        val itemHeightPx = triangleHeight * shape.height
        val itemWidthDp = with(density) { itemWidthPx.toDp() }
        val itemHeightDp = with(density) { itemHeightPx.toDp() }
        val currentPosition = itemPositions[index]

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { currentPosition.x.toDp() },
                    y = with(density) { currentPosition.y.toDp() }
                )
                .size(itemWidthDp, itemHeightDp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        itemPositions[index] = itemPositions[index] + dragAmount
                    }
                }
        ) {
            Image(
                painter = painterResource(shape.image),
                contentDescription = stringResource(tenthQuestionHitberShapeImage),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
