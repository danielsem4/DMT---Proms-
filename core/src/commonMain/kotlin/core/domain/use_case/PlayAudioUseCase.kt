package core.domain.use_case

import core.utils.AudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayAudioUseCase(
    private val audioPlayer: AudioPlayer
) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    suspend fun playAudio(audioText: String) {
        stopAudio()
        _isPlaying.value = true
        try {
            _isPlaying.value = true
            audioPlayer.play(audioText) {
                _isPlaying.value = false
            }
        } catch (e: Exception) {
            _isPlaying.value = false
            println("Error playing audio: ${e.message}")
        }
    }

    fun stopAudio() {
        try {
            audioPlayer.stop()
        } catch (e: Exception) {
            println("PlayAudioUseCase Error stopping audio: ${e}")
        }
        _isPlaying.value = false
    }
}