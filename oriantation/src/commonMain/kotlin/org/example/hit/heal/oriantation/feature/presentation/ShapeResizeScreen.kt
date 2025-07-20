package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.trial_pinch_instructions
import dmt_proms.oriantation.generated.resources.trial_pinch_title
import kotlinx.serialization.json.JsonNull.content
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.trialPinchInstructions
import org.example.hit.heal.core.presentation.Resources.String.trialPinchTitle
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
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
                            .padding(start = 16.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    // Calculate zoom based on drag amount
                                    val zoomFactor = if (dragAmount.y < 0) 1.1f else 0.9f
                                    scale = (scale * zoomFactor).coerceIn(0.1f, 5f)
                                    println("New scale: $scale")
                                }
                            }
                    ) {
                        // Triangle
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val width = size.width * scale
                            val height = size.height * scale
                            println("Drawing with scale: $scale, width: $width, height: $height")
                            val centerX = size.width / 2
                            val centerY = size.height / 2
                            val path = Path().apply {
                                moveTo(centerX, centerY - height / 3)
                                lineTo(centerX - width / 2.5f, centerY + height / 3)
                                lineTo(centerX + width / 2.5f, centerY + height / 3)
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
        )
        RegisterBackHandler(this)
        {
            navigator?.pop()
        }
    }
}