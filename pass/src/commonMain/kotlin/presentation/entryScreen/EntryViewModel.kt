package presentation.entryScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import presentation.components.AudioPlayer

class EntryViewModel: ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isOverlayVisible = MutableStateFlow(true)
    val isOverlayVisible: StateFlow<Boolean> = _isOverlayVisible

    private val audioPlayer = AudioPlayer()

    fun playAudio(audioText: String) {
        _isPlaying.value = true
        _isOverlayVisible.value = true

            audioPlayer.play(audioText) {
                _isPlaying.value = false
                _isOverlayVisible.value = false
            }
    }
}