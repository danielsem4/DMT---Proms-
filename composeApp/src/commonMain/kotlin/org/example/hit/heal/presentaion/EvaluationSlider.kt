package org.example.hit.heal.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun EvaluationSlider(onPrevClick: (() -> Unit)? = null, onNextClick: (() -> Unit)? = null) {
    var sliderValue by remember { mutableStateOf(5) }

    BaseScreen(
        title = "Evaluation", onPrevClick, onNextClick
    ) {
        Text(text = "How do you feel?", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "0")
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .padding(horizontal = 8.dp)
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                val newValue = (sliderValue + dragAmount / 20).toInt().coerceIn(0, 10)
                                sliderValue = newValue
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(sliderValue / 10f)
                            .height(40.dp)
                            .background(Color(0xFF6FCF97), shape = RoundedCornerShape(50))
                    )
                }
                Text(text = "10")
            }
        }

        Text(text = sliderValue.toString(), style = MaterialTheme.typography.body1)
    }
}
