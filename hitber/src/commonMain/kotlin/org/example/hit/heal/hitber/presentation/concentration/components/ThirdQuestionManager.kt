package org.example.hit.heal.hitber.presentation.concentration.components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.data.model.MeasureObjectInt
import org.example.hit.heal.hitber.data.model.ThirdQuestionItem
import org.example.hit.heal.hitber.utils.getNow

class ThirdQuestionManager{

    private var numberAppearedAt: Long = 0L

    fun startButtonSetVisible(state: ThirdQuestionState, visible: Boolean): ThirdQuestionState {
        return state.copy(startButtonIsVisible = visible)
    }

    fun recordAnswer(state: ThirdQuestionState, answer: Int, cogData: MutableStateFlow<CogData>): ThirdQuestionState {
        if (state.isNumberClickable) {
            val reactionTimeMillis = Clock.System.now().toEpochMilliseconds() - numberAppearedAt
            val reactionTimeSeconds = (reactionTimeMillis / 1000).toInt()

            val answerItem = ThirdQuestionItem(
                number = MeasureObjectInt(value = answer, dateTime = getNow()),
                time = MeasureObjectInt(value = reactionTimeSeconds, dateTime = getNow()),
                isPressed = MeasureObjectBoolean(value = true, dateTime = getNow())
            )

            val updatedList = ArrayList(cogData.value.thirdQuestion).apply {
                add(answerItem)
            }
            cogData.value = cogData.value.copy(thirdQuestion = updatedList)

            return state.copy(isNumberClickable = false)
        }
        return state
    }

    fun getNewRandomNumber(): Int {
        numberAppearedAt = Clock.System.now().toEpochMilliseconds()
        return (0..9).random()
    }
}