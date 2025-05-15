package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import presentation.components.AppData
import presentation.components.AudioPlayer

class AppDeviceViewModel : ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _dialogAudioText = MutableStateFlow<Pair<StringResource, StringResource>?>(null)
    val dialogAudioText: StateFlow<Pair<StringResource, StringResource>?> = _dialogAudioText

    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private val _isCloseIconDialog = MutableStateFlow(false)
    val isCloseIconDialog: StateFlow<Boolean> = _isCloseIconDialog

    private val _isCountdownActive = MutableStateFlow(false)
    val isCountdownActive: StateFlow<Boolean> = _isCountdownActive

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished

    private var didNothing = 0
    private var wrongApp = 0
    private var isCorrectApp = false
    private var isDialogActive = false
    private var isSecondInstructions = false

    private var dialogJob: Job? = null
    private var reminderJob: Job? = null

    private val audioPlayer = AudioPlayer()


    init {
        startDialogInstructions()
    }

    private fun countDownDialog(onCountdownFinished: () -> Unit) {
        isDialogActive = true
        _isCountdownActive.value = false

        dialogJob?.cancel()

        dialogJob = viewModelScope.launch {
            val audioDuration = 10 - _countdown.value - 1

            delay(audioDuration * 1000L)

            _isCountdownActive.value = true

            var remainingTime = _countdown.value

            while (remainingTime >= 0) {
                _countdown.value = remainingTime
                delay(1000L)
                remainingTime--
            }

            _isCountdownActive.value = false
            isDialogActive = false
            onCountdownFinished()
        }
    }


    private fun startDialogInstructions() {
        getReminderDidNotingText()
        _showDialog.value = true

        countDownDialog {
            _showDialog.value = false

            if (!isSecondInstructions) {
                _showUnderstandingDialog.value = true
                _isCloseIconDialog.value = true
            }
            else{
                didNothing++
            }
        }
    }


    fun startCheckingIfUserDidSomething() {

        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0

            while (isActive && didNothing <= 3) {

                if (isDialogActive) {
                    delay(1_000)
                    continue
                }

                if (elapsedTime >= 15) {
                    getReminderDidNotingText()

                    _isCloseIconDialog.value = true
                    _showDialog.value = true

                    countDownDialog {
                        _showDialog.value = false
                        if (didNothing >= 4) {
                            cancel()
                        }
                    }

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
        _showDialog.value = false
        didNothing++
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        _showDialog.value = true
        isSecondInstructions = true
        startDialogInstructions()
    }

    fun hideReminderDialog() {
        _showDialog.value = false
    }

    fun onAppClicked(app: AppData) {
        userDidSomething()

        if (app.label == "אנשי קשר") {
            isCorrectApp = true
        } else {
            wrongApp++
            if (wrongApp == 2) {
                _isFinished.value = true
            }
        }
    }

    private fun getReminderDidNotingText() {
        if (didNothing == 0) {
            _dialogAudioText.value =
                Res.string.call_to_hana_cohen_instruction_pass to Res.string.call_hana_cohen_pass
            _countdown.value = 2
            return
        }

        when (didNothing++) {
            1 -> {
                _dialogAudioText.value =
                    Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
                _countdown.value = 5
            }

            2 -> {
                _dialogAudioText.value =
                    Res.string.apps_page_second_assist to Res.string.search_contacts_list_in_the_phone_pass
                _countdown.value = 3
            }

            3 -> {
                _dialogAudioText.value =
                    Res.string.here_persons_number to Res.string.now_the_contacts_list_will_be_opened_pass
                _countdown.value = 2
                _isFinished.value = true
            }
        }
    }

    private fun userDidSomething() {
        _showDialog.value = false
        _showUnderstandingDialog.value = false
        reminderJob?.cancel()
    }
}
