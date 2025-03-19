import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.hit.heal.presentaion.components.CustomDropdownMenu
import org.example.hit.heal.presentaion.components.CustomOutlinedTextDataField
import org.example.hit.heal.presentaion.components.CustomWeeklySelector
import org.example.hit.heal.presentaion.primaryColor

import org.example.hit.heal.presentaion.screens.BaseScreen


data class MedicationReportDetailsScreen(val medicationName: String) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var selectedFrequency by remember { mutableStateOf("Daily") }
        var selectedTimeBetweenDoses by remember { mutableStateOf("1") }
        var selectedStartTime by remember { mutableStateOf("00:00") }
        var selectedStartDate by remember { mutableStateOf("") }
        var selectedEndDate by remember { mutableStateOf("") }
        val selectedDaysState = remember { mutableStateOf<List<Int>>(emptyList()) }

        val frequencyOptions = listOf("Daily", "Weekly")
        val timeBetweenDosesOptions = listOf("1", "2", "4", "6")
        val startTimeOptions = generateTimeSlots()

        BaseScreen(
            title = medicationName,
            prevButtonText = "Back",
            nextButtonText = "Save",
            onPrevClick = { navigator.pop() },
            onNextClick = {  }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "When do you want to get notification",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                CustomDropdownMenu(
                    selectedOption = selectedFrequency,
                    options = frequencyOptions,
                    onOptionSelected = { selectedFrequency = it },
                    modifier = Modifier.fillMaxWidth()
                )

                if (selectedFrequency == "Daily") {
                    Text(
                        text = "How many times per day",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    CustomDropdownMenu(
                        selectedOption = selectedTimeBetweenDoses,
                        options = timeBetweenDosesOptions,
                        onOptionSelected = { selectedTimeBetweenDoses = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (selectedFrequency == "Weekly") {
                    Text(
                        text = "Select week days",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 8.dp) )

                    CustomWeeklySelector(
                        selectedDays = selectedDaysState.value,
                        onSelectionChange = { selectedDaysState.value= it }
                    )

                }

                Text(
                    text = "Start time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                CustomDropdownMenu(
                    selectedOption = selectedStartTime,
                    options = startTimeOptions,
                    onOptionSelected = { selectedStartTime = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "What date you want the notification will work",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top =32.dp)
                )

                CustomOutlinedTextDataField(
                    value = selectedStartDate,
                    onValueChange = { selectedStartDate = it },
                    label = "Start date",
                    trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
                )

                CustomOutlinedTextDataField(
                    value = selectedEndDate,
                    onValueChange = { selectedEndDate = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = "End date",
                    trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
                )

                Text(
                    text = "Leave empty to not set end date",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    private fun generateTimeSlots(): List<String> {
        val timeSlots = mutableListOf<String>()
        for (hour in 0..23) {
            for (minute in listOf(0, 15, 30, 45)) {
                val formattedTime = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                timeSlots.add(formattedTime)
            }
        }
        return timeSlots
    }


}