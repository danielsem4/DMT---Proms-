package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.body_back
import dmt_proms.composeapp.generated.resources.body_front
import dmt_proms.composeapp.generated.resources.flip_button_label
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HumanBodyModelSelector(
    frontPoints: Set<Offset>,
    backPoints: Set<Offset>,
    isFrontView: Boolean,
    onSelectionChanged: (front: Set<Offset>, back: Set<Offset>) -> Unit,
    onToggleView: () -> Unit
) {
    val points = listOf(
        Offset(0.45f, 0.08f),  // Head
        Offset(0.45f, 0.26f),  // Chest
        Offset(0.45f, 0.4f),  // Belly
        Offset(0.095f, 0.51f),  // Left Palm
        Offset(0.78f, 0.51f),  // Right Palm
        Offset(0.30f, 0.68f),  // Left Knee
        Offset(0.6f, 0.68f)   // Right Knee
    )
    val selectedPoints = if (isFrontView) frontPoints else backPoints
    val imagePainter =
        painterResource(if (isFrontView) Res.drawable.body_front else Res.drawable.body_back)

    val aspectRatio = imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Keep image's original aspect ratio (based on actual image)
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .weight(.9f)
        ) {
            val containerWidth = constraints.maxWidth.toFloat()
            val containerHeight = constraints.maxHeight.toFloat()

            val imageWidth: Float
            val imageHeight: Float

            if (containerWidth / containerHeight > aspectRatio) {
                // Container is wider → fit by height
                imageHeight = containerHeight
                imageWidth = imageHeight * aspectRatio
            } else {
                // Container is taller → fit by width
                imageWidth = containerWidth
                imageHeight = imageWidth / aspectRatio
            }

            val offsetX = (containerWidth - imageWidth) / 2f
            val offsetY = (containerHeight - imageHeight) / 2f

            Image(
                painter = imagePainter,
                contentDescription = "Human",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            points.forEach { offset ->
                val x = offset.x * imageWidth + offsetX
                val y = offset.y * imageHeight + offsetY

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x.toInt(), y.toInt()) }
                        .size(28.dp)
                        .background(
                            color = if (selectedPoints.contains(offset)) primaryColor else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable {
                            if (isFrontView) {
                                val updated = frontPoints.toMutableSet().apply {
                                    if (contains(offset)) remove(offset) else add(offset)
                                }
                                onSelectionChanged(updated, backPoints)
                            } else {
                                val updated = backPoints.toMutableSet().apply {
                                    if (contains(offset)) remove(offset) else add(offset)
                                }
                                onSelectionChanged(frontPoints, updated)
                            }
                        }
                )
            }

            val flipIcon = Icons.Default.Refresh
            IconButton(
                onClick = onToggleView,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .size(flipIcon.defaultWidth)
                    .background(primaryColor, shape = CircleShape)
            ) {
                Icon(
                    imageVector = flipIcon,
                    contentDescription = stringResource(Res.string.flip_button_label),
                    tint = Color.White
                )
            }

        }
    }
}

@Preview
@Composable
fun Preview_HumanBodyModelSelector() {
    val frontPoints = remember { mutableStateOf(setOf<Offset>()) }
    val backPoints = remember { mutableStateOf(setOf<Offset>()) }
    val isFrontView = remember { mutableStateOf(true) }

    HumanBodyModelSelector(
        frontPoints = frontPoints.value,
        backPoints = backPoints.value,
        isFrontView = isFrontView.value,
        onSelectionChanged = { front, back ->
            frontPoints.value = front
            backPoints.value = back
        },
        onToggleView = {
            isFrontView.value = !isFrontView.value
        }
    )
}