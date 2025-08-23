package org.example.hit.heal.core.presentation.components.dialogs
import ToastMessage
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.*
import org.example.hit.heal.core.presentation.components.dateAndTiem.DatePicker
import org.example.hit.heal.core.presentation.components.dateAndTiem.TimePicker
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * A generic dialog for reporting an action.
 * Provides buttons for reporting and selecting date & time.
 */

@Composable
fun ReportDialog(
    name: String,
    selectedDate: String,
    selectedTime: String,
    isLoading: Boolean,
    errorMessage: String?,
    isSuccess: Boolean?,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    icon: DrawableResource,
    onDateUpdate: (String) -> Unit,
    onTimeUpdate: (String) -> Unit,
    onResetSuccess: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastType by remember { mutableStateOf(ToastType.Normal) }
    var buttonPressed by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(Sizes.radiusLg),
        modifier = Modifier.border(Sizes.elevationSm, Black, RoundedCornerShape(Sizes.radiusLg)),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Resources.String.reportMedicationTaken),
                    fontWeight = FontWeight.Bold,
                    fontSize = FontSize.EXTRA_MEDIUM,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Sizes.iconSize3Xl)
                        .padding(vertical = Sizes.spacingSm),
                )
                Spacer(modifier = Modifier.height(Sizes.spacingSm))
                Text(
                    buildString {
                        appendLine("${stringResource(Resources.String.took)} $name")
                        appendLine("${stringResource(Resources.String.dateMedications)} $selectedDate")
                        append("${stringResource(Resources.String.timeMedications)} $selectedTime")
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = FontSize.EXTRA_MEDIUM,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        onSave()
                        buttonPressed = true
                    },
                    colors = ButtonDefaults.buttonColors(ButtonPrimary),
                    shape = RoundedCornerShape(Sizes.radiusXl),
                    modifier = Modifier.height(Sizes.buttonHeightMd)
                ) {
                    Text(
                        stringResource(Resources.String.save),
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = FontSize.EXTRA_REGULAR
                    )
                }
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(ButtonPrimary),
                    shape = RoundedCornerShape(Sizes.radiusXl),
                    modifier = Modifier.height(Sizes.buttonHeightMd)
                ) {
                    Text(
                        stringResource(Resources.String.date),
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = FontSize.EXTRA_REGULAR
                    )
                }
            }
        }
    )

    if (showDatePicker) {
        DatePicker(
            onDateSelected = { date ->
                onDateUpdate(date)
                showDatePicker = false
                showTimePicker = true
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        TimePicker(
            onTimeSelected = { time ->
                onTimeUpdate(time)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

    if (showToast && toastMessage != null) {
        ToastMessage(
            message = toastMessage!!,
            type = toastType,
            onDismiss = { showToast = false }
        )

        LaunchedEffect(Unit) {
            delay(2500)
            showToast = false
        }
    }

    LaunchedEffect(isLoading) {
        if (!isLoading && buttonPressed) {
            toastMessage = errorMessage ?: "Error"
            toastType = if (isSuccess == true) ToastType.Success else ToastType.Error
            showToast = true
            buttonPressed = false
            onResetSuccess()
        }
    }
}
