package org.example.hit.heal.presentation.medication.presentaion.screens.medicationAlarm
import ToastMessage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.data.model.Medications.Medication
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.presentation.medication.presentaion.components.CustomDatePickerBox
import org.example.hit.heal.presentation.medication.presentaion.components.CustomDropdownMenu
import org.example.hit.heal.presentation.medication.presentaion.components.CustomWeeklySelector
import org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel.MedicationViewModel
import org.example.hit.heal.presentation.medication.presentaion.screens.mainMedication.MainMedicationScreen
import org.example.hit.heal.presentation.medication.presentaion.screens.medicationAlarm.components.generateTimeSlots
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.String


class MedicationAlarmScreen (private val medication: Medication) : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationViewModel>()
        val medicationName = viewModel.medicationName.collectAsState().value
        val medicationchoose = viewModel.getMedication()
        val selectedFrequency by viewModel.selectedFrequency.collectAsState()
        val selectedTimeBetweenDoses by viewModel.selectedTimeBetweenDoses.collectAsState()
        val selectedStartTime by viewModel.selectedStartTime.collectAsState()
        val selectedDays by viewModel.selectedDays.collectAsState()
        val selectedStartDate by viewModel.selectedStartDate.collectAsState()
        val selectedEndDate by viewModel.selectedEndDate.collectAsState()
        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        val frequencyOptions =
            listOf(stringResource(Resources.String.daily), stringResource(Resources.String.weekly))
        val timeBetweenDosesOptions = listOf(
            stringResource(Resources.String.one),
            stringResource(Resources.String.two),
            stringResource(Resources.String.three),
            stringResource(Resources.String.four)
        )
        var toastMessage by remember { mutableStateOf<String?>(null) }
        var toastType by remember { mutableStateOf(ToastType.Normal) }

        val startDateError = stringResource(Resources.String.error_start_date_empty)
        val endDateError = stringResource(Resources.String.error_end_before_start)

        fun validateInput() {
            if (selectedStartDate.isBlank()) {
                errorMessage = startDateError
                isError = true
                return
            }
            if (selectedEndDate.isNotBlank() && selectedEndDate < selectedStartDate) {
                errorMessage = endDateError
                isError = true
                return
            }

            isError = false
            errorMessage = ""
            viewModel.buildAndSendMedication(medicationchoose, medicationName)


        }

        BaseScreen(
            title = medicationName,
            config = ScreenConfig.PhoneConfig,
            onPrevClick = { navigator.pop() },
            onNextClick = { validateInput() },
            prevButtonText = stringResource(Resources.String.back),
            nextButtonText = stringResource(Resources.String.save),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        stringResource(Resources.String.whenNotification),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    CustomDropdownMenu(
                        selectedOption = selectedFrequency,
                        options = frequencyOptions,
                        onOptionSelected = { viewModel.setFrequency(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (selectedFrequency == stringResource(Resources.String.daily)) {
                        Text(
                            stringResource(Resources.String.howManyTimesPerDay),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        CustomDropdownMenu(
                            selectedOption = selectedTimeBetweenDoses,
                            options = timeBetweenDosesOptions,
                            onOptionSelected = { viewModel.setTimeBetweenDoses(it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (selectedFrequency == stringResource(Resources.String.weekly)) {
                        Text(
                            stringResource(Resources.String.selectWeekDays),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        CustomWeeklySelector(
                            selectedDays = selectedDays,
                            onSelectionChange = { viewModel.setSelectedDays(it) }
                        )
                    }
                    Text(
                        stringResource(Resources.String.startTime),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    CustomDropdownMenu(
                        selectedOption = selectedStartTime,
                        options = generateTimeSlots(),
                        onOptionSelected = { viewModel.setStartTime(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        stringResource(Resources.String.notificationStartQuestion),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    CustomDatePickerBox(
                        value = selectedStartDate,
                        onValueChange = { viewModel.setStartDate(it) },
                        label = stringResource(Resources.String.startDate),
                        modifier = Modifier.fillMaxWidth()
                    )
                    CustomDatePickerBox(
                        value = selectedEndDate,
                        onValueChange = { viewModel.setEndDate(it) },
                        label = stringResource(Resources.String.endDate),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (isError) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Text(
                        stringResource(Resources.String.leaveEndDateEmpty),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                toastMessage?.let { msg ->
                    ToastMessage(
                        message = msg,
                        type = toastType,
                        alignUp = false,
                        onDismiss = { toastMessage = null }
                    )
                }
            }
        }
    }
}


