package org.example.hit.heal.hitber.presentation.naming

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.presentation.naming.components.imageCouples
import org.example.hit.heal.hitber.utils.getNow
import org.jetbrains.compose.resources.DrawableResource

class FourthQuestionViewModel(): ViewModel() {

    var fourthQuestionAnswers: List<String> = emptyList()

    private val _selectedCouple = MutableStateFlow<Pair<DrawableResource, DrawableResource>?>(null)
    val selectedCouple: StateFlow<Pair<DrawableResource, DrawableResource>?> =
        _selectedCouple.asStateFlow()

    fun fourthQuestionAnswer(answer1: String, answer2: String, correct1: String, correct2: String) {
        fourthQuestionAnswers = listOf(correct1, answer1, correct2, answer2)
    }


    fun setRandomCouple() {
        _selectedCouple.value = imageCouples.random()
    }
}