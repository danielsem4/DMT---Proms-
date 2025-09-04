package org.example.hit.heal.core.presentation.components.dateAndTiem

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun DateTimePickerButton(
    modifier: Modifier = Modifier,
    initial: LocalDateTime? = null,
    onConfirm: (LocalDateTime, String) -> Unit
) {
    var showDate by remember { mutableStateOf(false) }
    var showTime by remember { mutableStateOf(false) }
    var pickedDate by remember { mutableStateOf<LocalDate?>(initial?.date) }
    var pickedTime by remember { mutableStateOf<LocalTime?>(initial?.time) }

    val displayText = remember(pickedDate, pickedTime) {
        val dt = pickedDate?.let { d -> pickedTime?.let { t ->
            LocalDateTime(d.year, d.month, d.day, t.hour, t.minute)
        } }
        dt?.let { formatDdMmYyyyHhMm(it) } ?: "בחר/י תאריך ושעה"
    }

    Button(onClick = { showDate = true }, modifier = modifier.height(44.dp), shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)) {
        Text(displayText, color = Color.White, fontSize = 14.sp, maxLines = 1)
    }

    // Date
    if (showDate) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            WheelDatePickerView(
                modifier = Modifier.padding(10.dp),
                title = stringResource(Resources.String.selectDate),
                showDatePicker = true,
                height = 200.dp,
                titleStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                ),
                doneLabelStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600), color = primaryColor),

                selectorProperties = WheelPickerDefaults.selectorProperties(borderColor = Color.LightGray),
                shape = RoundedCornerShape(18.dp),
                dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
                rowCount = 3,
                showShortMonths = false,
                onDoneClick = { selectedDate ->
                    pickedDate = normalizeToLocalDate(selectedDate.toString())
                    showDate = false
                    showTime = true
                },
                onDismiss = { showDate = false }
            )
        }
    }

    // Time
    if (showTime) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            WheelTimePickerView(
                modifier = Modifier.padding(10.dp),
                title = stringResource(Resources.String.selectTimeLabel),
                showTimePicker = true,
                height = 200.dp,
                titleStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = primaryColor),
                doneLabelStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600), color = primaryColor),

                selectorProperties = WheelPickerDefaults.selectorProperties(borderColor = Color.LightGray),
                shape = RoundedCornerShape(18.dp),
                dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
                rowCount = 3,
                onDoneClick = { selectedTime ->
                    pickedTime = normalizeToLocalTime(selectedTime.toString())
                    showTime = false
                    val d = pickedDate; val t = pickedTime
                    if (d != null && t != null) {
                        val dt = LocalDateTime(d.year, d.monthNumber, d.dayOfMonth, t.hour, t.minute)
                        onConfirm(dt, formatDdMmYyyyHhMm(dt))
                    }
                },
                onDismiss = { showTime = false }
            )
        }
    }
}

// helpers
private fun normalizeToLocalDate(s: String): LocalDate? = try {
    val p = s.split("-"); if (p.size==3) LocalDate(p[0].toInt(), p[1].toInt(), p[2].toInt()) else null
} catch (_: Throwable) { null }

private fun normalizeToLocalTime(s: String): LocalTime? = try {
    val p = s.split(":"); LocalTime(p[0].toInt(), p.getOrNull(1)?.toInt() ?: 0)
} catch (_: Throwable) { null }

private fun formatDdMmYyyyHhMm(dt: LocalDateTime): String {
    fun two(n:Int)=if(n<10)"0$n" else "$n"
    return "${two(dt.dayOfMonth)}/${two(dt.monthNumber)}/${dt.year} ${two(dt.hour)}:${two(dt.minute)}"
}