package org.example.hit.heal.hitber.presentation.dragAndDrop

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.instructions
import org.jetbrains.compose.resources.StringResource

class SeventhQuestionViewModel : ViewModel() {

    private val _instructionsResourceId = MutableStateFlow<StringResource?>(null)
    val instructionsResourceId: StateFlow<StringResource?> get() = _instructionsResourceId.asStateFlow()

    private val _targetCircleColor = MutableStateFlow<Color?>(null)
    val targetCircleColor: StateFlow<Color?> get() = _targetCircleColor


    init {
        setRandomInstructions()
    }


    var answer: Boolean = false

    private fun setRandomInstructions() {
        val (randomInstruction, color) = instructions.random()
        _instructionsResourceId.value = randomInstruction
        _targetCircleColor.value = color
    }

    fun seventhQuestionAnswer(isCorrect: Boolean) {
        answer = isCorrect
    }
}