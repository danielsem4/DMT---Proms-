package presentation.appsDeviceScreen.components

import CountdownTimerUseCase
import kotlinx.coroutines.CoroutineScope
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

    fun showCountdownDialog(duration: Int, audioText: Pair<StringResource, StringResource>) {
        _dialogAudioText.value = audioText
        _showDialog.value = true
        _isCountdownActive.value = false
        _countdown.value = duration

        countdownTimerUseCase.execute(
            countdownStart = duration,
            onCountdownTick = { remainingTime ->
                _countdown.value = remainingTime
                _isCountdownActive.value = true
            },
            onCountdownFinished = {
                _showDialog.value = false
            }
        )
    }

    fun hideDialog() {
        _showDialog.value = false
    }

    fun showDialog() {
        _showDialog.value = true
    }
}
