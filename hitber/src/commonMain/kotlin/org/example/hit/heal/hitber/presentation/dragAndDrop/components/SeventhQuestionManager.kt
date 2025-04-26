package org.example.hit.heal.hitber.presentation.dragAndDrop.components

import androidx.compose.ui.graphics.Color
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.data.model.SeventhQuestionType
import org.example.hit.heal.hitber.utils.getNow
import org.jetbrains.compose.resources.StringResource

class SeventhQuestionManager(
    private val instructions: List<Pair<StringResource, Color>>
) {

    fun setRandomInstructions(): Pair<StringResource, Color> {
        return instructions.random()
    }

    fun submitAnswer(
        isCorrect: Boolean,
        cogData: CogData,
    ): CogData {
        val time = getNow()
        val answerItem = SeventhQuestionType.SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(value = isCorrect, dateTime = time)
        )

        val currentList = cogData.seventhQuestion.toCollection(ArrayList())
        currentList.add(answerItem)
        return cogData.copy(seventhQuestion = currentList)
    }
}
