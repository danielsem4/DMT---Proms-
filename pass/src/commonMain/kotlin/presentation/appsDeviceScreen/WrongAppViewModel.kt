package presentation.appsDeviceScreen

import CountdownTimerUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.going_back_to_apss_screen_pass
import dmt_proms.pass.generated.resources.return_button_on_top_left_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.wrong_app_second_assist
import dmt_proms.pass.generated.resources.wrong_app_thired_assist
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import presentation.appsDeviceScreen.components.CountdownDialogHandler
import presentation.components.AudioPlayer

class WrongAppViewModel : ViewModel() {

    private val countdownTimerUseCase = CountdownTimerUseCase(viewModelScope)

    private val countdownDialogHandler = CountdownDialogHandler(
        countdownTimerUseCase = countdownTimerUseCase)

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _backToApps = MutableStateFlow(false)
    val backToApps: StateFlow<Boolean> = _backToApps

    private var didNothing = 0
    private var didNothingSecondTime = 0
    private var isSecondTimeWrongApp = false

    private var reminderJob: Job? = null

    private val audioPlayer = AudioPlayer()

    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && didNothing < 3 && didNothingSecondTime < 3) {

                if (showDialog.value) {
                    delay(1_000)
                    continue
                }

                if (elapsedTime >= 15) {
                    getReminderDidNothingText()
                    elapsedTime = 0
                }

                delay(1_000)
                elapsedTime++
            }
        }
    }

    private fun getReminderDidNothingText() {
        val didNothingCount = if (isSecondTimeWrongApp) ++didNothing else ++didNothingSecondTime

        when (didNothingCount) {
            1 -> countdownDialogHandler.showCountdownDialog(5, Res.string.what_you_need_to_do  to Res.string.what_do_you_need_to_do_pass)
            2 -> countdownDialogHandler.showCountdownDialog(1, Res.string.wrong_app_second_assist to Res.string.return_button_on_top_left_pass)
            3 -> {
                countdownDialogHandler.showCountdownDialog(5, Res.string.wrong_app_thired_assist to Res.string.going_back_to_apss_screen_pass)
                _backToApps.value = true
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
        countdownDialogHandler.hideDialog()
    }

    fun setSecondTimeWrongApp() {
        reminderJob?.cancel()
        isSecondTimeWrongApp = true
    }

    fun setBackToApps() {
        _backToApps.value = false
    }
}