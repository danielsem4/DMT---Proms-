package presentation.nextQuestion

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NextQuestionViewModel( private val playAudioUseCase: PlayAudioUseCase): ViewModel() {

    private val _time = MutableStateFlow(8)
    val time : StateFlow<Int> = _time

    private val _navigateToDialScreen = MutableStateFlow(false)
    val navigateToDialScreen: StateFlow<Boolean> = _navigateToDialScreen

    val isPlaying = playAudioUseCase.isPlaying

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    init {
        viewModelScope.launch {
            while (_time.value > 0) {
                delay(1000L)
                _time.value -= 1
            }
            _navigateToDialScreen.value = true
        }
    }
}