package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.apps_page_second_assist
import dmt_proms.pass.generated.resources.going_back_to_apss_screen_pass
import dmt_proms.pass.generated.resources.here_persons_number
import dmt_proms.pass.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.pass.generated.resources.return_button_on_top_left_pass
import dmt_proms.pass.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.wrong_app_second_assist
import dmt_proms.pass.generated.resources.wrong_app_thired_assist
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import presentation.components.AudioPlayer

class WrongAppViewModel : ViewModel() {

    private val _dialogAudioText = MutableStateFlow<Pair<StringResource, StringResource>?>(null)
    val dialogAudioText: StateFlow<Pair<StringResource, StringResource>?> = _dialogAudioText

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private val _isCountdownActive = MutableStateFlow(false)
    val isCountdownActive: StateFlow<Boolean> = _isCountdownActive

    private val _backToApps = MutableStateFlow(false)
    val backToApps: StateFlow<Boolean> = _backToApps

    private var didNothing = 0
    private var didNothingSecondTime = 0
    private var isSecondTimeWrongApp = false
    private var isDialogActive = false

    private var reminderJob: Job? = null
    private var dialogJob: Job? = null

    private val audioPlayer = AudioPlayer()

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



    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && didNothing < 3 && didNothingSecondTime < 3) {

                if (isDialogActive) {
                    delay(1_000)
                    continue
                }
                if (elapsedTime >= 15) {

                    getReminderDidNotingText()
                    _showDialog.value = true

                    countDownDialog {
                        _showDialog.value = false
                        if (didNothing >= 4 || didNothingSecondTime >= 4) {
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

    fun hideReminderDialog() {
        _showDialog.value = false
    }

    fun setSecondTimeWrongApp() {
        reminderJob?.cancel()
        isSecondTimeWrongApp = true
    }

    private fun getReminderDidNotingText() {
        var didNothingCount = 0
        if (isSecondTimeWrongApp) {
            didNothing++
            didNothingCount = didNothing
        } else {
            didNothingSecondTime++
            didNothingCount = didNothingSecondTime
        }

        when (didNothingCount) {
            1 -> {
                _dialogAudioText.value =
                    Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
                _countdown.value = 5
            }

            2 -> {
                _dialogAudioText.value =
                    Res.string.wrong_app_second_assist to Res.string.return_button_on_top_left_pass
                _countdown.value = 1
            }

            3 -> {
                _dialogAudioText.value =
                    Res.string.wrong_app_thired_assist to Res.string.going_back_to_apss_screen_pass
                _countdown.value = 5
                _backToApps.value = true
                reminderJob?.cancel()
            }
        }
    }

    fun setBackToApps() {
        _backToApps.value = false
    }
}