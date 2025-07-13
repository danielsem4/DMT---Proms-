package presentation.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.StringResource

class CountdownDialogHandler(
    private val countdownTimerUseCase: CountdownTimerUseCase
) {

    private val _dialogAudioText = MutableStateFlow<Pair<StringResource, StringResource>?>(null)
    val dialogAudioText: StateFlow<Pair<StringResource, StringResource>?> = _dialogAudioText

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private val _isCountdownActive = MutableStateFlow(false)
    val isCountdownActive: StateFlow<Boolean> = _isCountdownActive

    fun showCountdownDialog( isPlayingFlow: StateFlow<Boolean>, audioText: Pair<StringResource, StringResource>) {
        _dialogAudioText.value = audioText
        _showDialog.value = true
        _isCountdownActive.value = false

        countdownTimerUseCase.execute(
            onCountdownTick = { remainingTime ->
                _countdown.value = remainingTime
                _isCountdownActive.value = true
            },
            onCountdownFinished = {
                _showDialog.value = false
            },
            isPlaying = isPlayingFlow
        )
    }

    fun hideDialog() {
        _showDialog.value = false
        countdownTimerUseCase.cancel()
    }

    fun showDialog() {
        _showDialog.value = true
    }
}
