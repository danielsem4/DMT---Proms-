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
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePicker(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
    WheelDatePickerView(
        modifier = Modifier.padding(10.dp),
        title = stringResource(Resources.String.selectDate),
        showDatePicker = true,
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
        dateTextColor = Color(0xff007AFF),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = Color.LightGray,
        ),
        shape = RoundedCornerShape(18.dp),
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        rowCount = 3,
        showShortMonths = false,
        onDoneClick = { selectedDate ->
            onDateSelected(normalizeDateString(selectedDate.toString()))

            onDismiss()
        },
        onDismiss = { onDismiss() }
    )
}