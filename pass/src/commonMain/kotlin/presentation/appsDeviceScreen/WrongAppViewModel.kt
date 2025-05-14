package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.apps_page_second_assist
import dmt_proms.pass.generated.resources.going_back_to_apss_screen_pass
import dmt_proms.pass.generated.resources.here_persons_number
import dmt_proms.pass.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.pass.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.wrong_app_second_assist
import kotlinx.coroutines.Job
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

    private val _didNothing = MutableStateFlow(0)
    val didNothing: StateFlow<Int> = _didNothing

    private val _didNothingSecondTime = MutableStateFlow(0)
    val didNothingSecondTime: StateFlow<Int> = _didNothingSecondTime

    private var isSecondTimeWrongApp = false

    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private val _backToApps = MutableStateFlow(false)
    val backToApps: StateFlow<Boolean> = _backToApps


    private var reminderJob: Job? = null

    private val audioPlayer = AudioPlayer()

    init {
        startCheckingIfUserDidSomething()
    }

    private fun startCheckingIfUserDidSomething() {
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0
            while (isActive && _didNothing.value < 3 && _didNothingSecondTime.value < 3) {

                if (elapsedTime >= 15) {
                    getReminderDidNotingText()
                    _showDialog.value = true
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
            _didNothing.value++
            didNothingCount = _didNothing.value
        } else {
            _didNothingSecondTime.value++
            didNothingCount = _didNothingSecondTime.value
        }

        when (didNothingCount) {
            1 -> {
                _dialogAudioText.value =
                    Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            }

            2 -> {
                _dialogAudioText.value =
                    Res.string.wrong_app_second_assist to Res.string.going_back_to_apss_screen_pass
            }

            3 -> {
                _backToApps.value = true
                reminderJob?.cancel()
            }
        }
    }

    fun setBackToApps() {
        _backToApps.value = false
    }
}