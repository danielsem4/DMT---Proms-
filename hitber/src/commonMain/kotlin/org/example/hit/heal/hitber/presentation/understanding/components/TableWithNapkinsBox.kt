package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Density
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.Resources.Icon.table
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberTable
import org.example.hit.heal.hitber.presentation.understanding.model.napkins
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TableWithNapkinsBox(
    tableSize: Pair<Float, Float>,
    onTableSizeChanged: (Pair<Float, Float>) -> Unit,
    napkinWidthPx: Float,
    napkinHeightPx: Float,
    napkinResourceId: Color?,
    onNapkinPositionCalculated: (Offset) -> Unit,
    density: Density,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth(0.4f)
            .zIndex(-1f)
            .onSizeChanged { size ->
                onTableSizeChanged(size.width.toFloat() to size.height.toFloat())
            }
    ) {
        // Draw table image
        Image(
            painter = painterResource(table),
            contentDescription = stringResource(sixthQuestionHitberTable),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        // Draw napkins positioned based on predefined ratios relative to table size
        napkins.forEachIndexed { _, item ->

            // Convert pixel sizes and positions to dp units for Compose layout

            val xPx = tableSize.first * item.xRatio
            val yPx = tableSize.second * item.yRatio

            val napkinWidthDp = with(density) { napkinWidthPx.toDp() }
            val napkinHeightDp = with(density) { napkinHeightPx.toDp() }
            val xDp = with(density) { xPx.toDp() }
            val yDp = with(density) { yPx.toDp() }

            Box(
                modifier = Modifier
                    .offset(x = xDp, y = yDp)
                    .onGloballyPositioned { coordinates ->
                        if (napkinResourceId == item.tint) {
                            onNapkinPositionCalculated(coordinates.positionInRoot())
                        }
                    }
                    .size(napkinWidthDp, napkinHeightDp)
                    .zIndex(1f)
            ) {
                // Draw napkin icon tinted with its specific color
                Icon(
                    painter = painterResource(item.image),
                    contentDescription = null,
                    tint = item.tint,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }
}
