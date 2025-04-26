package org.example.hit.heal.hitber.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.data.model.EighthQuestionItem
import org.example.hit.heal.hitber.data.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.data.model.MeasureObjectDouble
import org.example.hit.heal.hitber.data.model.MeasureObjectString
import org.example.hit.heal.hitber.data.model.TenthQuestionType
import org.example.hit.heal.hitber.presentation.concentration.components.ThirdQuestionManager
import org.example.hit.heal.hitber.presentation.concentration.components.ThirdQuestionState
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.SeventhQuestionManager
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.SeventhQuestionState
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.instructions
import org.example.hit.heal.hitber.presentation.naming.components.FourthQuestionManager
import org.example.hit.heal.hitber.presentation.naming.components.imageCouples
import org.example.hit.heal.hitber.presentation.shapes.components.SecondQuestionManager
import org.example.hit.heal.hitber.presentation.shapes.components.Shape
import org.example.hit.heal.hitber.presentation.shapes.components.ShapesSelectionManager
import org.example.hit.heal.hitber.presentation.shapes.components.ShapesSelectionState
import org.example.hit.heal.hitber.presentation.shapes.components.shapeList
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.DropDownItem
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.FirstQuestionManager
import org.example.hit.heal.hitber.presentation.understanding.components.AudioItem
import org.example.hit.heal.hitber.presentation.understanding.components.SixthQuestionManager
import org.example.hit.heal.hitber.presentation.understanding.components.SixthQuestionState
import org.example.hit.heal.hitber.presentation.understanding.components.audioList
import org.example.hit.heal.hitber.presentation.writing.components.EighthQuestionManager
import org.example.hit.heal.hitber.presentation.writing.components.EighthQuestionState
import org.example.hit.heal.hitber.presentation.writing.components.sentencesResourceId
import org.example.hit.heal.hitber.presentation.writing.components.slotsList
import org.example.hit.heal.hitber.utils.getNow

class ActivityViewModel : ViewModel() {

    private val _cogData = MutableStateFlow(CogData())
    val cogData: StateFlow<CogData> get() = _cogData

    //Time and Place Question (1/10)
    private val _allAnswersFinished = MutableStateFlow(false)
    val allAnswersFinished: StateFlow<Boolean> get() = _allAnswersFinished

    private val firstQuestionManager = FirstQuestionManager()

    fun firstQuestionAnswer(field: String, answer: DropDownItem) {
        _cogData.value = firstQuestionManager.updateAnswer(field, answer, _cogData.value)
        updateAllAnswersFinished()
        println("Answer: '${_cogData.value.firstQuestion}")
    }

    private fun updateAllAnswersFinished() {
        _allAnswersFinished.value = firstQuestionManager.allAnswersFinished(_cogData.value)
    }

    //Shape Question (2/10)
    private val selectionManager = ShapesSelectionManager()
    private val secondQuestionManager = SecondQuestionManager(selectionManager)

    private val _secondQuestionState = MutableStateFlow(ShapesSelectionState(shapeList = shapeList))
    val secondQuestionState: StateFlow<ShapesSelectionState> = _secondQuestionState.asStateFlow()

    fun setRandomShapeSet() {
        _secondQuestionState.value = selectionManager.setRandomShapeSet(_secondQuestionState.value) }

    fun setSelectedShapes(shape: Shape) {
        _secondQuestionState.value = selectionManager.setSelectedShapes(_secondQuestionState.value, shape)
    }

    fun onNextClicked(
        question: Int,
        shapeNames: List<String>,
        onShowDialog: () -> Unit,
        onNavigateNext: (Boolean) -> Unit
    ) {
        secondQuestionManager.handleNextClick(
            question = question,
            shapeNames = shapeNames,
            onShowDialog = onShowDialog,
            onNavigateNext = onNavigateNext,
            cogData = _cogData.value,
            updateCogData = { newCogData -> _cogData.value = newCogData },
            state = _secondQuestionState.value,
            updateState = { newState -> _secondQuestionState.value = newState }
        )
        println("Answer: '${_cogData.value.secondQuestion}")
    }

    //concentration Question (3/10)
    private val thirdQuestionManager = ThirdQuestionManager()

    private val _thirdQuestionState = MutableStateFlow(ThirdQuestionState())
    val thirdQuestionState = _thirdQuestionState.asStateFlow()

    fun setThirdQuestionStartButtonVisible(visible: Boolean) {
        _thirdQuestionState.value = thirdQuestionManager.startButtonSetVisible(_thirdQuestionState.value, visible)
    }

    fun onThirdQuestionAnswer(answer: Int) {
        _thirdQuestionState.value = thirdQuestionManager.recordAnswer(_thirdQuestionState.value, answer, _cogData)
        println("Answer: '${_cogData.value.thirdQuestion}")

    }
    private var elapsedTime = 0.0

    fun startThirdQuestion() {
        viewModelScope.launch {
            while (elapsedTime < 60) {
                val randomNumber = thirdQuestionManager.getNewRandomNumber()

                _thirdQuestionState.value = _thirdQuestionState.value.copy(
                    number = randomNumber,
                    isNumberClickable = true
                )

                delay(2500)
                elapsedTime += 2.5
            }
            _thirdQuestionState.value = _thirdQuestionState.value.copy(isFinished = true)
        }
    }


    //naming Question (4/10)
    private val fourthQuestionManager = FourthQuestionManager()
    val selectedCouple = fourthQuestionManager.selectedCouple

