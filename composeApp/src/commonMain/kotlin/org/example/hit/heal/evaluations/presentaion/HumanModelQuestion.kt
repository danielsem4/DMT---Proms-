package org.example.hit.heal.evaluations.presentaion

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dmt_proms.composeapp.generated.resources.body_back_removed
import dmt_proms.composeapp.generated.resources.body_front_removed
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.Colors
import org.jetbrains.compose.resources.painterResource

@Composable
fun HumanModelQuestion(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    // State to track the current view (front or back)
    var isFrontView by rememberSaveable { mutableStateOf(true) }

    // Separate states for selected points on front and back views
    var selectedFrontPoints by rememberSaveable { mutableStateOf(setOf<Offset>()) }
    var selectedBackPoints by rememberSaveable { mutableStateOf(setOf<Offset>()) }

    // Determine the current image and selected points based on the view
    val bodyImage: Painter = painterResource(
        if (isFrontView) Res.drawable.body_front_removed else Res.drawable.body_back_removed
    )
    val selectedPoints = if (isFrontView) selectedFrontPoints else selectedBackPoints

    // Define the pain points (positions are relative to the image)
    val painPoints = listOf(
        Offset(0.5f, 0.05f),  // Head
        Offset(0.5f, 0.27f),  // Chest
        Offset(0.5f, 0.4f),   // Stomach
        Offset(0.3f, 0.55f), // Left hand
        Offset(0.7f, 0.55f), // Right hand
        Offset(0.45f, 0.72f), // Left Leg
        Offset(0.55f, 0.72f)  // Right Leg
    )

    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    // Animate the rotation angle based on isFrontView state
    val rotationAngle by animateFloatAsState(
        targetValue = if (isFrontView) 0f else 180f
    )

    BaseScreen(
        title = "Evaluation",
        onPrevClick = onPrev,
        onNextClick = onNext
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Where do you feel the pain?",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(0.6f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .onGloballyPositioned { coordinates ->
                        parentSize = coordinates.size
                    }
            ) {
                Image(
                    painter = bodyImage,
                    contentDescription = "Human Body",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = rotationAngle }
                )

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    painPoints.forEach { point ->
                        val x = point.x * width
                        val y = point.y * height
                        val isSelected = selectedPoints.contains(point)

                        drawCircle(
                            color = if (isSelected) Colors.primaryColor else Color.Gray,
                            radius = 30f,
                            center = Offset(x, y)
                        )
                    }
                }

                painPoints.forEach { point ->
                    val x = point.x * parentSize.width
                    val y = point.y * parentSize.height

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(x.toInt() - 20, y.toInt() - 20) }
                            .size(40.dp)
                            .clickable {
                                val updatedPoints = selectedPoints.toMutableSet().apply {
                                    if (contains(point)) remove(point) else add(point)
                                }
                                if (isFrontView) {
                                    selectedFrontPoints = updatedPoints
                                } else {
                                    selectedBackPoints = updatedPoints
                                }
                            }
                    )
                }
            }

            IconButton(
                onClick = {
                    isFrontView = !isFrontView
                    // Reset selection if switching to a view with no selections
                    if (isFrontView && selectedFrontPoints.isEmpty()) {
                        selectedFrontPoints = setOf()
                    } else if (!isFrontView && selectedBackPoints.isEmpty()) {
                        selectedBackPoints = setOf()
                    }
                }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Flip image",
                        tint = Color(0xFF6FCF97),
                        modifier = Modifier.size(50.dp)
                    )
                    Text("Flip", fontSize = 20.sp)
                }
            }
        }
    }
}