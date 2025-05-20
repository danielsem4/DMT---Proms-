package org.example.hit.heal.hitber.presentation.dragAndDrop

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.instructions
import org.jetbrains.compose.resources.StringResource

class SeventhQuestionViewModel : ViewModel() {

    private val _instructionsResourceId = MutableStateFlow<StringResource?>(null)
    val instructionsResourceId: StateFlow<StringResource?> get() = _instructionsResourceId.asStateFlow()

    private val _targetCircleColor = MutableStateFlow<Color?>(null)
    val targetCircleColor: StateFlow<Color?> get() = _targetCircleColor

    private val _isUploadFinished = MutableStateFlow(false)
    val isUploadFinished: StateFlow<Boolean> get() = _isUploadFinished

    var answer: Boolean = false

    fun setRandomInstructions() {
        val (randomInstruction, color) = instructions.random()
        _instructionsResourceId.value = randomInstruction
        _targetCircleColor.value = color
    }

    fun setIsUploadFinished(){
        _isUploadFinished.value = true
    }

    fun seventhQuestionAnswer(isCorrect: Boolean) {
        answer = isCorrect
    }
}