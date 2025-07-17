package org.example.hit.heal.presentaion.screens.MedicationViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.model.Medications.Medication
import core.data.model.Medications.MedicationReport
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.util.PrefKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlin.math.abs

class MedicationViewModel
    (private val remoteDataSource: AppApi,
     private val storage: Storage
) : ViewModel() {

    private var clinicId: Int? = null
    private var patientId: Int? = null

    private val _medication = MutableStateFlow<Medication?>(null)
    var medicationId by mutableStateOf<Int?>(null)

    private val _medicationName = MutableStateFlow("")
    val medicationName: StateFlow<String> = _medicationName

    private val _selectedFrequency = MutableStateFlow("Daily")
    val selectedFrequency: StateFlow<String> = _selectedFrequency

    private val _selectedTimeBetweenDoses = MutableStateFlow("1")
    val selectedTimeBetweenDoses: StateFlow<String> = _selectedTimeBetweenDoses

    private val _selectedStartTime = MutableStateFlow("00:00")
    val selectedStartTime: StateFlow<String> = _selectedStartTime

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays

    private val _selectedStartDate = MutableStateFlow("")
    val selectedStartDate: StateFlow<String> = _selectedStartDate

    private val _selectedEndDate = MutableStateFlow("")
    val selectedEndDate: StateFlow<String> = _selectedEndDate

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var _isReport by mutableStateOf<Boolean>(false)
        private set

    fun setFrequency(frequency: String) {
        _selectedFrequency.value = frequency
    }

    fun setTimeBetweenDoses(time: String) {
        _selectedTimeBetweenDoses.value = time
    }

    fun setStartTime(time: String) {
        _selectedStartTime.value = time
    }

    fun setSelectedDays(days: List<Int>) {
        _selectedDays.value = days
    }

    fun setStartDate(date: String) {
        _selectedStartDate.value = date
    }

    fun setEndDate(date: String) {
        _selectedEndDate.value = date
    }

    fun setMedicationName(name: String) {
        _medicationName.value = name
    }

    fun chooseMedication(medication: Medication) {
        _medication.value = medication
    }

    fun getMedication(): Medication? {
        return _medication.value
    }

    var selectedDate by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault()).run {
            "$dayOfMonth/$monthNumber/$year"
        }
    )
        private set

    var selectedTime by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault()).run {
            "$hour:$minute"
        }
    )
        private set



    fun setReport(report: Boolean){
        _isReport = report
    }

    fun getReport(): Boolean{
        return _isReport
    }

    fun updateDate(newDate: String) {
        selectedDate = newDate
        errorMessage = null
    }

    fun updateTime(newTime: String) {
        selectedTime = newTime
        errorMessage = null
    }

    suspend fun initUserIds(): Boolean {
        val clinic = storage.get(PrefKeys.clinicId)
        val patient = storage.get(PrefKeys.userId)?.toIntOrNull()

        return if (clinic != null && patient != null) {
            clinicId = clinic
            patientId = patient
            true
        } else {
            false
        }
    }

    fun validateAndSave(medication: Medication? , medicationId: Int? ) {
        val date = selectedDate
        val time = selectedTime

        if (medication == null) {
            errorMessage = "Не выбран медикамент"
            return
        }

        if (date.isBlank() || time.isBlank()) {
            errorMessage = "Пожалуйста, выберите дату и время"
            return
        }

        val timestamp = convertToIso8601(date,time)

        viewModelScope.launch {
            reportMedication(medication, timestamp, medicationId)
        }
    }



    suspend fun reportMedication(medication: Medication, timestamp: String, medicationId: Int?) {
        isLoading.value = true
        if (!initUserIds()) {
            errorMessage = "We couldn't get userId/clinicId"
            isLoading.value = false
            return
        }


        //if (!isValidIso8601Format(timestamp)) {
        //    println(" Invalid format: $timestamp")
        //} else {
        //    println(" Valid: $timestamp")
        //}


        val report = MedicationReport(
            clinicId = clinicId!!,
            patientId = patientId!!,
            medicationId = medicationId!! ,
            timestamp = timestamp
        )
        val result = remoteDataSource.reportMedicationTook(report)
        val jsonString = Json.encodeToString(report)
        println(jsonString)
        result.onSuccess {
            errorMessage = null
           // println(" УУУУУУУУ Молодец какая !")
        }.onError { error ->
            println("Error: $error")
          //  println(" УУУУУУУУ Не  Молодец  !")
        }
        isLoading.value = false
    }

    fun convertToIso8601(
        date: String,
        time: String,
        useUtc: Boolean = false
    ): String {
        val (day, month, year) = date.split("/").map { it.padStart(2, '0') }
        val timeParts = time.split(":").map { it.padStart(2, '0') }

        val hour = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
        val second = timeParts.getOrNull(2)?.toIntOrNull() ?: 0

        val localDateTime = LocalDateTime(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour,
            minute,
            second
        )

        val timeZone = if (useUtc) TimeZone.Companion.UTC else TimeZone.Companion.currentSystemDefault()
        val instant = localDateTime.toInstant(timeZone)

        // Для UTC возвращаем с "Z"
        if (useUtc) {
            return instant.toString()  // ISO8601 с Z
        }

        // Для локального времени — смещение вручную
        val offset = timeZone.offsetAt(instant)
        val totalSeconds = offset.totalSeconds
        val sign = if (totalSeconds >= 0) "+" else "-"
        val hours = abs(totalSeconds / 3600)
        val minutes = abs((totalSeconds % 3600) / 60)
        val offsetStr = "$sign${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"

        val isoDateTime = "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}" +
                "T${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"

        return isoDateTime + offsetStr
    }

    var medications = mutableStateOf<List<Medication>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun loadMedications() {
        viewModelScope.launch {
            isLoading.value = true

            if (!initUserIds()) {
                errorMessage = "We couldn't get userId/clinicId"
                isLoading.value = false
                println(errorMessage)
                return@launch
            }

            remoteDataSource.getAllPatientMedicines(3, 243)
                .onSuccess {
                    medications.value = it
                    errorMessage = "We could load medications"
                    println(medications)

                }
                .onError {
                        error ->
                    when (error) {
                        DataError.Remote.NO_INTERNET -> {
                            errorMessage= "Нет подключения к интернету"
                        }
                        DataError.Remote.REQUEST_TIMEOUT -> {
                            errorMessage= "Время ожидания истекло"
                        }
                        DataError.Remote.SERVER -> {
                            errorMessage= "Ошибка на сервере"
                        }
                        DataError.Remote.SERIALIZATION -> {
                            errorMessage= "Ошибка при разборе данных"
                        }
                        else -> {
                            errorMessage= "Неизвестная ошибка: $error"
                        }
                    }

                    println("Ошибка загрузки: $error")
                  }

            println(errorMessage)
            isLoading.value = false
        }
    }

    fun buildAndSendMedication(
        medication: Medication?, // null если создаём новое
        medicationName: String
    ) {
        viewModelScope.launch {
            if (!initUserIds()) {
                errorMessage = "Ошибка: нет данных пациента или клиники"
                return@launch
            }

            val newMedication = Medication(
                id = medication?.id ?: 0,
                patient = patientId!!,
                medicine = clinicId.toString(),
                name = medicationName,
                form = "tablet",
                unit = "mg",
                frequency = selectedFrequency.value,
                frequencyData = if (selectedFrequency.value == "Weekly")
                    selectedDays.value.joinToString(",") else selectedTimeBetweenDoses.value,
                startDate = selectedStartDate.value,
                endDate = selectedEndDate.value.takeIf { it.isNotBlank() },
                dosage = "1" // или из ввода
            )

            val result = remoteDataSource.setMedicationNotifications(newMedication)

            result.onSuccess {
                errorMessage = null
                println("Medication notification saved!")
            }.onError { error ->
                errorMessage = "Ошибка при сохранении: $error"
                println("Ошибка сохранения медикамента: $error")
            }
        }
    }
    fun sentMedication(medication: Medication){

    }
    private fun isValidDateTime(date: String, time: String): Boolean {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault())

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


    fun getCurrentDate(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault())
        return "${now.dayOfMonth}/${now.monthNumber}/${now.year}"
    }

    fun getCurrentTime(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault())
        return "${now.hour}:${now.minute}"
    }


}