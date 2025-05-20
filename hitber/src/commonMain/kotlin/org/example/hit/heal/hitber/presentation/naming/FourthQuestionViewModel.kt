package org.example.hit.heal.hitber.presentation.naming

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.presentation.naming.components.imageCouples
import org.jetbrains.compose.resources.DrawableResource

class FourthQuestionViewModel(): ViewModel() {

    var fourthQuestionAnswers: List<String> = emptyList()

    private val _selectedCouple = MutableStateFlow<Pair<DrawableResource, DrawableResource>?>(null)
    val selectedCouple: StateFlow<Pair<DrawableResource, DrawableResource>?> =
        _selectedCouple.asStateFlow()

    private val _answer1 = MutableStateFlow("")
    val answer1: StateFlow<String> = _answer1.asStateFlow()

    private val _answer2 = MutableStateFlow("")
    val answer2: StateFlow<String> = _answer2.asStateFlow()


    fun onAnswer1Changed(newValue: String) {
        _answer1.value = newValue
    }

    fun onAnswer2Changed(newValue: String) {
        _answer2.value = newValue
    }


    fun fourthQuestionAnswer(answer1: String, answer2: String, correct1: String, correct2: String) {
        fourthQuestionAnswers = listOf(correct1, answer1, correct2, answer2)
    }


    fun setRandomCouple() {
        _selectedCouple.value = imageCouples.random()
    }
}