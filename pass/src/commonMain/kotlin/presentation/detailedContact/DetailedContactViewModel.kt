package presentation.detailedContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.updateAndGet
import presentation.components.ContactData

class DetailedContactViewModel(): ViewModel() {
    private val _showReminderDialog = MutableStateFlow(false)
    val showReminderDialog: StateFlow<Boolean> = _showReminderDialog

    private val _dialogText = MutableStateFlow("")
    val dialogText: StateFlow<String> = _dialogText

    private val _userDidSomething = MutableStateFlow(false)

    private val _didNothing = MutableStateFlow(0)
    val didNothing: StateFlow<Int> = _didNothing

    private val _wrongClick = MutableStateFlow(0)
    val wrongClick: StateFlow<Int> = _wrongClick

    private val _shouldNavigateAfterDialog = MutableStateFlow(false)
    val shouldNavigateAfterDialog: StateFlow<Boolean> = _shouldNavigateAfterDialog


    private var reminderJob: Job? = null

    fun startInactivityReminder() {
        _userDidSomething.value = false
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            while (_didNothing.value < 2) {
                delay(15_000)
                if (!_userDidSomething.value) {
                    _dialogText.value = getInactivityText()
                    _showReminderDialog.value = true
                }
            }
        }
    }

    private fun getInactivityText(): String {
        return when (_didNothing.updateAndGet { it + 1 }) {
            1 -> "מה יש לעשות?"
            2 -> "לחץ על מספר הפלאפון או על כפתור החיוג עם השפורפרת"
            else -> ""
        }
    }

    fun onWrongButtonClicked() {
        _userDidSomething.value = true
        if (_wrongClick.value < 2) {
            _dialogText.value = getWrongClickText()
            _showReminderDialog.value = true
        }
    }

    private fun getWrongClickText(): String {
        return when (_wrongClick.updateAndGet { it + 1 }) {
            1 -> "מה יש לעשות?"
            2 -> "לחץ על מספר הפלאפון או על כפתור החיוג עם השפורפרת"
            else -> ""
        }
    }

    fun onCorrectAction() {
        _userDidSomething.value = true
        reminderJob?.cancel()
    }

    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }
}