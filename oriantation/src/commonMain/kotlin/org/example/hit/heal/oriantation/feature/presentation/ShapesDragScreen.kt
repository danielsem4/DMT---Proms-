package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.example.hit.heal.oriantation.feature.presentation.components.DraggableShape
import org.example.hit.heal.oriantation.feature.presentation.components.DraggableShapeIcon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ShapesDragScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        // Initial positions for shapes in a column on the right
        val initialOffsets = remember {
            listOf(
                Offset(1500f, 200f),  // triangle
                Offset(1500f, 500f),  // diamond
                Offset(1500f, 800f),  // star
                Offset(2000f, 200f),  // hash
                Offset(2000f, 500f),  // X
                Offset(2000f, 800f)   // check
            )
        }

        // Drag state for all shapes
        var shapes by remember {
            mutableStateOf(
                listOf(
                    DraggableShape(0, Res.drawable.bleach, initialOffsets[0]),         // triangle
                    DraggableShape(1, Res.drawable.rhomb_outline, initialOffsets[1]),  // diamond
                    DraggableShape(2, Res.drawable.star, initialOffsets[2]),           // star
                    DraggableShape(3, Res.drawable.hash_tag, initialOffsets[3]),       // hash
                    DraggableShape(4, Res.drawable.close, initialOffsets[4]),          // X
                    DraggableShape(5, Res.drawable.check, initialOffsets[5])           // check
                )
            )
        }

        // Red square position and size
        val redSquareSize = 500.dp
        val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

        TabletBaseScreen(

            title = (stringResource(trialDragTitle)),
            question = 4,
            onNextClick = { navigator?.push(ShapeResizeScreen(viewModel)) },
            content = {
                Spacer(modifier = Modifier.height(16.dp))
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
                            // Show shapes that were dropped in the red square
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
                                        // Check if dropped inside the red square
                                        if (dropX in 0f..redSquarePx && dropY in 0f..redSquarePx) {
//                                        shapes = shapes.map{
//                                            if (it.id == shape.id) it.copy(isDroppedInSquare = true) else it
//                                        }
                                            // If the dropped shape is the triangle (id = 0), update the viewModel
                                            if (shape.id == 0) {
                                                viewModel.updateShapesDrag(true)
                                                println("triangle dropped")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
        RegisterBackHandler(this)
        {
            navigator?.popUntilRoot()
        }
    }
}
