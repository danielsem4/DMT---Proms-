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
    private val _wrongClick = MutableStateFlow(0)

    private val _shouldNavigateAfterDialog = MutableStateFlow(false)
    val shouldNavigateAfterDialog: StateFlow<Boolean> = _shouldNavigateAfterDialog


    private var reminderJob: Job? = null

    fun startReminderCountdown(wrongClick: Boolean) {
        if (wrongClick) {
            _userDidSomething.value = true
        }

        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && _didNothing.value + _wrongClick.value < 3) {

                if (_userDidSomething.value) {
                    _wrongClick.value++
                    _dialogText.value = getReminderText()
                    _showReminderDialog.value = true
                    elapsedTime = 0
                    _userDidSomething.value = false
                    continue
                }

                if (elapsedTime >= 15) {
                    _didNothing.value++
                    _dialogText.value = getReminderText()
                    _showReminderDialog.value = true
                    elapsedTime = 0
                }

                delay(1_000)
                elapsedTime++
            }
        }
    }

    private fun getReminderText(): String {
        val count = _didNothing.value + _wrongClick.value
        return when (count) {
            1 -> "מה יש לעשות?"
            2 -> "לחץ על מספר הפלאפון או על כפתור החיוג עם השפורפרת"
            3 -> {
                _shouldNavigateAfterDialog.value = true
                ""
            }
            else -> ""
        }
    }



    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }
}