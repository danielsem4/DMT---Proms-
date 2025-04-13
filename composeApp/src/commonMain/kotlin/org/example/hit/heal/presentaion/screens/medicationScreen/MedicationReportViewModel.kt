package org.example.hit.heal.presentaion.screens.medicationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime


class MedicationReportViewModel : ViewModel() {

    var selectedDate by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).run {
            "$dayOfMonth/$monthNumber/$year"
        }
    )
        private set

    var selectedTime by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).run {
            "$hour:$minute"
        }
    )
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateDate(newDate: String) {
        selectedDate = newDate
        errorMessage = null
    }

    fun updateTime(newTime: String) {
        selectedTime = newTime
        errorMessage = null
    }

    fun validateAndSave(onSuccess: () -> Unit) {
        if (isValidDateTime(selectedDate, selectedTime)) {
            errorMessage = null
            onSuccess()
        } else {
            errorMessage = "Date cannot be in the past"
        }
    }

    private fun isValidDateTime(date: String, time: String): Boolean {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        return try {
            val (day, month, year) = date.split("/").map { it.toInt() }
            val (hour, minute) = time.split(":").map { it.toInt() }

            val selectedDate = LocalDate(year, month, day)
            val selectedTime = LocalTime(hour, minute)
            val selectedDateTime = selectedDate.atTime(selectedTime)

            selectedDateTime <= currentDateTime
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        fun getCurrentDate(): String {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return "${now.dayOfMonth}/${now.monthNumber}/${now.year}"
        }

        fun getCurrentTime(): String {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return "${now.hour}:${now.minute}"
        }
    }
}