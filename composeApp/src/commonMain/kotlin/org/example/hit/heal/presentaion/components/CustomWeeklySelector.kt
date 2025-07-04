package org.example.hit.heal.presentaion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.new_memory_test.primaryColor


@Composable
fun CustomWeeklySelector(  selectedDays: List<Int>, onSelectionChange: (List<Int>) -> Unit) {
    val days = listOf("S", "M", "T", "W", "T", "F", "S")


    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        days.forEachIndexed { index, day ->
            val isSelected = index in selectedDays
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Canvas(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            val newSelection = selectedDays.toMutableList()
                            if (isSelected) {
                                newSelection.remove(index)
                            } else {
                                newSelection.add(index)
                            }
                            onSelectionChange(newSelection)
                        }
                ) {

                    drawCircle(
                        color = if (isSelected) primaryColor else Color.Black,
                        style = if (isSelected) Fill else Stroke(width = 5f)

                    )
                }
                Text(text = day, fontSize = 14.sp)
            }
        }
    }
}


