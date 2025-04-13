import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MedicationAlarmViewModel : ViewModel() {

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
}

