package org.example.hit.heal.hitber.presentation.shapes.components

import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.MeasureObjectInt
import org.example.hit.heal.hitber.data.model.SecondQuestionItem
import org.example.hit.heal.hitber.data.model.SelectedShapesStringList
import org.example.hit.heal.hitber.utils.getNow

class SecondQuestionManager(private val shapesSelectionManager: ShapesSelectionManager) {

    private fun submitAnswer(
        questionNumber: Int,
        shapeNames: List<String>,
        correctShapesCount: Int,
        cogData: CogData,
        updateCogData: (CogData) -> Unit
    ) {
        val selected = SelectedShapesStringList(
            value = shapeNames,
            dateTime = getNow()
        )

        val newAnswer = SecondQuestionItem(
            selectedShapes = selected,
            wrongShapes = MeasureObjectInt(
                value = 5 - correctShapesCount,
                dateTime = getNow()
            )
        )

        when (questionNumber) {
            2 -> {
                val updatedList = cogData.secondQuestion + newAnswer
                updateCogData(cogData.copy(secondQuestion = ArrayList(updatedList)))
            }

            9 -> {
                val updatedList = cogData.ninthQuestion + newAnswer
                updateCogData(cogData.copy(ninthQuestion = ArrayList(updatedList)))
            }
        }
    }


    fun handleNextClick(
        question: Int,
        shapeNames: List<String>,
        onShowDialog: () -> Unit,
        onNavigateNext: (Boolean) -> Unit,
        cogData: CogData,
        updateCogData: (CogData) -> Unit,
        state: ShapesSelectionState,
        updateState: (ShapesSelectionState) -> Unit
    ) {
        shapesSelectionManager.calculateCorrectShapesCount(state)
        val updatedState = shapesSelectionManager.updateTask(state)
        updateState(updatedState)

        submitAnswer(
            questionNumber = question,
            shapeNames = shapeNames,
            correctShapesCount = shapesSelectionManager.correctShapesCount,
            cogData = cogData,
            updateCogData = updateCogData
        )

        val resetSelectedShapesState = shapesSelectionManager.resetSelectedShapes(updatedState)
        updateState(resetSelectedShapesState)

        if (resetSelectedShapesState.attempt <= 3) {
            onShowDialog()
        } else {
            val resetSecondQuestionState = shapesSelectionManager.resetSecondQuestion(resetSelectedShapesState)
            updateState(resetSecondQuestionState)
            onNavigateNext(question == 2)
        }
    }


}
