package org.example.hit.heal.hitber

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.instructions
import org.example.hit.heal.hitber.presentation.naming.components.imageCouples
import org.example.hit.heal.hitber.presentation.shapes.components.shapeList
import org.example.hit.heal.hitber.presentation.shapes.components.shapeSets
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.DropDownItem
import org.example.hit.heal.hitber.presentation.understanding.components.audioList
import org.example.hit.heal.hitber.presentation.understanding.components.fridgeItems
import org.example.hit.heal.hitber.presentation.writing.components.DraggableWordState
import org.example.hit.heal.hitber.presentation.writing.components.WordSlotState
import org.example.hit.heal.hitber.presentation.writing.components.draggableWordsList
import org.example.hit.heal.hitber.presentation.writing.components.sentencesResourceId
import org.example.hit.heal.hitber.presentation.writing.components.slotsList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

class ActivityViewModel : ViewModel() {

    //Time and Place Question (1/10)
    private val _answersTimeAndPlace = MutableStateFlow<Map<String, DropDownItem>>(emptyMap())
    val answersTimeAndPlace: StateFlow<Map<String, DropDownItem>> =
        _answersTimeAndPlace.asStateFlow()

    fun setAnswersTimeAndPlace(question: String, answer: DropDownItem) {
        _answersTimeAndPlace.value = _answersTimeAndPlace.value.toMutableMap().apply {
            this[question] = answer
        }
    }

    //Shape Question (2/10)
    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<DrawableResource>> = _listShapes.asStateFlow()

    private val _selectedSet = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedSet: StateFlow<List<DrawableResource>> = _selectedSet.asStateFlow()

    private val _selectedShapes = MutableStateFlow<List<DrawableResource>>(emptyList())
    val selectedShapes: StateFlow<List<DrawableResource>> = _selectedShapes.asStateFlow()

    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    private val _answersShapes =
        MutableStateFlow<List<Triple<Int, List<DrawableResource>, Int>>>(emptyList())

    private var correctShapesCount = 0
    private var distractorToRemove = 0


    fun setRandomShapeSet() {
        _selectedSet.value = shapeSets.random()
    }

