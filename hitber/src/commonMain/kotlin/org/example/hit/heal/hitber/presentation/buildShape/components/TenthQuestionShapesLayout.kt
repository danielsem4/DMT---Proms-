package org.example.hit.heal.hitber.presentation.buildShape.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Density
import org.example.hit.heal.core.presentation.Sizes.radiusSm
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes

// Composable layout that displays draggable and static shapes within a styled container.
@Composable
fun TenthQuestionShapesLayout(
    staticShapesItem: List<BuildShapes>,
    draggableShapesItem: List<BuildShapes>,
    itemPositions: MutableList<Offset>,
    triangleWidth: Float,
    triangleHeight: Float,
    density: Density,
    onScreenSizeChanged: (Pair<Float, Float>) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenSize = remember { mutableStateOf(0f to 0f) }

    Box(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(radiusSm))
            .onSizeChanged { size ->
                val sizePair = size.width.toFloat() to size.height.toFloat()
                screenSize.value = sizePair
                onScreenSizeChanged(sizePair)
            }
    )  {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth(0.7f)
                .fillMaxHeight()
                .background(color = Color(0xFFB2FFFF), shape = RoundedCornerShape(radiusSm))
        )

        TopRowLabels()

        StaticShapesArea(
            screenSize = screenSize.value,
            triangleWidth = triangleWidth,
            triangleHeight = triangleHeight,
            staticShapesItem = staticShapesItem,
            density = density
        )

        DraggableShapesArea(
            triangleHeight = triangleHeight,
            draggableShapesItem = draggableShapesItem,
            itemPositions = itemPositions,
            density = density
        )
    }
}
