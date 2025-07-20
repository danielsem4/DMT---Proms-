package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.formatLabel
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RoundedFilledSlider(
    start: Float,
    end: Float,
    value: Float,
    availableValues: List<Float>,
    startText: String = start.formatLabel(),
    endText: String = end.formatLabel(),
    onValueChanged: ((Float) -> Unit)? = null
) {
    val range = end - start
    val fillFraction = ((value - start) / range).coerceIn(0f, 0.999f)

    val steps = (availableValues.size - 2).coerceAtLeast(0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = start.formatLabel(),
                    fontSize = 14.sp
                )
                Text(
                    text = startText,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fillFraction)
                        .fillMaxHeight()
                        .background(primaryColor, RoundedCornerShape(12.dp))
                )

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
                        thumbColor = primaryColor,
                        activeTrackColor = Color.Transparent,
                        inactiveTrackColor = Color.Transparent,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = end.formatLabel(),
                    fontSize = 14.sp
                )
                Text(
                    text = endText,
                    fontSize = 12.sp
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
        startText = "לא כואב",
        endText = "כואב מאוד",
        onValueChanged = { sliderValue = it }
    )
}