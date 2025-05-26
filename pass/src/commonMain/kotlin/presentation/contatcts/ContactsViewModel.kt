package presentation.contatcts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.contacts_page_first_assist
import dmt_proms.pass.generated.resources.contacts_page_second_assist
import dmt_proms.pass.generated.resources.contacts_page_thired_assist
import dmt_proms.pass.generated.resources.person_names
import dmt_proms.pass.generated.resources.search_at_latter_h_pass
import dmt_proms.pass.generated.resources.search_for_hana_choen_in_contacts_pass
import dmt_proms.pass.generated.resources.witch_contact_are_we_looking_for_pass
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import presentation.components.ContactData
import presentation.components.CountdownDialogHandler
import presentation.components.PlayAudioUseCase
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


    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0

            while (isActive && didNothing + wrongContact <= 4) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedTime++

                if (elapsedTime >= if (_isScrolling.value) 25 else 15) {
                    didNothing++
                    getReminderText()
                    elapsedTime = 0
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
                Res.string.contacts_page_first_assist to Res.string.witch_contact_are_we_looking_for_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                Res.string.contacts_page_second_assist to Res.string.search_for_hana_choen_in_contacts_pass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying, Res.string.contacts_page_thired_assist to Res.string.search_at_latter_h_pass
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
        startCheckingIfUserDidSomething()
    }

}
