package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.apps_page_second_assist
import dmt_proms.pass.generated.resources.here_persons_number
import dmt_proms.pass.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.pass.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.StringResource
import presentation.components.AppData
import presentation.components.AudioPlayer

class AppDeviceViewModel : ViewModel() {

    // --- Dialog State ---
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _showReminderDialog = MutableStateFlow(false)
    val showReminderDialog: StateFlow<Boolean> = _showReminderDialog

    private val _dialogAudioText = MutableStateFlow<Pair<StringResource, StringResource>?>(null)
    val dialogAudioText: StateFlow<Pair<StringResource, StringResource>?> = _dialogAudioText

    private val _isSecondInstructions = MutableStateFlow(false)
    val isSecondInstructions: StateFlow<Boolean> = _isSecondInstructions

    // --- Countdown ---
    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished

    private var didNothing = 0
    private var wrongApp = 0
    private var isCorrectApp = false



    // --- Internal ---

    private var dialogJob: Job? = null
    private var reminderJob: Job? = null

    // --- Public API ---

    private val audioPlayer = AudioPlayer()

    init {
        startDialogInstructions()
        startCheckingIfUserDidSomething()
    }


    private fun startDialogInstructions() {
        dialogJob?.cancel()
        _showDialog.value = true

        dialogJob = viewModelScope.launch {
            var remainingTime = 10

            while (remainingTime > 0) {
                if (remainingTime <= 3) {
                    _countdown.value = remainingTime - 1
                }
                delay(1000)
                remainingTime--
            }

            _showDialog.value = false

            if (!_isSecondInstructions.value) {
                _showUnderstandingDialog.value = true
            }
        }
    }

    private fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && didNothing < 3) {

                if (elapsedTime >= 15) {
                    getReminderDidNotingText()
                    _showReminderDialog.value = true
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
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        _showDialog.value = true
        _isSecondInstructions.value = true
        startDialogInstructions()
    }

    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }

    fun onAppClicked(app: AppData) {
        userDidSomething()
        if (app.label == "אנשי קשר") {
            isCorrectApp = true
            reminderJob?.cancel()
        } else {
            wrongApp++
            if (wrongApp == 3) {
                reminderJob?.cancel()
                _isFinished.value = true
            }
        }
    }

    private fun getReminderDidNotingText() {
        when (didNothing++) {
            1 -> {
                _dialogAudioText.value =
                    Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            }

            2 -> {
                _dialogAudioText.value =
                    Res.string.apps_page_second_assist to Res.string.search_contacts_list_in_the_phone_pass
            }

            3 -> {
                _dialogAudioText.value =
                    Res.string.here_persons_number to Res.string.now_the_contacts_list_will_be_opened_pass
                _isFinished.value = true
            }
        }
    }

    fun userDidSomething(){
        _showDialog.value = false
        _showUnderstandingDialog.value = false
        _showReminderDialog.value = false
    }

}
