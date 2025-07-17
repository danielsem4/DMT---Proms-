package org.example.hit.heal.presentaion.screens.medicationListScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit, onDismiss: () -> Unit) {
    WheelTimePickerView(
        modifier = Modifier.padding(10.dp),
        title =stringResource(Resources.String.selectTimeLabel),
        showTimePicker = true,
        height = 200.dp,
        titleStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
        ),
        doneLabelStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF007AFF),
        ),
        textColor = Color(0xff007AFF),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = Color.LightGray,
        ),
        shape = RoundedCornerShape(18.dp),
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        rowCount = 3,
        onDoneClick = { selectedTime ->
            onTimeSelected(selectedTime.toString())
            onDismiss()
        },
        onDismiss = { onDismiss() }
    )
}
