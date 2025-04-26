package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.ui.geometry.Offset
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.data.model.SixthQuestionType
import org.example.hit.heal.hitber.utils.getNow


class SixthQuestionManager {

    fun setRandomAudio(audioList: List<AudioItem>, currentState: SixthQuestionState): SixthQuestionState {
        if (currentState.audioResourceId == null) {
            val randomAudio = audioList.random()
            return currentState.copy(
                audioResourceId = randomAudio.audioResId,
                selectedItem = randomAudio.itemResId,
                selectedNapkin = randomAudio.napkinColorResId
            )
        }
        return currentState
    }

    fun updateItemLastPosition(index: Int, position: Offset, currentState: SixthQuestionState): SixthQuestionState {
        val updatedPositions = currentState.itemLastPositions.toMutableMap().apply {
            this[index] = position
        }
        return currentState.copy(itemLastPositions = updatedPositions)
    }

    fun setFridgeOpened(currentState: SixthQuestionState): SixthQuestionState {
        return currentState.copy(isFridgeOpened = true)
    }

    fun setItemMovedCorrectly(currentState: SixthQuestionState): SixthQuestionState {
        return currentState.copy(isItemMovedCorrectly = true)
    }

    fun setNapkinPlacedCorrectly(currentState: SixthQuestionState): SixthQuestionState {
        return currentState.copy(isNapkinPlacedCorrectly = true)
    }

    fun sixthQuestionAnswer(
        cogData: CogData,
        currentState: SixthQuestionState
    ): CogData {
        val time = getNow()

        val answerItem = SixthQuestionType.SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(value = currentState.isFridgeOpened, dateTime = time),
            correctProductDragged = MeasureObjectBoolean(value = currentState.isItemMovedCorrectly, dateTime = time),
            placedOnCorrectNap = MeasureObjectBoolean(value = currentState.isNapkinPlacedCorrectly, dateTime = time)
        )

        val currentList = cogData.sixthQuestion.toCollection(ArrayList())
        currentList.add(answerItem)

        return cogData.copy(sixthQuestion = currentList)
    }
}
