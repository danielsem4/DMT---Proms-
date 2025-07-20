package org.example.hit.heal.presentation.medication.presentaion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Resources


import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource


@Composable
fun CustomWeeklySelector(  selectedDays: List<Int>, onSelectionChange: (List<Int>) -> Unit) {
    val days = listOf(
        stringResource(Resources.String.weekDayS),
        stringResource(Resources.String.weekDayM),
        stringResource(Resources.String.weekDayTu),
        stringResource(Resources.String.weekDayW),
        stringResource(Resources.String.weekDayTh),
        stringResource(Resources.String.weekDayF),
        stringResource(Resources.String.weekDaySa)
    )


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


