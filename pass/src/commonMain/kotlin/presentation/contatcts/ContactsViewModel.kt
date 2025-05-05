package presentation.contatcts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import presentation.contatcts.components.allContacts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import presentation.appsDeviceScreen.AppDeviceViewModel.NavigationEvent
import presentation.appsDeviceScreen.ScreenState
import presentation.components.ContactData

class ContactsViewModel : ViewModel()  {


    private val _contacts = MutableStateFlow(allContacts)
    val contacts: StateFlow<List<ContactData>> = _contacts

    private val _showReminderDialog = MutableStateFlow(false)
    val showReminderDialog: StateFlow<Boolean> = _showReminderDialog

    private val _dialogText = MutableStateFlow("")
    val dialogText: StateFlow<String> = _dialogText

    private val _userDidSomething = MutableStateFlow(false)

    private val _didNothing = MutableStateFlow(0)
    private val _wrongContact = MutableStateFlow(0)

    private val _shouldNavigateAfterDialog = MutableStateFlow(false)
    val shouldNavigateAfterDialog: StateFlow<Boolean> = _shouldNavigateAfterDialog


    private var reminderJob: Job? = null

    var searchQuery = mutableStateOf("")
        private set

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        _contacts.value = if (query.isBlank()) {
            allContacts
        } else {
            allContacts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    fun startReminderCountdownForDidNothing(contactData: ContactData) {
        _userDidSomething.value = false
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            while (_didNothing.value < 3) {
                delay(15_000)
                if (!_userDidSomething.value) {
                    _dialogText.value = getDidNothingReminderText(contactData)
                    _showReminderDialog.value = true
                }
            }
        }
    }

    fun startReminderCountdownForWrongContact(contactData: ContactData) {
            if (_wrongContact.value < 3) {
                    _dialogText.value = getWrongContactReminderText(contactData)
                    _showReminderDialog.value = true
            }
        }


    private fun getDidNothingReminderText(contactData: ContactData): String {
        val count = _didNothing.updateAndGet { it + 1 }
        return when (count) {
            1 -> "תחפש את ${contactData.name} באנשי קשר"
            2 -> "תחפש באות ${contactData.name.first()}"
            3 -> {
                _shouldNavigateAfterDialog.value = true
                "${contactData.name} תחפש את"
            }
            else -> ""
        }
    }

    private fun getWrongContactReminderText(contactData: ContactData): String {
        _userDidSomething.value = true
        val count = _wrongContact.updateAndGet { it + 1 }
        return when (count) {
            1 -> "תחפש את ${contactData.name} באנשי קשר"
            2 -> "תחפש באות ${contactData.name.first()}"
            3 -> {
                _shouldNavigateAfterDialog.value = true
                "${contactData.name} תחפש את"
            }
            else -> ""
        }
    }


    fun startRepeatCountdown(contactData: ContactData) {
        reminderJob?.cancel()
        _userDidSomething.value = false

        reminderJob = viewModelScope.launch {
            while (_didNothing.value < 3) {
                delay(25_000)

                if (!_userDidSomething.value) {
                    _dialogText.value = getDidNothingReminderText(contactData)
                    _showReminderDialog.value = true
                    _didNothing.value += 1
                } else {
                    _userDidSomething.value = false
                }
            }
        }
    }
    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }
}