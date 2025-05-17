package presentation.appsDeviceScreen

import presentation.components.CountdownTimerUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.apps_page_second_assist
import dmt_proms.pass.generated.resources.calculator
import dmt_proms.pass.generated.resources.call_hana_cohen_pass
import dmt_proms.pass.generated.resources.call_to_hana_cohen_instruction_pass
import dmt_proms.pass.generated.resources.camera
import dmt_proms.pass.generated.resources.clock
import dmt_proms.pass.generated.resources.contacts
import dmt_proms.pass.generated.resources.documents
import dmt_proms.pass.generated.resources.email
import dmt_proms.pass.generated.resources.here_persons_number
import dmt_proms.pass.generated.resources.messages
import dmt_proms.pass.generated.resources.my_files
import dmt_proms.pass.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.purse
import dmt_proms.pass.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.pass.generated.resources.settings
import dmt_proms.pass.generated.resources.store
import dmt_proms.pass.generated.resources.weather
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.white_messages
import dmt_proms.pass.generated.resources.white_phone
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.example.hit.heal.core.presentation.Colors
import presentation.components.CountdownDialogHandler
import presentation.components.AppData
import presentation.components.AudioPlayer
import presentation.components.ContactData
import presentation.components.PlayAudioUseCase
import presentation.contatcts.ContactsScreen
import presentation.detailedContact.DetailedContactScreen
import presentation.nextQuestion.NextQuestionScreen

class AppDeviceViewModel( private val countdownDialogHandler: CountdownDialogHandler,
                          private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val items = listOf(
        AppData(
            imageRes = Res.drawable.calculator,
            circleColor = Colors.calculatorColor,
            label = Res.string.calculator
        ),
        AppData(
            imageRes = Res.drawable.settings,
            circleColor = Colors.settingsColor,
            label = Res.string.settings
        ),
        AppData(
            imageRes = Res.drawable.camera,
            circleColor = Colors.cameraColor,
            label = Res.string.camera
        ),
        AppData(
            imageRes = Res.drawable.email,
            circleColor = Colors.emailColor,
            label = Res.string.email
        ),
        AppData(
            imageRes = Res.drawable.store,
            circleColor = Colors.storeColor,
            label = Res.string.store
        ),
        AppData(
            imageRes = Res.drawable.clock,
            circleColor = Colors.clockColor,
            label = Res.string.clock
        ),
        AppData(
            imageRes = Res.drawable.contacts,
            circleColor = Colors.contactsColor,
            label = Res.string.contacts
        ),
        AppData(
            imageRes = Res.drawable.white_messages,
            circleColor = Colors.messagesColor,
            label = Res.string.messages
        ),
        AppData(
            imageRes = Res.drawable.purse,
            circleColor = Colors.purseColor,
            label = Res.string.purse
        ),
        AppData(
            imageRes = Res.drawable.weather,
            circleColor = Colors.weatherColor,
            label = Res.string.weather
        ),
        AppData(
            imageRes = Res.drawable.documents,
            circleColor = Colors.documentsColor,
            label = Res.string.my_files
        ),
        AppData(
            imageRes = Res.drawable.white_phone,
            circleColor = Colors.phoneColor,
            label = Res.string.phone
        )
    )


    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _isCloseIconDialog = MutableStateFlow(false)
    val isCloseIconDialog: StateFlow<Boolean> = _isCloseIconDialog

    private val _isNextScreen = MutableStateFlow(true)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private var didNothing = 0
    private var wrongApp = 0
    private var isSecondInstructions = false

    private var reminderJob: Job? = null

    val isPlaying = playAudioUseCase.isPlaying



    init {
        startDialogInstructions()
    }


    private fun startDialogInstructions() {
        getReminderDidNotingText()

        if (!isSecondInstructions) {
            _showUnderstandingDialog.value = true
        } else {
            _isCloseIconDialog.value = true
        }
    }


    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0

            while (isActive && didNothing <= 3) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedTime++

                if (elapsedTime >= 15) {
                    getReminderDidNotingText()
                    _isCloseIconDialog.value = true
                    elapsedTime = 0
                }
            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        isSecondInstructions = true
        didNothing--
        startDialogInstructions()
    }


    fun onAppClicked(app: AppData) {

        if (app.label == Res.string.contacts) {
            reminderJob?.cancel()
            _nextScreen.value = ContactsScreen()
            return
        }

        startCheckingIfUserDidSomething()
        wrongApp++

        _nextScreen.value = if (wrongApp == 3) {
            ContactsScreen()
        } else {
            WrongAppScreen(app)
        }
    }


    private fun getReminderDidNotingText() {
        when (didNothing++) {
            0 -> countdownDialogHandler.showCountdownDialog(
                duration = 2,
                audioText = Res.string.call_to_hana_cohen_instruction_pass to Res.string.call_hana_cohen_pass
            )

            1 -> countdownDialogHandler.showCountdownDialog(
                duration = 5,
                audioText = Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                duration = 3,
                audioText = Res.string.apps_page_second_assist to Res.string.search_contacts_list_in_the_phone_pass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    duration = 2,
                    audioText = Res.string.here_persons_number to Res.string.now_the_contacts_list_will_be_opened_pass
                )
                reminderJob?.cancel()
                _isNextScreen.value = false
                _nextScreen.value = ContactsScreen()
            }
        }
    }


    fun clearNextScreen() {
        _nextScreen.value = null
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        startCheckingIfUserDidSomething()
    }

}
