package org.example.hit.heal.presentaion.components


import MedicationViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.data.model.Medications.Medication


import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.pills
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ReportMedicationDialog(
    medicationName: String,
    medication: Medication,
    onDismiss: () -> Unit,
    viewModel: MedicationViewModel = koinViewModel()
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(Resources.String.reportMedicationTaken),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(Res.drawable.pills),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(bottom = 8.dp,top=12.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "I took $medicationName on:\nDate: ${viewModel.selectedDate}\nTime :${viewModel.selectedTime}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.validateAndSave(medication) },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(stringResource(Resources.String.save), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(stringResource(Resources.String.date), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = { showTimePicker = true },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(stringResource(Resources.String.time), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }
            }
        }
    )

    if (showDatePicker) {
        DatePicker(
            onDateSelected = { date -> viewModel.updateDate(date) },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        TimePicker(
            onTimeSelected = { time -> viewModel.updateTime(time) },
            onDismiss = { showTimePicker = false }
        )
    }
}



fun normalizeDateString(dateString: String): String {
    return if (dateString.contains("-")) {
        val parts = dateString.split("-")
        if (parts.size == 3) {

            val (year, month, day) = parts
            "${day.toInt()}/${month.toInt()}/${year.toInt()}"
        } else {
            dateString
        }
    } else {
        dateString
    }
}


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
