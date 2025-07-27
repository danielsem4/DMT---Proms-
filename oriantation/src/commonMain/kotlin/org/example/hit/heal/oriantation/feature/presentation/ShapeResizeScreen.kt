package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.trialPinchInstructions
import org.example.hit.heal.core.presentation.Resources.String.trialPinchTitle
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

data class Item(
    val id: Int,
    val resId: DrawableResource,
    var isPlaced: Boolean = false,
    var position: Offset = Offset.Zero
)

class ShapeResizeScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var scale by remember { mutableStateOf(1f) }
        // Use the triangleOffset from the viewModel, or default to Offset(100f, 100f)
        var triangleOffset by remember { mutableStateOf(viewModel.triangleOffset ?: Offset(100f, 100f)) }
        
        // Debug information
        LaunchedEffect(scale) {
            println("Scale changed to: $scale")
        }
        
        // Add a debug text to show current scale
        Text(
            text = "Current scale: $scale",
            modifier = Modifier.padding(8.dp)
        )

        // Red square size
        val redSquareSize = 500.dp
        val redSquarePx = with(LocalDensity.current) { redSquareSize.toPx() }

        TabletBaseScreen(
            title = stringResource(trialPinchTitle),
            question = 5,
            onNextClick = {
                if (scale > 1f) {
                    viewModel.updateShapeResize(true)}
                navigator?.push(DrawScreen(viewModel)) },
            content = {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(trialPinchInstructions),
                    color = Color(0xFF4EC3AF),
                    textAlign = TextAlign.Center,
                    fontSize = LARGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Red square with gesture detection
                    Box(
                        modifier = Modifier
                            .size(redSquareSize)
                            .border(3.dp, Color.Red)
                            .background(backgroundColor)
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        // Triangle with pinch and drag
                        Box(
                            modifier = Modifier
                                .offset { IntOffset(triangleOffset.x.toInt(), triangleOffset.y.toInt()) }
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        scale = (scale * zoom).coerceIn(0.1f, 5f)
                                        triangleOffset += pan
                                    }
                                }
                                .size((150 * scale).dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                val path = Path().apply {
                                    moveTo(width / 2, 0f)
                                    lineTo(0f, height)
                                    lineTo(width, height)
                                    close()
                                }
                                drawPath(
                                    path = path,
                                    color = Color.Black,
                                    style = Stroke(width = 10f)
                                )
                            }
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