    fun setSelectedShapes(shape: DrawableResource) {
        if (_selectedShapes.value.size >= 5) {
            if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value = _selectedShapes.value.filterNot { it == shape }
            }
        } else {
            _selectedShapes.value = if (_selectedShapes.value.contains(shape)) {
                _selectedShapes.value.filterNot { it == shape }
            } else {
                _selectedShapes.value + shape
            }
        }
    }


    fun calculateCorrectShapesCount() {
        correctShapesCount = selectedShapes.value.count { it in selectedSet.value }
    }

    fun updateTask() {
        when (correctShapesCount) {
            5 -> _attempt.value = 3

            4 -> {
                when (attempt.value) {
                    1, 2 -> _attempt.value++
                }
            }

            3 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove = 1
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }

            2 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove = 2
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }

            1, 0 -> {
                when (_attempt.value++) {
                    1 -> distractorToRemove = 3
                    2 -> distractorToRemove = 2
                }
                removeDistractors(distractorToRemove)
            }
        }
    }

    private fun removeDistractors(count: Int) {
        val distractors = _listShapes.value.filter { it !in selectedSet.value }
        val distractorsToRemove = distractors.take(count)
        val updatedList = _listShapes.value - distractorsToRemove.toSet()
        _listShapes.value = updatedList
    }

    fun setAnswersShapes() {
        _answersShapes.value += Triple(_attempt.value - 1, selectedShapes.value, correctShapesCount)
        resetSelectedShapes()
        println("תשובות שנשמרו: ${_answersShapes.value}")

    }

    private fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
    }


    //concentration Question (3/10)
    private val _startButtonIsVisible = MutableStateFlow(true)
    val startButtonIsVisible: StateFlow<Boolean> =
        _startButtonIsVisible.asStateFlow()

    private val _answersConcentration = MutableStateFlow<List<Pair<Double, Int>>>(emptyList())

    private var numberAppearedAt: Long = 0L
    private var elapsedTime = 0.0

    private val _number = MutableStateFlow((0..9).random())
    val number: StateFlow<Int> = _number.asStateFlow()

    private val _isNumberClickable = MutableStateFlow(true)
    val isNumberClickable: StateFlow<Boolean> = _isNumberClickable.asStateFlow()


    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    fun startButtonSetVisible(visible: Boolean) {
        _startButtonIsVisible.value = visible
    }

    fun setAnswersConcentration(answer: Int) {
        if (_isNumberClickable.value) {
            val reactionTime =
                (Clock.System.now().toEpochMilliseconds() - numberAppearedAt) / 1000.0
            _answersConcentration.value += Pair(reactionTime, answer)
            _isNumberClickable.value = false
        }
    }

    fun startRandomNumberGeneration() {
        viewModelScope.launch {
            while (elapsedTime < 60) {
                delay(2500)
                numberAppearedAt = Clock.System.now().toEpochMilliseconds()
                _number.value = (0..9).random()
                _isNumberClickable.value = true
                elapsedTime += 2.5
            }
            _isFinished.value = true
        }
    }

    //naming Question (4/10)
    private val _selectedCouple = MutableStateFlow<Pair<DrawableResource, DrawableResource>?>(null)
    val selectedCouple: StateFlow<Pair<DrawableResource, DrawableResource>?> =
        _selectedCouple.asStateFlow()

    private val _answersNaming =
        MutableStateFlow<Pair<Pair<String, String>, Pair<String, String>>?>(null)

    fun setAnswersNaming(answer1: String, answer2: String, correct1: String, correct2: String) {
        _answersNaming.value = Pair(Pair(answer1, correct1), Pair(answer2, correct2))
    }

    fun setRandomCouple() {
        _selectedCouple.value = imageCouples.random()
    }

    //Understanding Question (6/10)
    private val _audioResourceId = MutableStateFlow<StringResource?>(null)
    val audioResourceId: StateFlow<StringResource?> get() = _audioResourceId.asStateFlow()

    private val _selectedItem = MutableStateFlow<DrawableResource?>(null)
    val selectedItem: StateFlow<DrawableResource?> get() = _selectedItem.asStateFlow()

    private val _selectedNapkin = MutableStateFlow<DrawableResource?>(null) // שמירה על צבע המפית
    val selectedNapkin: StateFlow<DrawableResource?> get() = _selectedNapkin.asStateFlow()


    fun setRandomAudio() {
        if (_audioResourceId.value == null) {
            val randomAudio = audioList.random()
            _audioResourceId.value = randomAudio.audioResId
            _selectedItem.value = randomAudio.itemResId
            _selectedNapkin.value = randomAudio.napkinColorResId
        }
    }

    private val _itemLastPositions = MutableStateFlow<Map<Int, Offset>>(emptyMap())
    val itemLastPositions: StateFlow<Map<Int, Offset>> = _itemLastPositions.asStateFlow()

    fun updateItemLastPosition(index: Int, position: Offset) {
        _itemLastPositions.value = _itemLastPositions.value.toMutableMap().apply {
            this[index] = position
        }
    }

    private val _itemPositions = MutableStateFlow(List(fridgeItems.size) { Pair(0f, 0f) })
    val itemPositions = _itemPositions.asStateFlow()

    fun updateItemPosition(index: Int, dragAmount: Pair<Float, Float>) {
        _itemPositions.update { currentList ->
            currentList.mapIndexed { i, position ->
                if (i == index) {
                    val newX = position.first + dragAmount.first
                    val newY = position.second + dragAmount.second
                    Pair(newX, newY)
                } else {
                    position
                }
            }
        }
    }

    private val _score = MutableStateFlow(0)

    private val _isFridgeOpened = MutableStateFlow(false)
    val isFridgeOpened: StateFlow<Boolean> get() = _isFridgeOpened.asStateFlow()

    private val _isItemMovedCorrectly = MutableStateFlow(false)
    val isItemMovedCorrectly: StateFlow<Boolean> get() = _isItemMovedCorrectly.asStateFlow()

    private val _isNapkinPlacedCorrectly = MutableStateFlow(false)

    private fun updateScore() {
        _score.value = listOf(
            _isFridgeOpened.value,
            _isItemMovedCorrectly.value,
            _isNapkinPlacedCorrectly.value
        ).count { it }
        println("Current score: ${_score.value}")
    }

    fun setFridgeOpened() {
        _isFridgeOpened.value = true
        updateScore()
    }

    fun setItemMovedCorrectly() {
        _isItemMovedCorrectly.value = true
        updateScore()
    }

    fun setNapkinPlacedCorrectly() {
        _isNapkinPlacedCorrectly.value = true
        updateScore()
    }

    //Drag and drop Question (7/10)
    private val _circlePositions = MutableStateFlow(
        listOf(
            Pair(0.4f, 0.6f),
            Pair(0.4f, 0.4f),
            Pair(0.6f, 0.4f),
            Pair(0.6f, 0.6f)
        )
    )
    val circlePositions = _circlePositions.asStateFlow()

    fun updateCirclePosition(index: Int, dragAmount: Offset) {
        _circlePositions.update { currentList ->
            currentList.mapIndexed { i, position ->
                if (i == index) {
                    Pair(position.first + dragAmount.x, position.second + dragAmount.y)
                } else {
                    position
                }
            }
        }
    }

    private val _instructionsResourceId = MutableStateFlow<StringResource?>(null)
    val instructionsResourceId: StateFlow<StringResource?> get() = _instructionsResourceId.asStateFlow()

    private val _targetCircleColor = MutableStateFlow<Color?>(null)
    val targetCircleColor: StateFlow<Color?> get() = _targetCircleColor

    private val _answerDragAndDrop = MutableStateFlow<Boolean>(false)

    fun setRandomInstructions() {
        val (randomInstruction, color) = instructions.random()
        _instructionsResourceId.value = randomInstruction
        _targetCircleColor.value = color
    }

    fun setAnswerDragAndDrop() {
        println("setAnswerDragAndDrop called!")
        _answerDragAndDrop.value = true
    }

    //Writing Question (8/10)

    private val _words = MutableStateFlow(draggableWordsList)
    val words: StateFlow<List<DraggableWordState>> = _words

    private val _copiedWords = MutableStateFlow(draggableWordsList)
    val copiedWords: StateFlow<List<DraggableWordState>> = _copiedWords

    private val _slotsWords = MutableStateFlow(slotsList)
    val slotsWords : StateFlow<List<WordSlotState>> = _slotsWords

    private val _activeSlotIndex = MutableStateFlow<Int?>(null)
    val activeSlotIndex: StateFlow<Int?> = _activeSlotIndex

    private val _sentence = MutableStateFlow<List<String>>(emptyList())


    private val _allFinished= MutableStateFlow(false)
     val allFinished: StateFlow<Boolean> = _allFinished

    private val _answerSentences = MutableStateFlow(sentencesResourceId)
    val answerSentences: StateFlow<List<StringResource>> = _answerSentences

    private val _answerWriting = MutableStateFlow(false)

    fun checkSentence(sentences: List<String>): Boolean {
        val userSentence = _sentence.value.joinToString(" ")
        _answerWriting.value = userSentence in sentences
        return _answerWriting.value
    }

    fun isWordOnSlot(wordState: Offset, containerSize: IntSize, density: Density, isRTL: Boolean): Int? {
        val foundIndex = _slotsWords.value.indexOfFirst { slot ->
            val slotWidthPx = with(density) { 150.dp.toPx() }
            val slotHeightPx = with(density) { 100.dp.toPx() }

            val adjustedX = if (isRTL) {
                containerSize.width - (slot.initialOffset.x * containerSize.width)
            } else {
                slot.initialOffset.x * containerSize.width
            }

            val slotLeft = adjustedX - slotWidthPx / 2
            val slotRight = adjustedX + slotWidthPx / 2
            val slotTop = slot.initialOffset.y * containerSize.height - slotHeightPx / 2
            val slotBottom = slot.initialOffset.y * containerSize.height + slotHeightPx / 2

            wordState.x * containerSize.width in slotLeft..slotRight &&
                    wordState.y * containerSize.height in slotTop..slotBottom
        }.takeIf { it >= 0 }

        _activeSlotIndex.value = if (isRTL && foundIndex != null) {
            _slotsWords.value.size - 1 - foundIndex
        } else {
            foundIndex
        }

        return _activeSlotIndex.value
    }

    fun updateWordInSlot(word: String, slotId: Int) {
        _slotsWords.value = _slotsWords.value.mapIndexed { index, slot ->

            if (index == slotId && slot.word == null) {
                slot.copy(word = word, color = backgroundColor)
            } else {
                slot
            }
        }
        updateSentence()
        _allFinished.value = areAllSlotsFilled()

    }

    fun resetSlot(slotIndex: Int) {
        _slotsWords.value = _slotsWords.value.mapIndexed { index, slot ->
            if (index == slotIndex) {
                slot.copy(word = null, color = Color.Gray)
            } else {
                slot
            }
        }
        updateSentence()
    }
    fun updateSlotColor(slotId: Int) {
        _slotsWords.value = _slotsWords.value.map { slot ->
            if (slot.id == slotId && slot.word == null) {
                slot.copy(color = backgroundColor)
            } else if (slot.word == null) {
                slot.copy(color = Color.Gray)
            } else {
                slot
            }
        }
    }


    private fun updateSentence() {
        _sentence.value = _slotsWords.value
            .sortedBy { it.id }
            .mapNotNull { it.word }
    }

    private fun areAllSlotsFilled(): Boolean {
        return _slotsWords.value.all { it.word != null }
    }

}