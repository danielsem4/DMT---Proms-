package core.domain.use_case

import core.utils.AudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayAudioUseCase(
    private val audioPlayer: AudioPlayer
) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    fun playAudio(audioText: String) {
        stopAudio()
        _isPlaying.value = true
        audioPlayer.play(audioText) {
            _isPlaying.value = false
        }
    }

    fun stopAudio() {
        audioPlayer.stop()
        _isPlaying.value = false
    }
}