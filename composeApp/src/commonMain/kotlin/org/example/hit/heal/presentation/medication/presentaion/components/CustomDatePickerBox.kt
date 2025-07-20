package org.example.hit.heal.presentation.medication.presentaion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.now
import org.example.hit.heal.core.presentation.primaryColor
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView as WheelDatePickerView

@Composable
fun CustomDatePickerBox(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    WheelDatePickerView(
        showDatePicker = showDatePicker,
        height = 200.dp,
        dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
        rowCount = 3,
        titleStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        ),
        doneLabelStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        ),
        yearsRange = LocalDate.now().year..LocalDate.now().year + 100,
        onDoneClick = { selectedDate ->
            try {
                val parsedDate = LocalDate.parse(selectedDate.toString())
                if (parsedDate < LocalDate.now()) {
                    isError = true
                    errorMessage = "Date cannot be in the past"
                } else {
                    isError = false
                    errorMessage = null
                    onValueChange(selectedDate.toString())
                }
            } catch (e: Exception) {
                isError = true
                errorMessage = "Incorrect date"
            }
            showDatePicker = false
        },
        onDismiss = { showDatePicker = false }
    )

    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, if (isError) Color.Red else Color.Black, RoundedCornerShape(10.dp))
                .clickable { showDatePicker = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value.ifEmpty { label },
                color = if (isError) Color.Red else Color.Black,
                fontSize = 16.sp
            )
        }
        if (isError) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}






