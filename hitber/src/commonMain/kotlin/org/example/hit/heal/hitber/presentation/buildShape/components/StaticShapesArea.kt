package org.example.hit.heal.hitber.presentation.buildShape.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Density
import org.example.hit.heal.core.presentation.Resources.Icon.triangle
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberShapeImage
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StaticShapesArea(
    screenSize: Pair<Float, Float>,
    triangleWidth: Float,
    triangleHeight: Float,
    staticShapesItem: List<BuildShapes>,
    density: Density
) {
    Box(
        modifier = Modifier
            .width(with(density) { triangleWidth.toDp() })
            .height(with(density) { triangleHeight.toDp() })
            .offset(
                x = with(density) { (screenSize.first * staticShapesItem[0].xRatio).toDp() },
                y = with(density) { (screenSize.second * staticShapesItem[0].yRatio).toDp() }
            )
    ) {
        Image(
            painter = painterResource(triangle),
            contentDescription = stringResource(tenthQuestionHitberShapeImage),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        staticShapesItem.drop(1).forEach { shape ->
            val itemWidthPx = triangleHeight * shape.width
            val itemHeightPx = triangleHeight * shape.height
            val xPx = triangleWidth * shape.xRatio
            val yPx = triangleHeight * shape.yRatio

            Box(
                modifier = Modifier
                    .offset(
                        x = with(density) { xPx.toDp() },
                        y = with(density) { yPx.toDp() }
                    )
                    .size(
                        width = with(density) { itemWidthPx.toDp() },
                        height = with(density) { itemHeightPx.toDp() }
                    )
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
}
