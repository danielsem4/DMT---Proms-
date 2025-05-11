package presentation.contatcts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.person_names
import dmt_proms.pass.generated.resources.phone_number
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import presentation.components.ContactData

class ContactsViewModel : ViewModel() {


    private val _showReminderDialog = MutableStateFlow(false)
    val showReminderDialog: StateFlow<Boolean> = _showReminderDialog

    private val _dialogText = MutableStateFlow("")
    val dialogText: StateFlow<String> = _dialogText

    private val _userDidSomething = MutableStateFlow(false)

    private val _didNothing = MutableStateFlow(0)
    private val _wrongContact = MutableStateFlow(0)

    private val _shouldNavigateAfterDialog = MutableStateFlow(false)
    val shouldNavigateAfterDialog: StateFlow<Boolean> = _shouldNavigateAfterDialog

    private var scrollJob: Job? = null
    private var reminderJob: Job? = null

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private var allContacts: List<ContactData> = emptyList()

    private val _contactsList = MutableStateFlow<List<ContactData>>(emptyList())
    val contactsList: StateFlow<List<ContactData>> = _contactsList

    suspend fun loadContacts(phoneNumber: String) {
        val names = getStringArray(Res.array.person_names).toList()
        allContacts = names
            .map { name -> ContactData(name, phoneNumber) }
            .sortedBy { it.name }
        _contactsList.value = allContacts
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        _contactsList.value = if (query.isBlank()) {
            allContacts
        } else {
            allContacts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }



    fun startReminderCountdown(contactData: ContactData, wrongContact: Boolean) {
        if (wrongContact) {
            _userDidSomething.value = true
        }

        if (scrollJob?.isActive == true) return

        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && _didNothing.value + _wrongContact.value < 3) {

                if (_userDidSomething.value) {
                    _wrongContact.value++
                    _dialogText.value = getReminderText(contactData)
                    _showReminderDialog.value = true
                    elapsedTime = 0
                    _userDidSomething.value = false
                    continue
                }

                if (elapsedTime >= 15) {
                    _didNothing.value++
                    _dialogText.value = getReminderText(contactData)
                    _showReminderDialog.value = true
                    elapsedTime = 0
                }

                delay(1_000)
                elapsedTime++
            }
        }
    }


    private fun getReminderText(contactData: ContactData): String {
        val count = _didNothing.value + _wrongContact.value
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

    fun startScrollCountdown(contactData: ContactData) {
        _userDidSomething.value = true

        reminderJob?.cancel()
        scrollJob?.cancel()

        scrollJob = viewModelScope.launch {
            var elapsedTime = 0
            var dialogShown = false

            while (isActive && _didNothing.value + _wrongContact.value < 3) {
                delay(1_000)
                elapsedTime++

                if (_userDidSomething.value) {
                    if (!dialogShown) {
                        _dialogText.value = getReminderText(contactData)
                        _showReminderDialog.value = true
                        dialogShown = true
                    }
                    _userDidSomething.value = false
                }

                if (elapsedTime >= 25 && !dialogShown) {
                    _dialogText.value = getReminderText(contactData)
                    _showReminderDialog.value = true
                    _didNothing.value++
                    dialogShown = true
                }
            }
        }
    }

    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }
}