package org.example.hit.heal.presentaion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import org.example.hit.heal.presentaion.primaryColor

@Composable
fun CustomSlider(start: Int, end: Int, onValueChange: ((Int) -> Unit)? = null) {
    var sliderValue by remember { mutableStateOf((end - start) / 2) }

    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = start.toString())
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .padding(horizontal = 8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(50))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        val newValue = (sliderValue + dragAmount / 20)
                        sliderValue = newValue.toInt().coerceIn(start, end)
                        onValueChange?.invoke(sliderValue)
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(sliderValue / (end - start).toFloat())
                    .height(40.dp)
                    .background(primaryColor, shape = RoundedCornerShape(50))
            )
        }
        Text(text = end.toString())
    }
    Text("value: $sliderValue", fontSize = 30.sp)
}

