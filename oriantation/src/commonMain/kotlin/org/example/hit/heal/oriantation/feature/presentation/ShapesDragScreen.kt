package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.bleach
import dmt_proms.oriantation.generated.resources.check
import dmt_proms.oriantation.generated.resources.close
import dmt_proms.oriantation.generated.resources.hash_tag
import dmt_proms.oriantation.generated.resources.rhomb_outline
import dmt_proms.oriantation.generated.resources.star
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.trialDragInstructions
import org.example.hit.heal.core.presentation.Resources.String.trialDragTitle
import org.example.hit.heal.core.presentation.Resources.String.trialPinchTitle
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.example.hit.heal.oriantation.feature.presentation.components.DraggableShape
import org.example.hit.heal.oriantation.feature.presentation.components.DraggableShapeIcon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

/**
 * Single screen with two phases:
 *  - Phase.Drag   : drag one of the shapes into the red square.
 *  - Phase.Resize : show ONLY a triangle; user pinch-zooms it. We record if resized.
 */

class ShapesDragScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {

    private enum class Phase { Drag, Resize }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var phase by remember { mutableStateOf(Phase.Drag) }

        var scale by remember { mutableStateOf(1f) }
        var resized by remember { mutableStateOf(false) }
        val transformState = rememberTransformableState { zoomChange, _, _ ->
            val newScale = (scale * zoomChange).coerceIn(0.5f, 3f)
            if (!resized && abs(newScale - 1f) > 0.02f) resized = true
            scale = newScale
        }

        val initialOffsets = remember {
            listOf(
                Offset(1500f, 200f),
                Offset(1500f, 500f),
                Offset(1500f, 800f),
                Offset(2000f, 200f),
                Offset(2000f, 500f),
                Offset(2000f, 800f)
            )
        }

        var shapes by remember {
            mutableStateOf(
                listOf(
                    DraggableShape(0, Res.drawable.bleach, initialOffsets[0]),
                    DraggableShape(1, Res.drawable.rhomb_outline, initialOffsets[1]),
                    DraggableShape(2, Res.drawable.star, initialOffsets[2]),
                    DraggableShape(3, Res.drawable.hash_tag, initialOffsets[3]),
                    DraggableShape(4, Res.drawable.close, initialOffsets[4]),
                    DraggableShape(5, Res.drawable.check, initialOffsets[5])
                )
            )
        }

        val redSquareSize = 500.dp
        val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

        TabletBaseScreen(
            title = if (phase == Phase.Drag) stringResource(trialDragTitle) else "Resize the Shape",
            question = if (phase == Phase.Drag) 4 else 5,
            onNextClick = {
                if (phase == Phase.Drag) {
                    phase = Phase.Resize
                    scale = 1f
                    resized = false
                } else {
                    viewModel.updateShapeResize(resized)
                    navigator.push(DrawScreen(viewModel))
                }
            },
            content = {
                Spacer(modifier = Modifier.height(16.dp))

                if (phase == Phase.Drag) {
                    // ------------------ PHASE 1: DRAG ------------------
                    Text(
                        text = stringResource(trialDragInstructions),
                        color = Color(0xFF4EC3AF),
                        textAlign = TextAlign.Center,
                        fontSize = LARGE,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    )
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Red square (drop target)
                            Box(
                                modifier = Modifier
                                    .size(redSquareSize)
                                    .border(3.dp, Color.Red)
                                    .background(backgroundColor)
                                    .align(Alignment.CenterStart)
                                    .padding(start = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // If you want to show any shape left inside after dropping (optional)
                                shapes.filter { it.isDroppedInSquare }.forEach { shape ->
                                    Image(
                                        painter = painterResource(shape.drawableRes),
                                        contentDescription = null,
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                            }

                            // Draggable shapes
                            shapes.forEach { shape ->
                                if (!shape.isDroppedInSquare) {
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
                                                viewModel.setDroppedShapeId(shape.id)

                                                if (shape.id == 0) {
                                                    viewModel.updateShapesDrag(true)
                                                    viewModel.setTriangleOffset(Offset(dropX, dropY))
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = stringResource(trialPinchTitle),
                        color = primaryColor,
                        textAlign = TextAlign.Center,
                        fontSize = LARGE,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.bleach),
                            contentDescription = "Triangle",
                            modifier = Modifier
                                .size(220.dp)
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale
                                )
                                .transformable(transformState)
                        )
                    }
                }
            }
        )

        RegisterBackHandler(this) {
            navigator.popUntilRoot()
        }
    }
}
