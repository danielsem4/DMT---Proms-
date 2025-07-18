package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.String.goingBackToAppsScreenPass
import org.example.hit.heal.core.presentation.Resources.String.returnButtonOnTopLeftPass
import org.example.hit.heal.core.presentation.Resources.String.whatDoYouNeedToDoPass
import org.example.hit.heal.core.presentation.Resources.String.whatYouNeedToDo
import org.example.hit.heal.core.presentation.Resources.String.wrongAppSecondAssist
import org.example.hit.heal.core.presentation.Resources.String.wrongAppThirdAssist
import utils.CountdownDialogHandler

class WrongAppViewModel(
    private val countdownDialogHandler: CountdownDialogHandler,
    private val playAudioUseCase: PlayAudioUseCase,
) : ViewModel() {

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _backToApps = MutableStateFlow(false)
    val backToApps: StateFlow<Boolean> = _backToApps

    private var reminderJob: Job? = null

    private var didNothing = 0
    private var didNothingSecondTime = 0
    private var isSecondTimeWrongApp = false

    val isPlaying = playAudioUseCase.isPlaying

    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            if (didNothing < 3 || didNothingSecondTime < 3) {
                delay(15_000)
                getReminderDidNothingText()
            }
        }
    }


    private fun getReminderDidNothingText() {
        val didNothingCount = if (isSecondTimeWrongApp) ++didNothingSecondTime else ++didNothing

        when (didNothingCount) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                wrongAppSecondAssist to returnButtonOnTopLeftPass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    wrongAppThirdAssist to goingBackToAppsScreenPass
                )
                isSecondTimeWrongApp = true
                _backToApps.value = true
            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        startCheckingIfUserDidSomething()
    }

    fun setSecondTimeWrongApp() {
        isSecondTimeWrongApp = true
    }

    fun setBackToApps() {
        _backToApps.value = false
    }

    fun stopAll() {
        println("didNothing: $didNothing")
        println("didNothingSecondTime: $didNothingSecondTime")
        playAudioUseCase.stopAudio()
        countdownDialogHandler.hideDialog()
        reminderJob?.cancel()
        reminderJob = null
    }

    fun resetAppProgress() {
        didNothing = 0
        didNothingSecondTime = 0
        isSecondTimeWrongApp = false
    }

}