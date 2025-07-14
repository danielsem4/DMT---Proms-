package core.domain.use_case

import core.utils.AudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class PlayAudioUseCase(
    private val audioPlayer: AudioPlayer
) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    open fun playAudio(audioText: String) {
        stopAudio()
        _isPlaying.value = true
        audioPlayer.play(audioText) {
            _isPlaying.value = false
        }
    }

    open fun stopAudio() {
        audioPlayer.stop()
        _isPlaying.value = false
    }
}