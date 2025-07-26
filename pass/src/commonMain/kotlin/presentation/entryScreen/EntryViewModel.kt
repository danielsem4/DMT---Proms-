package presentation.entryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import core.domain.use_case.PlayAudioUseCase

class EntryViewModel(private val playAudioUseCase: PlayAudioUseCase) : ViewModel() {

    val isPlaying = playAudioUseCase.isPlaying

    private val _isOverlayVisible = MutableStateFlow(true)
    val isOverlayVisible: StateFlow<Boolean> = _isOverlayVisible

    fun onPlayAudioRequested(audioText: String) {
        _isOverlayVisible.value = true

        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
            playAudioUseCase.isPlaying.collect { isPlaying ->
                if (!isPlaying) {
                    _isOverlayVisible.value = false
                }
            }
        }
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
        _isOverlayVisible.value = false
    }
}