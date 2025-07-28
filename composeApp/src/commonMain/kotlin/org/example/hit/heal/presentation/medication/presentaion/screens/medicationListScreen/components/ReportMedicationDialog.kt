package org.example.hit.heal.presentation.medication.presentaion.screens.medicationListScreen.components


import ToastMessage
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.data.model.Medications.Medication
import dmt_proms.composeapp.generated.resources.Res
import dmt_proms.composeapp.generated.resources.pills
import kotlinx.coroutines.delay
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel.MedicationViewModel
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

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastType by remember { mutableStateOf(ToastType.Normal) }
    var buttonPressed by remember { mutableStateOf(false) }



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
                        .padding(bottom = 8.dp, top = 12.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    buildString {
                        appendLine(stringResource(Resources.String.took))
                        appendLine(medicationName)
                        appendLine("${stringResource(Resources.String.dateMedications)} ${viewModel.selectedDate}")
                        append("${stringResource(Resources.String.timeMedications)} ${viewModel.selectedTime}")
                    },
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
                    onClick = {
                        viewModel.validateAndSave(medication, medication.id)
                        buttonPressed = true
                    },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        stringResource(Resources.String.save),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        stringResource(Resources.String.date),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Button(
                    onClick = { showTimePicker = true },
                    colors = ButtonDefaults.buttonColors(primaryColor),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        stringResource(Resources.String.time),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
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
    LaunchedEffect(viewModel.isLoading.value) {
        if (!viewModel.isLoading.value && buttonPressed) {
            toastMessage = viewModel.errorMessage?: "Error"
            toastType = if (viewModel.successMessageReport == true) ToastType.Success else ToastType.Error
            showToast = true
            buttonPressed = false
            viewModel.resetSaveSuccess()

        }
    }
   // LaunchedEffect(viewModel.isLoading.value) {
   //     if (!viewModel.isLoading.value && buttonPressed) {
   //         toastMessage = viewModel.errorMessage ?: "Error"
   //         toastType = if (viewModel.errorMessage == null) ToastType.Success else ToastType.Error
   //         showToast = true
   //         buttonPressed = false
   //         viewModel.resetSaveSuccess()
   //     }
   // }

}


