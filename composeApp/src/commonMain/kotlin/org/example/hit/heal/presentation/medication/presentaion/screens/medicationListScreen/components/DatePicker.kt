package org.example.hit.heal.presentation.medication.presentaion.screens.medicationListScreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePicker(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        WheelDatePickerView(
            modifier = Modifier.padding(10.dp),
            title = stringResource(Resources.String.selectDate),
            showDatePicker = true,
            height = 200.dp,
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
            ),
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = primaryColor,
            ),
            dateTextColor = Color.Black,
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
}