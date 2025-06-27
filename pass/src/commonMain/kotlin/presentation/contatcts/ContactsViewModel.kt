package presentation.contatcts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.String.contactsPageFirstAssist
import org.example.hit.heal.core.presentation.Resources.String.contactsPageSecondAssist
import org.example.hit.heal.core.presentation.Resources.String.contactsPageThirdAssist
import org.example.hit.heal.core.presentation.Resources.String.personNames
import org.example.hit.heal.core.presentation.Resources.String.searchAtLatterHPass
import org.example.hit.heal.core.presentation.Resources.String.searchForHanaChoenInContactsPass
import org.example.hit.heal.core.presentation.Resources.String.witchContactAreWeLookingForPass
import org.jetbrains.compose.resources.getStringArray
import presentation.components.ContactData
import presentation.components.CountdownDialogHandler
import presentation.detailedContact.DetailedContactScreen

class ContactsViewModel(private val countdownDialogHandler: CountdownDialogHandler,
                        private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private var didNothing = 0
    private var wrongContact = 0
    private var correctContact = ContactData("", "")

    private var reminderJob: Job? = null
    private var elapsedSeconds = 0

    val isPlaying = playAudioUseCase.isPlaying

    private val _isScrolling = MutableStateFlow(false)

    private val _isScrolled = MutableStateFlow(false)
    val isScrolled: StateFlow<Boolean> = _isScrolled


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allContacts: List<ContactData> = emptyList()

    private val _contactsList = MutableStateFlow<List<ContactData>>(emptyList())
    val contactsList: StateFlow<List<ContactData>> = _contactsList

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private val _isNextScreen = MutableStateFlow(false)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    suspend fun loadContacts(phoneNumber: String) {
        val names = getStringArray(personNames).toList()
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


    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {

            while (isActive && didNothing + wrongContact <= 4) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedSeconds++

                if (elapsedSeconds >= if (_isScrolling.value) 25 else 15) {
                    didNothing++
                    getReminderText()
                    elapsedSeconds = 0
                    _isScrolling.value = false
                }

            }
        }
    }

    fun onContactClicked(contact: ContactData) {
        if (contact.name == correctContact.name) {
            nextQuestion()
            return
        }
        wrongContact++
        getReminderText()
    }

    private fun getReminderText() {
        val count = didNothing + wrongContact
        return when (count) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                contactsPageFirstAssist to witchContactAreWeLookingForPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                contactsPageSecondAssist to searchForHanaChoenInContactsPass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying, contactsPageThirdAssist to searchAtLatterHPass
                )
            }

            else -> {
                nextQuestion()
            }
        }
    }

    private fun nextQuestion(){
        reminderJob?.cancel()
        _isNextScreen.value = true
        _nextScreen.value = DetailedContactScreen(correctContact)
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        startCheckingIfUserDidSomething()
    }

    fun clearNextScreen() {
        _nextScreen.value = null
    }

    fun setCorrectContact(name: String, phoneNumber: String) {
        correctContact = ContactData(name = name, phoneNumber = phoneNumber)
    }

    fun startScrolling(){
        _isScrolling.value = true
        _isScrolled.value = true
        elapsedSeconds = 0
    }
}
