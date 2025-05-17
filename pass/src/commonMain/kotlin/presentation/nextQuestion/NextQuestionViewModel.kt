package presentation.nextQuestion

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.components.AudioPlayer

class NextQuestionViewModel: ViewModel() {

    private val _time = MutableStateFlow(8)
    val time : StateFlow<Int> = _time

    private val _navigateToDialScreen = MutableStateFlow(false)
    val navigateToDialScreen: StateFlow<Boolean> = _navigateToDialScreen

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying


    private val audioPlayer = AudioPlayer()

    fun playAudio(audioText: String) {
        _isPlaying.value = true

        audioPlayer.play(audioText) {
            _isPlaying.value = false
        }
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