package presentation.dialScreen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class DialScreenViewModel : ViewModel() {

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _enteredNumber = MutableStateFlow("")
    val enteredNumber: StateFlow<String> = _enteredNumber

    fun onNumberClicked(number: String) {
        _enteredNumber.value += number
    }

    fun toggleDialog() {
        _isDialogVisible.value = !_isDialogVisible.value
    }

    fun dial() {
        _isDialogVisible.value = false
    }

    fun deleteLastDigit() {
        if (_enteredNumber.value.isNotEmpty()) {
            _enteredNumber.value = _enteredNumber.value.dropLast(1)
        }
    }
}
