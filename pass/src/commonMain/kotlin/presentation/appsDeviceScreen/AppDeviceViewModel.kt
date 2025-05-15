package presentation.appsDeviceScreen

import CountdownTimerUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.apps_page_second_assist
import dmt_proms.pass.generated.resources.call_hana_cohen_pass
import dmt_proms.pass.generated.resources.call_to_hana_cohen_instruction_pass
import dmt_proms.pass.generated.resources.here_persons_number
import dmt_proms.pass.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.pass.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.StringResource
import presentation.appsDeviceScreen.components.CountdownDialogHandler
import presentation.components.AppData
import presentation.components.AudioPlayer
import presentation.contatcts.ContactsScreen

class AppDeviceViewModel : ViewModel() {

    private val countdownTimerUseCase = CountdownTimerUseCase(viewModelScope)

    private val countdownDialogHandler =
        CountdownDialogHandler(countdownTimerUseCase = countdownTimerUseCase)

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _isCloseIconDialog = MutableStateFlow(false)
    val isCloseIconDialog: StateFlow<Boolean> = _isCloseIconDialog

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()


    private var didNothing = 0
    private var wrongApp = 0
    private var isCorrectApp = false
    private var isSecondInstructions = false

    private var reminderJob: Job? = null

    private val audioPlayer = AudioPlayer()


    init {
        startDialogInstructions()
    }


    private fun startDialogInstructions() {
        getReminderDidNotingText()

        if (!isSecondInstructions) {
            _showUnderstandingDialog.value = true
            _isCloseIconDialog.value = true
        } else {
            didNothing++
        }
    }


    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0

            while (isActive && didNothing <= 3) {

                if (showDialog.value || _showUnderstandingDialog.value) {
                    delay(1_000)
                    continue
                }

                if (elapsedTime >= 15) {
                    getReminderDidNotingText()
                    _isCloseIconDialog.value = true
                    elapsedTime = 0
                }

                delay(1_000)
                elapsedTime++
            }
        }
    }

    fun playAudio(audioText: String) {
        _isPlaying.value = true

        audioPlayer.play(audioText) {
            _isPlaying.value = false
        }
    }

    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
        hideReminderDialog()
        didNothing++
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        countdownDialogHandler.showDialog()
        isSecondInstructions = true
        startDialogInstructions()
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
    }

    fun onAppClicked(app: AppData) {
        userDidSomething()

        if (app.label == "אנשי קשר") {
            isCorrectApp = true
        } else {
            wrongApp++
            if (wrongApp == 3) {
                _nextScreen.value = ContactsScreen()
            } else {
                _nextScreen.value = WrongAppScreen(app)
            }
        }
    }


    private fun getReminderDidNotingText() {
        if (didNothing == 0) {
            countdownDialogHandler.showCountdownDialog(
                duration = 2,
                audioText = Res.string.call_to_hana_cohen_instruction_pass to Res.string.call_hana_cohen_pass
            )
            return
        }

        when (didNothing++) {
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
                _nextScreen.value = ContactsScreen()
            }
        }
    }


    fun clearNextScreen() {
        _nextScreen.value = null
    }

    private fun userDidSomething() {
        hideReminderDialog()
        _showUnderstandingDialog.value = false
        reminderJob?.cancel()
    }
}
