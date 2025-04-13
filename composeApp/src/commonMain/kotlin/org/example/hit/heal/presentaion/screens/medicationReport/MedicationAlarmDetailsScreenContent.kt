package org.example.hit.heal.presentaion.screens.medicationReport


import MedicationAlarmViewModel
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
import org.example.hit.heal.presentaion.components.CustomDatePickerBox
import org.example.hit.heal.presentaion.components.CustomDropdownMenu

import org.example.hit.heal.presentaion.components.CustomWeeklySelector

import org.example.hit.heal.presentaion.screens.BaseScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


class MedicationAlarmDetailsScreenContent (private val  medicationName: String) : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<MedicationAlarmViewModel>()

        val selectedFrequency by viewModel.selectedFrequency.collectAsState()
        val selectedTimeBetweenDoses by viewModel.selectedTimeBetweenDoses.collectAsState()
        val selectedStartTime by viewModel.selectedStartTime.collectAsState()
        val selectedDays by viewModel.selectedDays.collectAsState()
        val selectedStartDate by viewModel.selectedStartDate.collectAsState()
        val selectedEndDate by viewModel.selectedEndDate.collectAsState()

        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }

        val frequencyOptions = listOf("Daily", "Weekly")
        val timeBetweenDosesOptions = listOf("1", "2", "4", "6")
        val startTimeOptions = generateTimeSlots()

        fun validateInput(): Boolean {
            if (selectedStartDate.isBlank()) {
                errorMessage = "Start date cannot be empty"
                isError = true
                return false
            }
            if (selectedEndDate.isNotBlank() && selectedEndDate < selectedStartDate) {
                errorMessage = "End date cannot be earlier than start date"
                isError = true
                return false
            }
            isError = false
            errorMessage = ""
            return true
        }

        BaseScreen(
            title = medicationName,
            prevButtonText = "Back",
            nextButtonText = "Save",
            onPrevClick =
            { navigator.pop() },
            onNextClick =
            {
                if (validateInput()) {

                    navigator.pop()
                }
            }
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "When do you want to get notification",
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

                if (selectedFrequency == "Daily") {
                    Text(
                        "How many times per day",
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

                if (selectedFrequency == "Weekly") {
                    Text(
                        "Select week days",
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
                    "Start time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                CustomDropdownMenu(
                    selectedOption = selectedStartTime,
                    options = startTimeOptions,
                    onOptionSelected = { viewModel.setStartTime(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "What date you want the notification will work?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 32.dp)
                )

                CustomDatePickerBox(
                    value = selectedStartDate,
                    onValueChange = { viewModel.setStartDate(it) },
                    label = "Start date",
                    modifier = Modifier.fillMaxWidth()
                )

                CustomDatePickerBox(
                    value = selectedEndDate,
                    onValueChange = { viewModel.setEndDate(it) },
                    label = "End date",
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
                    "Leave empty to not set end date",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


    private fun generateTimeSlots(): List<String> {
        val timeSlots = mutableListOf<String>()
        for (hour in 0..23) {
            for (minute in listOf(0, 15, 30, 45)) {
                val formattedTime =
                    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                timeSlots.add(formattedTime)
            }
        }
        return timeSlots
    }

