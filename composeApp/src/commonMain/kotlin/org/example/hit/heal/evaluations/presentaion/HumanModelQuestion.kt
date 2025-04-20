package org.example.hit.heal.evaluations.presentaion

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
import org.example.hit.heal.core.presentation.Colors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
        Offset(0.24f, 0.45f),  // Left Palm
        Offset(0.7f, 0.45f),  // Right Palm
        Offset(0.38f, 0.68f),  // Left Knee
        Offset(0.5f, 0.68f)   // Right Knee
    )
    val selectedPoints = if (isFrontView) frontPoints else backPoints
    val imagePainter =
        painterResource(if (isFrontView) Res.drawable.body_front else Res.drawable.body_back)

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
            val imageWidth = constraints.maxWidth.toFloat()
            val imageHeight = constraints.maxHeight.toFloat()

            Image(
                painter = imagePainter,
                contentDescription = "Human",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            points.forEach { offset ->
                val x = offset.x * imageWidth
                val y = offset.y * imageHeight

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x.toInt(), y.toInt()) }
                        .size(28.dp)
                        .background(
                            color = if (selectedPoints.contains(offset)) Colors.primaryColor else Color.LightGray,
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
                    .background(Colors.primaryColor, shape = CircleShape)
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
