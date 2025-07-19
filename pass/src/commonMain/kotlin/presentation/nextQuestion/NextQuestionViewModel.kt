package presentation.nextQuestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NextQuestionViewModel(private val playAudioUseCase: PlayAudioUseCase) : ViewModel() {

    private val _time = MutableStateFlow(8)
    val time: StateFlow<Int> = _time

    private val _navigateToDialScreen = MutableStateFlow(false)
    val navigateToDialScreen: StateFlow<Boolean> = _navigateToDialScreen

    val isPlaying = playAudioUseCase.isPlaying

    fun onPlayAudioRequested(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }
    }

    // Starts the countdown from 8 seconds down to 0  and triggers navigation to the dial screen
    init {
        startCountdown()
    }

    private fun startCountdown() {
        _time.value = 8

        viewModelScope.launch {
            while (_time.value > 0) {
                delay(1000L)
                _time.value -= 1
            }
            _navigateToDialScreen.value = true
        }
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }
}