package org.example.hit.heal.presentation.medication.presentaion.screens.MedicationViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import core.data.model.Medications.Medication
import core.data.model.Medications.MedicationReport
import core.data.storage.Storage
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.onError
import core.domain.onSuccess
import core.util.PrefKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.example.hit.heal.core.presentation.ToastType
import kotlin.math.abs
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MedicationViewModel
    (private val remoteDataSource: AppApi,
     private val storage: Storage,
) : ViewModel() {

    private var clinicId: Int? = null
    private var patientId: Int? = null

    private val _medication = MutableStateFlow<Medication?>(null)

    private val _medicationName = MutableStateFlow("")
    val medicationName: StateFlow<String> = _medicationName

    private val _selectedFrequency = MutableStateFlow( " ")
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

    private val _isSaveSuccessful = MutableStateFlow(false)


    var successMessageAlarm by mutableStateOf<Boolean?>(null)
    var successMessageReport by mutableStateOf<Boolean?>(null)


    fun resetSaveSuccess() {
        _isSaveSuccessful.value = false
    }

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var _isReport by mutableStateOf<Boolean>(false)
        private set


    var errorAlarmMessage by mutableStateOf<String?>(null)
    private set

    fun getMedication(): Medication? {
        return _medication.value
    }

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

    fun setReport(report: Boolean){
        _isReport = report
    }

    var medications = mutableStateOf<List<Medication>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    //--------------Date
    //or date now
    var selectedDate by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault()).run {
            "$dayOfMonth/$monthNumber/$year"
        }
    )
        private set

    //or selected time
    var selectedTime by mutableStateOf(
        Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault()).run {
            "$hour:$minute"
        }
    )
        private set

    fun updateDate(newDate: String) {
        selectedDate = newDate
        errorMessage = null
    }

    fun updateTime(newTime: String) {
        selectedTime = newTime
        errorMessage = null
    }

    //----------------Server
    //load medication evaluation


    //Take clinickId and patientId from storage
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
            errorMessage = errorMessage
            _isSaveSuccessful.value = false
            return
        }

        if (date.isBlank() || time.isBlank()) {
            errorMessage = errorMessage
            _isSaveSuccessful.value = false
            return
        }

       val timestamp = convertToIso8601(date,time)

        viewModelScope.launch {
            reportMedication(medication, timestamp, medicationId)
        }
    }

    //report of taking medication
    suspend fun reportMedication(medication: Medication, timestamp: String, medicationId: Int?) {
        isLoading.value = true
        if (!initUserIds()) {
            errorMessage = errorMessage
            isLoading.value = false
            _isSaveSuccessful.value = false
            return
        }
        val report = MedicationReport(
            clinicId = clinicId!!,
            patientId = patientId!!,
            medicationId = medication.medicine,
            timestamp = timestamp
        )
        val result = remoteDataSource.reportMedicationTook(report)

        result.onSuccess {
            successMessageReport = true
            errorMessage = null
            _isSaveSuccessful.value = true
        }.onError { error ->
            println("Error: $error")
            successMessageReport = false
            _isSaveSuccessful.value = false
        }
        isLoading.value = false
    }

    //convertor (it could be without too)
    @OptIn(ExperimentalTime::class)
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


        if (useUtc) {
            return instant.toString()  // ISO8601 с Z
        }


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

   //Take a list of medications of the person
    fun loadMedications() {
        viewModelScope.launch {
            isLoading.value = true


            if (!initUserIds()) {
                errorMessage = errorMessage
                isLoading.value = false

                return@launch
            }

            remoteDataSource.getAllPatientMedicines(3, 243)
                .onSuccess {
                    medications.value = it

                }
                .onError { error ->
            errorMessage = " $error"

        }
            println(errorMessage)
            isLoading.value = false
        }
    }

    fun buildAndSendMedication(
        medication: Medication?,
        medicationName: String
    ) {
        viewModelScope.launch {
            isLoading.value = true
            if (!initUserIds()) {
                errorAlarmMessage = errorMessage
                isLoading.value = false
                return@launch
            }

            val newMedication = Medication(
                id = clinicId!!,
                clinicId = clinicId!!,
                patient = patientId!!,
                patientId = patientId!!,
                medicationId = medication?.id ?: 0,
                medicine = medication?.medicine.toString() ?: "",
                name = medicationName,
                form = medication?.form ?: "",
                unit = medication?.unit ?: "",
                frequency = selectedFrequency.value,
                frequencyData = if (selectedFrequency.value == "Weekly")
                    selectedDays.value.joinToString(",") else selectedTimeBetweenDoses.value,
                startDate = selectedStartDate.value,
                endDate = selectedEndDate.value.takeIf { it.isNotBlank() },
                dosage = medication?.dosage ?: ""
            )

            val result = remoteDataSource.setMedicationNotifications(newMedication)

            withContext(Dispatchers.Main) {
                result.onSuccess {
                    errorAlarmMessage = null
                    successMessageAlarm = true
                    isLoading.value = false

                }.onError { error ->
                    errorAlarmMessage = "$error"
                    successMessageAlarm = false
                    isLoading.value = false

                }
            }
        }
    }
}