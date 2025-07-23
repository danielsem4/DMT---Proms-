package org.example.hit.heal.presentation.components.evaluation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import core.data.model.evaluation.EvaluationValue
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.body_back
import dmt_proms.composeapp.generated.resources.body_front
import dmt_proms.composeapp.generated.resources.flip_button_label
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes
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
    onToggleView: () -> Unit,
    availableValues: List<EvaluationValue>
) {
    val alertMessage = remember { mutableStateOf<String?>(null) }

    val frontPointsWithValues = listOf(
        Offset(0.45f, 0.08f) to availableValues[0],  // Head
        Offset(0.45f, 0.26f) to availableValues[1],  // Chest
        Offset(0.45f, 0.4f) to availableValues[4],  // Belly
        Offset(0.78f, 0.51f) to availableValues[3]   // Right Arm
    )

    val backPointsWithValues = listOf(
        Offset(0.45f, 0.4f) to availableValues[2]   // Lower Back
    )

    val currentPointsWithValues = if (isFrontView) frontPointsWithValues else backPointsWithValues
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .aspectRatio(0.5f)
        ) {
            val containerWidth = constraints.maxWidth.toFloat()
            val containerHeight = constraints.maxHeight.toFloat()

            val imageWidth: Float
            val imageHeight: Float

            if (containerWidth / containerHeight > aspectRatio) {
                imageHeight = containerHeight
                imageWidth = imageHeight * aspectRatio
            } else {
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

            currentPointsWithValues.forEach { (offset, evalValue) ->
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
                            val wasSelected = selectedPoints.contains(offset)

                            if (!wasSelected) {
                                alertMessage.value = evalValue.available_value
                            }

                            if (isFrontView) {
                                val updated = frontPoints.toMutableSet().apply {
                                    if (wasSelected) remove(offset) else add(offset)
                                }
                                onSelectionChanged(updated, backPoints)
                            } else {
                                val updated = backPoints.toMutableSet().apply {
                                    if (wasSelected) remove(offset) else add(offset)
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

    alertMessage.value?.let { selectedText ->
        AlertDialog(
            onDismissRequest = { alertMessage.value = null },
            title = {
                Text(
                    text = stringResource(Resources.String.selected_point),
                    style = MaterialTheme.typography.titleMedium,
                    color = primaryColor
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = selectedText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { alertMessage.value = null },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                    modifier = Modifier.padding(Sizes.paddingSm),
                    shape = RoundedCornerShape(Sizes.radiusLg)
                ) {
                    Text(
                        text = stringResource(Resources.String.`continue`),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            shape = RoundedCornerShape(Sizes.radiusLg),
            containerColor = Color.White,
            textContentColor = primaryColor
        )
    }
}

@Composable
@Preview
fun HumanBodyModelSelectorPreview() {
    val frontPoints = remember { mutableStateOf(setOf<Offset>()) }
    val backPoints = remember { mutableStateOf(setOf<Offset>()) }
    val isFrontView = remember { mutableStateOf(true) }

    val mockValues = listOf(
        EvaluationValue(473, "Head (?) ראש, עיניים, לסת", false, "", 256),
        EvaluationValue(474, "Chest (?) בית החזה, הצלעות", false, "", 256),
        EvaluationValue(475, "Lower Back (?) גב תחתון, גב ח", false, "", 256),
        EvaluationValue(476, "Right Arm (?) פרק כף יד, אצבעות, גב יד", false, "", 256),
        EvaluationValue(477, "Belly (?) בטן, קיבה, כבד", false, "", 256)
    )

    HumanBodyModelSelector(
        frontPoints = frontPoints.value,
        backPoints = backPoints.value,
        isFrontView = isFrontView.value,
        onSelectionChanged = { f, b ->
            frontPoints.value = f
            backPoints.value = b
        },
        onToggleView = {
            isFrontView.value = !isFrontView.value
        },
        availableValues = mockValues
    )
}
