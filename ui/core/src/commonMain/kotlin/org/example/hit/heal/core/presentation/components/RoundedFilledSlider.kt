package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.paddingXs
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.formatLabel
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A custom slider component with a rounded filled track.
 */

@Composable
fun RoundedFilledSlider(
    start: Float,
    end: Float,
    value: Float,
    availableValues: List<Float>,
    startText: String = start.formatLabel(),
    endText: String = end.formatLabel(),
    onValueChanged: ((Float) -> Unit)? = null,
    trackBrush: Brush? = null,
    trackHeight: Dp = heightSm,
    cornerRadius: Dp = radiusMd2,
    showEdgeLabels: Boolean = true
) {
    val range = end - start
    val fillFraction = ((value - start) / range).coerceIn(0f, 0.999f)
    val steps = (availableValues.size - 2).coerceAtLeast(0)

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(cornerRadius))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMd)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (showEdgeLabels) {
                    Text(
                        text = start.toString(),
                        fontSize = EXTRA_MEDIUM,
                        modifier = Modifier.padding(end = paddingSm)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(trackHeight)
                ) {
                    // Background track
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.LightGray, RoundedCornerShape(cornerRadius))
                    )
                    // Filled track (gradient or color)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fillFraction)
                            .fillMaxHeight()
                            .background(
                                trackBrush ?: Brush.horizontalGradient(listOf(primaryColor, primaryColor)),
                                RoundedCornerShape(cornerRadius)
                            )
                    )
                    // Slider (transparent)
                    Slider(
                        value = value,
                        onValueChange = { newValue ->
                            val snapped = availableValues.minByOrNull { kotlin.math.abs(it - newValue) }
                                ?: newValue
                            onValueChanged?.invoke(snapped)
                        },
                        valueRange = start..end,
                        steps = steps,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Transparent,
                            activeTrackColor = Color.Transparent,
                            inactiveTrackColor = Color.Transparent,
                            activeTickColor = Color.Transparent,
                            inactiveTickColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (showEdgeLabels) {
                    Text(
                        text = end.toString(),
                        fontSize = EXTRA_MEDIUM,
                        modifier = Modifier.padding(start = paddingSm)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingXs),
            ) {
                Text(
                    text = startText,
                    fontSize = EXTRA_MEDIUM,

                    )

                Text(
                    text = value.toString(),
                    fontSize = EXTRA_MEDIUM,
                    color = primaryColor,

                    )

                Text(
                    text = endText,
                    fontSize = EXTRA_MEDIUM,

                    )
            }
        }
    }

}

@Preview
@Composable
fun RoundedFilledSliderPreview() {
    var sliderValue by remember { mutableStateOf(1f) }

    val availableValues = listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f)



        RoundedFilledSlider(
            start = 1f,
            end = 10f,
            value = sliderValue,
            availableValues = availableValues,
            startText = "Great",
            endText = "Terrible",
            onValueChanged = { sliderValue = it }
        )

}