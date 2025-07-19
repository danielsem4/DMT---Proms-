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
import presentation.appsDeviceScreen.AppDeviceProgressCache.resetAppDevice
import presentation.appsDeviceScreen.AppProgressCache.didNothing
import presentation.appsDeviceScreen.AppProgressCache.didNothingSecondTime
import presentation.appsDeviceScreen.AppProgressCache.isSecondTimeWrongApp
import presentation.appsDeviceScreen.AppProgressCache.resetWrongApp
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

    val isPlaying = playAudioUseCase.isPlaying

    // Starts an inactivity timer – triggers a reminder after 15 seconds of no user interaction.
    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            if (didNothing < 3 || didNothingSecondTime < 3) {
                delay(15_000)
                getReminderDidNothingText()
            }
        }
    }

    // Display the dialogs with the correct message and audio
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
        }
    }

    // close dialog and restarts the 15-second timer
    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        startCheckingIfUserDidSomething()
    }


    // Marks that this is the second time the user selected the wrong app
    fun setSecondTimeWrongApp() {
        isSecondTimeWrongApp = true
    }

    fun setBackToApps() {
        _backToApps.value = false
    }

    fun stopAll() {
        playAudioUseCase.stopAudio()
        countdownDialogHandler.hideDialog()
        reminderJob?.cancel()
        reminderJob = null
    }

    fun resetAll() {
        resetAppDevice()
        resetWrongApp()
    }
}