    fun fourthQuestionAnswer(answer1: String, answer2: String, correct1: String, correct2: String) {
        fourthQuestionManager.fourthQuestionAnswer(
            answer1,
            answer2,
            correct1,
            correct2,
            _cogData.value
        ) { updatedCogData ->
            _cogData.value = updatedCogData
        }

        println("Answer: '${_cogData.value.fourthQuestion}")

    }

    fun setRandomCouple() {
        fourthQuestionManager.setRandomCouple(imageCouples)
    }

    //Understanding Question (6/10)
    private val sixthQuestionManager = SixthQuestionManager()

    private val _sixthQuestionState = MutableStateFlow(SixthQuestionState())
    val sixthQuestionState: StateFlow<SixthQuestionState> = _sixthQuestionState.asStateFlow()

    fun setRandomAudio() {
        _sixthQuestionState.value = sixthQuestionManager.setRandomAudio(audioList, _sixthQuestionState.value)
    }

    fun updateItemLastPosition(index: Int, position: Offset) {
        _sixthQuestionState.value = sixthQuestionManager.updateItemLastPosition(index, position, _sixthQuestionState.value)
    }

    fun setFridgeOpened() {
        _sixthQuestionState.value = sixthQuestionManager.setFridgeOpened(_sixthQuestionState.value)
    }

    fun setItemMovedCorrectly() {
        _sixthQuestionState.value = sixthQuestionManager.setItemMovedCorrectly(_sixthQuestionState.value)
    }

    fun setNapkinPlacedCorrectly() {
        _sixthQuestionState.value = sixthQuestionManager.setNapkinPlacedCorrectly(_sixthQuestionState.value)
    }

    fun sixthQuestionAnswer() {
        _cogData.value = sixthQuestionManager.sixthQuestionAnswer(
            cogData.value,
            _sixthQuestionState.value
        )
        println("Answer: '${_cogData.value.sixthQuestion}")

    }

    //Drag and drop Question (7/10)

    private val seventhQuestionManager = SeventhQuestionManager(instructions = instructions)

    private val _seventhQuestionState = MutableStateFlow(SeventhQuestionState())
    val seventhQuestionState: StateFlow<SeventhQuestionState> = _seventhQuestionState.asStateFlow()

    fun setRandomInstructions() {
        val (randomInstruction, color) = seventhQuestionManager.setRandomInstructions()
        _seventhQuestionState.value = _seventhQuestionState.value.copy(
            instruction = randomInstruction,
            targetColor = color
        )
    }

    fun seventhQuestionAnswer(isCorrect: Boolean) {
        _cogData.value = seventhQuestionManager.submitAnswer(isCorrect, _cogData.value)
        println("Answer: '${_cogData.value.seventhQuestion}")

    }

    //Writing Question (8/10)
    private val eighthQuestionManager = EighthQuestionManager()

    private val _eighthQuestionState = MutableStateFlow(EighthQuestionState(slots = slotsList, answerSentences = sentencesResourceId))
    val eighthQuestionState: StateFlow<EighthQuestionState> = _eighthQuestionState.asStateFlow()

    fun isWordOnSlot(wordState: Offset, screenSize: IntSize, density: Density): Int? {
        val foundIndex = eighthQuestionManager.isWordOnSlot(wordState, screenSize, density, _eighthQuestionState.value.slots)
        _eighthQuestionState.update { it.copy(activeSlotIndex = foundIndex) }
        return foundIndex
    }

    fun updateWordInSlot(word: String, slotId: Int) {
        val updatedSlots = eighthQuestionManager.updateWordInSlot(word, slotId, _eighthQuestionState.value.slots)
        val updatedSentence = updatedSlots.sortedBy { it.id }.mapNotNull { it.word }
        val allFinished = updatedSlots.all { it.word != null }

        _eighthQuestionState.update {
            it.copy(
                slots = updatedSlots,
                sentence = updatedSentence,
                allFinished = allFinished
            )
        }
    }

    fun resetSlot(slotIndex: Int) {
        val updatedSlots = eighthQuestionManager.resetSlot(slotIndex, _eighthQuestionState.value.slots)
        val updatedSentence = updatedSlots.sortedBy { it.id }.mapNotNull { it.word }

        _eighthQuestionState.update {
            it.copy(
                slots = updatedSlots,
                sentence = updatedSentence,
                allFinished = false
            )
        }
    }

    fun updateSlotColor(slotId: Int) {
        val updatedSlots = eighthQuestionManager.updateSlotColor(slotId, _eighthQuestionState.value.slots)
        _eighthQuestionState.update { it.copy(slots = updatedSlots) }
    }

    fun eighthQuestionAnswer(sentences: List<String>) {
        val userSentence = _eighthQuestionState.value.sentence
        val answerItem = eighthQuestionManager.eighthQuestionAnswer(userSentence, sentences)

        _cogData.value = _cogData.value.copy(
            eighthQuestion = ArrayList(_cogData.value.eighthQuestion).apply { add(answerItem) }
        )
        println("Answer: '${_cogData.value.eighthQuestion}'")
    }

    //BuildShape Question (10/10)
    fun tenthQuestionAnswer(shape: String, grade: Double) {
        val time = getNow()
        val answer = TenthQuestionType.TenthQuestionItem(
            shape = MeasureObjectString(value = shape, dateTime = time),
            grade = MeasureObjectDouble(value = grade, dateTime = time)
        )
        val updatedList = _cogData.value.tenthQuestion + answer
        _cogData.value = _cogData.value.copy(tenthQuestion = ArrayList(updatedList))
    }
}
