package org.example.hit.heal.evaluations.presentaion

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.body_back
import dmt_proms.composeapp.generated.resources.body_front
import org.example.hit.heal.app.BaseScreen
import org.jetbrains.compose.resources.painterResource

@Composable
fun HumanModelQuestion(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var isFrontView: Boolean by remember { mutableStateOf(true) }
    val bodyImage: Painter = painterResource(
        if (isFrontView) Res.drawable.body_front else Res.drawable.body_back
    )

    // Define the pain points (positions are relative to the image)
    val painPoints = listOf(
        Offset(0.5f, 0.05f),  // Head
        Offset(0.5f, 0.27f),  // Chest
        Offset(0.5f, 0.4f),   // Stomach
        Offset(0.31f, 0.55f), // Left hand
        Offset(0.69f, 0.55f), // Right hand
        Offset(0.45f, 0.72f), // Left Leg
        Offset(0.55f, 0.72f)  // Right Leg
    )

    var selectedPoints by remember { mutableStateOf(mutableSetOf<Offset>()) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }


    // Animate the rotation angle based on isFlipped state
    val rotationAngle by animateFloatAsState(
        targetValue = if (isFrontView) 180f else 0f
    )

    BaseScreen(
        title = "Evaluation",
        onPrevClick = onPrev,
        onNextClick = onNext
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Where do you feel the pain?",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(0.2f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(0.6f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .weight(0.8f)
                    .onGloballyPositioned { coordinates ->
                        parentSize = coordinates.size
                    }
            ) {
                Image(
                    painter = bodyImage,
                    contentDescription = "Human Body",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().graphicsLayer {
                        rotationY = rotationAngle
                    }
                )

                // Draw dots
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    for (point in painPoints) {
                        val x = point.x * width
                        val y = point.y * height
                        val isSelected = selectedPoints.contains(point)

                        drawCircle(
                            color = if (isSelected) Color(0xFF6FCF97) else Color.Gray,
                            radius = 30f,
                            center = Offset(x, y)
                        )
                    }
                }

                // Clickable areas
                painPoints.forEach { point ->
                    val x = point.x * parentSize.width
                    val y = point.y * parentSize.height

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(x.toInt() - 15, y.toInt() - 15) }
                            .size(40.dp)
                            .clickable {
                                selectedPoints = selectedPoints.toMutableSet().apply {
                                    if (contains(point)) remove(point) else add(point)
                                }
                            }
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            IconButton(
                onClick = {
                    println("Clicked flip body")
                    isFrontView = !isFrontView
                },
                modifier = Modifier.rotate(rotationAngle) // Apply rotation animation
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Flip image",
                    tint = primaryColor,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}