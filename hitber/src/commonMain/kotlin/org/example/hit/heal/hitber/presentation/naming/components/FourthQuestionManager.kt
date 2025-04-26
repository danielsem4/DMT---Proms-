package org.example.hit.heal.hitber.presentation.naming.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.utils.getNow
import org.jetbrains.compose.resources.DrawableResource

class FourthQuestionManager {

    private val _selectedCouple = MutableStateFlow<Pair<DrawableResource, DrawableResource>?>(null)
    val selectedCouple: StateFlow<Pair<DrawableResource, DrawableResource>?> = _selectedCouple.asStateFlow()

    fun setRandomCouple(imageCouples: List<Pair<DrawableResource, DrawableResource>>) {
        _selectedCouple.value = imageCouples.random()
    }

    fun fourthQuestionAnswer(
        answer1: String,
        answer2: String,
        correct1: String,
        correct2: String,
        cogData: CogData,
        updateCogData: (CogData) -> Unit
    ) {
        val now = getNow()
        val newFourthQuestionList = arrayListOf(
            MeasureObjectString(value = correct1, dateTime = now),
            MeasureObjectString(value = answer1, dateTime = now),
            MeasureObjectString(value = correct2, dateTime = now),
            MeasureObjectString(value = answer2, dateTime = now)
        )

        updateCogData(cogData.copy(fourthQuestion = newFourthQuestionList))
        println("New Fourth Question List: $newFourthQuestionList")
    }
}