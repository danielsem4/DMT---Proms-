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
import utils.CountdownDialogHandler
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

    val isPlaying = playAudioUseCase.isPlaying

    private val _isScrolling = MutableStateFlow(false)

    private val _isScrolled = MutableStateFlow(false)
    val isScrolled: StateFlow<Boolean> = _isScrolled

    private var seconds = 15_000L

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allContacts: List<ContactData> = emptyList()

    private val _contactsList = MutableStateFlow<List<ContactData>>(emptyList())
    val contactsList: StateFlow<List<ContactData>> = _contactsList

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private val _isNextScreen = MutableStateFlow(false)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    // Loads contacts and initializes the contacts
    suspend fun loadContacts(phoneNumber: String) {
        val names = getStringArray(personNames).toList()
        allContacts = names
            .map { name -> ContactData(name, phoneNumber) }
            .sortedBy { it.name }
        _contactsList.value = allContacts
    }

    // Updates the search query and filters the contacts list accordingly
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

    // Starts an inactivity timer – triggers a reminder after 15 seconds of no user interaction.
    // If user is scrolling, increase the delay time to 25 seconds
    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {

            if (didNothing + wrongContact <= 4) {
                seconds = if (_isScrolling.value) 25_000L else 15_000L
                    delay(seconds)
                    didNothing++
                    showReminderText()
                    _isScrolling.value = false
            }
        }
    }

    // Checks if clicked correct contact, proceeds if correct, otherwise shows helper dialog
    fun onContactClicked(contact: ContactData) {
        if (contact.name == correctContact.name) {
            nextQuestion()
            return
        }
        wrongContact++
        showReminderText()
    }

    // Display the dialogs with the correct message and audio
    private fun showReminderText() {
        reminderJob?.cancel()

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
        _isNextScreen.value = true
        _nextScreen.value = DetailedContactScreen(correctContact)
    }

    fun onPlayAudioRequested(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }
    }

    // close dialog and restarts the 15 or 25 second timer
    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        startCheckingIfUserDidSomething()
    }

    fun clearNextScreen() {
        _nextScreen.value = null
    }

    // Sets the contact that the user is expected to find
    fun setCorrectContact(name: String, phoneNumber: String) {
        correctContact = ContactData(name = name, phoneNumber = phoneNumber)
    }

    // Marks scrolling as occurred once and restarts the inactivity timer with a 25 second.
    fun startScrolling(){
        _isScrolling.value = true
        _isScrolled.value = true
        startCheckingIfUserDidSomething()
    }

    fun stopAll() {
        playAudioUseCase.stopAudio()
        hideReminderDialog()
        reminderJob?.cancel()
        reminderJob = null
    }
}
