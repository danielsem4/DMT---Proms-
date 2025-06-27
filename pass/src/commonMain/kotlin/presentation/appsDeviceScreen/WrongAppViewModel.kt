package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Resources.String.goingBackToAppsScreenPass
import org.example.hit.heal.core.presentation.Resources.String.returnButtonOnTopLeftPass
import org.example.hit.heal.core.presentation.Resources.String.whatDoYouNeedToDoPass
import org.example.hit.heal.core.presentation.Resources.String.whatYouNeedToDo
import org.example.hit.heal.core.presentation.Resources.String.wrongAppSecondAssist
import org.example.hit.heal.core.presentation.Resources.String.wrongAppThirdAssist
import presentation.components.CountdownDialogHandler

class WrongAppViewModel(private val countdownDialogHandler: CountdownDialogHandler,
                        private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _backToApps = MutableStateFlow(false)
    val backToApps: StateFlow<Boolean> = _backToApps

    private var didNothing = 0
    private var didNothingSecondTime = 0
    private var isSecondTimeWrongApp = false

    private var reminderJob: Job? = null
    private var elapsedSeconds = 0

    val isPlaying = playAudioUseCase.isPlaying

    fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            while (isActive && (didNothing < 3 || didNothingSecondTime < 3)) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedSeconds++

                if (elapsedSeconds >= 15) {
                    getReminderDidNothingText()
                    elapsedSeconds = 0
                }
            }
        }
    }

    private fun getReminderDidNothingText() {
        val didNothingCount = if (isSecondTimeWrongApp) ++didNothingSecondTime else ++didNothing

        when (didNothingCount) {
            1 -> countdownDialogHandler.showCountdownDialog(isPlayingFlow = isPlaying, whatYouNeedToDo  to whatDoYouNeedToDoPass)
            2 -> countdownDialogHandler.showCountdownDialog(isPlayingFlow = isPlaying, wrongAppSecondAssist to returnButtonOnTopLeftPass)
            3 -> {
                countdownDialogHandler.showCountdownDialog(isPlayingFlow = isPlaying, wrongAppThirdAssist to goingBackToAppsScreenPass)
                isSecondTimeWrongApp = true
                reminderJob?.cancel()
                _backToApps.value = true

            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
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