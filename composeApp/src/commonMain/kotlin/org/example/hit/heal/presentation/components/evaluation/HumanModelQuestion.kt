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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Redo
import core.data.model.evaluation.EvaluationValue
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
    availableValues: List<EvaluationValue>,
    selectedValues: List<String>, // Overall selected granular parts for visual feedback
    onBodyPartClicked: (EvaluationValue) -> Unit // Callback when a body part is clicked
) {
    var isFrontView by remember { mutableStateOf(true) }

    // Map Offset to EvaluationValue for easy lookup
    val frontPointsMap = remember(availableValues) {
        mapOf(
            Offset(0.45f, 0.08f) to availableValues.getOrNull(0),  // Head
            Offset(0.45f, 0.26f) to availableValues.getOrNull(1),  // Chest
            Offset(0.45f, 0.4f) to availableValues.getOrNull(4),   // Belly
            Offset(0.78f, 0.51f) to availableValues.getOrNull(3)    // Right Arm
        ).filterValues { it != null }
            .mapValues { it.value!! } // Filter out null EvaluationValue objects and assert non-null
    }

    val backPointsMap = remember(availableValues) {
        mapOf(
            Offset(0.45f, 0.4f) to availableValues.getOrNull(2)    // Lower Back
        ).filterValues { it != null }
            .mapValues { it.value!! } // Filter out null EvaluationValue objects and assert non-null
    }

    val currentPointsMap = if (isFrontView) frontPointsMap else backPointsMap

    val imagePainter =
        painterResource(if (isFrontView) Res.drawable.body_front else Res.drawable.body_back)

    val aspectRatio = imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(32.dp))
                .background(Color.White).aspectRatio(0.5f)
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

            currentPointsMap.forEach { (offset, evalValue) ->
                val x = offset.x * imageWidth + offsetX
                val y = offset.y * imageHeight + offsetY

                // Determine if any of the sub-options for this part are selected
                val subOptionsForPart = parseBodyPartSubOptions(evalValue.available_value)
                val isAnySubOptionSelected = selectedValues.any { subOptionsForPart.contains(it) }

                Box(
                    modifier = Modifier.offset { IntOffset(x.toInt(), y.toInt()) }.size(28.dp)
                        .background(
                            color = if (isAnySubOptionSelected) primaryColor else Color.LightGray.copy(
                                alpha = 0.5f
                            ), shape = CircleShape
                        ).clickable {
                            onBodyPartClicked(evalValue) // Trigger the dialog opening in parent
                        })
            }

            val flipIcon = FontAwesomeIcons.Solid.Redo
            IconButton(
                onClick = { isFrontView = !isFrontView },
                modifier = Modifier.align(Alignment.TopEnd).padding(24.dp).size(20.dp)
                    .background(primaryColor, shape = CircleShape)
            ) {
                Icon(
                    imageVector = flipIcon,
                    contentDescription = stringResource(Res.string.flip_button_label),
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// Helper function to parse the main title (e.g., "Head")
fun parseBodyPartTitle(availableValue: String) =
    availableValue.substringBefore(" (?)").trim()

// Helper function to parse the list of sub-options (e.g., ["head", "eyes", "jaw"])
fun parseBodyPartSubOptions(availableValue: String): List<String> {
    return availableValue.substringAfter(" (?)", "")
        .split(",").map { it.trim() }
        .filter { it.isNotBlank() }
}

@Composable
@Preview
fun HumanBodyModelSelectorPreview() {
    val mockValues = listOf(
        EvaluationValue(
            473, "Head (?) head,eyes,jaw",
            false, "", 256
        ),
        EvaluationValue(
            474, "Chest (?) chest,ribs",
            false, "", 256
        ),
        EvaluationValue(
            475, "Lower Back (?) lower back",
            false, "", 256
        ),
        EvaluationValue(
            476, "Right Arm (?) wrist,fingers",
            false, "", 256
        ),
        EvaluationValue(
            477, "Belly (?) belly,stomach,liver",
            false, "", 256
        )
    )

    val currentSelectedGranularParts by remember { mutableStateOf(listOf("head", "liver")) }

    HumanBodyModelSelector(
        availableValues = mockValues,
        selectedValues = currentSelectedGranularParts,
        onBodyPartClicked = { clickedPart ->
            println("Human Body Part Clicked: ${clickedPart.available_value}")
            // In a real app, this would trigger showing the dialog
        })
}