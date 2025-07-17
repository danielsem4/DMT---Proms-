package org.example.hit.heal.hitber.presentation.understanding

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.presentation.understanding.model.audioList
import org.example.hit.heal.hitber.utils.isObjectInsideTargetArea
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

class SixthQuestionViewModel(private val playAudioUseCase: PlayAudioUseCase) : ViewModel() {

    private val _audioResourceId = MutableStateFlow<StringResource?>(null)
    val audioResourceId: StateFlow<StringResource?> get() = _audioResourceId.asStateFlow()

    val isPlaying = playAudioUseCase.isPlaying

    private val _selectedItem = MutableStateFlow<DrawableResource?>(null)
    val selectedItem: StateFlow<DrawableResource?> get() = _selectedItem.asStateFlow()

    private val _selectedNapkin = MutableStateFlow<Color?>(null)
    val selectedNapkin: StateFlow<Color?> get() = _selectedNapkin.asStateFlow()

    init {
        val randomAudio = audioList.random()
        _audioResourceId.value = randomAudio.audioResId
        _selectedItem.value = randomAudio.itemResId
        _selectedNapkin.value = randomAudio.napkinTint
    }

    fun onPlayAudio(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }
    }

    fun stopAudio() {
        playAudioUseCase.stopAudio()
    }

    private val _itemLastPositions = MutableStateFlow<Map<Int, Offset>>(emptyMap())
    val itemLastPositions: StateFlow<Map<Int, Offset>> = _itemLastPositions.asStateFlow()

    fun updateItemLastPosition(index: Int, position: Offset) {
        _itemLastPositions.value = _itemLastPositions.value.toMutableMap().apply {
            this[index] = position
        }
    }

    var isFridgeOpened: Boolean = false
    var isItemMovedCorrectly: Boolean = false
    var isNapkinPlacedCorrectly: Boolean = false

    fun setFridgeOpened() {
        isFridgeOpened = true
    }

    fun setItemMovedCorrectly() {
        isItemMovedCorrectly = true
    }

    fun evaluateAnswer(
        napkinResourceId: Color?,
        napkinPosition: Offset,
        napkinSize: Pair<Float, Float>,
        itemSize: Pair<Float, Float>,
        itemLastPositions: Map<Int, Offset>,
    ) {

        if (napkinResourceId != null) {
            val isItemInNapkin = itemLastPositions.values.any { itemPosition ->
                isObjectInsideTargetArea(
                    targetPosition = napkinPosition,
                    draggablePosition = itemPosition,
                    targetSize = napkinSize,
                    draggableSize = itemSize,
                    threshold = 65f,
                    isCircle = false
                )
            }
            if (isItemInNapkin) {
                isNapkinPlacedCorrectly = true
            }
        }
    }

}