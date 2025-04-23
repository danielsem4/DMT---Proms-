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
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.hit.heal.core.presentation.Colors.backgroundColor
import org.example.hit.heal.hitber.model.CogData
import org.example.hit.heal.hitber.model.EighthQuestionItem
import org.example.hit.heal.hitber.model.FirstQuestion
import org.example.hit.heal.hitber.model.MeasureObjectBoolean
import org.example.hit.heal.hitber.model.MeasureObjectDouble
import org.example.hit.heal.hitber.model.MeasureObjectInt
import org.example.hit.heal.hitber.model.MeasureObjectString
import org.example.hit.heal.hitber.model.SecondQuestionItem
import org.example.hit.heal.hitber.model.SelectedShapesStringList
import org.example.hit.heal.hitber.model.SeventhQuestionType
import org.example.hit.heal.hitber.model.SixthQuestionType
import org.example.hit.heal.hitber.model.TenthQuestionType
import org.example.hit.heal.hitber.model.ThirdQuestionItem
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.instructions
import org.example.hit.heal.hitber.presentation.naming.components.imageCouples
import org.example.hit.heal.hitber.presentation.shapes.components.Shape
import org.example.hit.heal.hitber.presentation.shapes.components.shapeList
import org.example.hit.heal.hitber.presentation.shapes.components.shapeSets
import org.example.hit.heal.hitber.presentation.timeAndPlace.components.DropDownItem
import org.example.hit.heal.hitber.presentation.understanding.components.audioList
import org.example.hit.heal.hitber.presentation.writing.components.WordSlotState
import org.example.hit.heal.hitber.presentation.writing.components.sentencesResourceId
import org.example.hit.heal.hitber.presentation.writing.components.slotsList
import org.example.hit.heal.hitber.utils.getNow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

class ActivityViewModel : ViewModel() {

    private val _cogData = MutableStateFlow(CogData())
    val cogData: StateFlow<CogData> get() = _cogData

    //Time and Place Question (1/10)

    private val _allAnswersFinished = MutableStateFlow(false)
    val allAnswersFinished : StateFlow<Boolean> get() = _allAnswersFinished

    private fun updateAllAnswersFinished() {
        val q = _cogData.value.firstQuestion
        _allAnswersFinished.value = listOf(
            q.day, q.month, q.year, q.country, q.city, q.place, q.survey
        ).all { it.value.isNotBlank() }
    }

    fun firstQuestionAnswer(field: String, answer: DropDownItem) {
        val currentFirstQuestion = _cogData.value.firstQuestion

        val updated = when (field) {
            "day" -> currentFirstQuestion.copy(day = MeasureObjectString(101, answer.text, getNow()))
            "month" -> currentFirstQuestion.copy(month = MeasureObjectString(102, answer.text, getNow()))
            "year" -> currentFirstQuestion.copy(year = MeasureObjectString(109, answer.text, getNow()))
            "country" -> currentFirstQuestion.copy(country = MeasureObjectString(104, answer.text, getNow()))
            "city" -> currentFirstQuestion.copy(city = MeasureObjectString(105, answer.text, getNow()))
            "place" -> currentFirstQuestion.copy(place = MeasureObjectString(106, answer.text, getNow()))
            "survey" -> currentFirstQuestion.copy(survey = MeasureObjectString(108, answer.text, getNow()))
            else -> currentFirstQuestion
        }

        _cogData.value = _cogData.value.copy(firstQuestion = updated)
        updateAllAnswersFinished()
        println("Updated Question: Day: ${updated.day}, Month: ${updated.month}, Year: ${updated.year}")
    }

    //Shape Question (2/10)
    private val _listShapes = MutableStateFlow(shapeList)
    val listShapes: StateFlow<List<Shape>> = _listShapes.asStateFlow()

    private val _selectedSet = MutableStateFlow<List<Shape>>(emptyList())
    val selectedSet: StateFlow<List<Shape>> = _selectedSet.asStateFlow()

    private val _selectedShapes = MutableStateFlow<List<Shape>>(emptyList())
    val selectedShapes: StateFlow<List<Shape>> = _selectedShapes.asStateFlow()

    private val _attempt = MutableStateFlow(1)
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    private var correctShapesCount = 0
    private var distractorToRemove = 0

    fun setRandomShapeSet() {
        val selectedTypes = shapeSets.random()
        _selectedSet.value = shapeList.filter { it.type in selectedTypes }
    }

    fun setSelectedShapes(shape: Shape) {
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
        val distractors = _listShapes.value.filter { shape ->
            selectedSet.value.none { it.drawable == shape.drawable }
        }
        val distractorsToRemove = distractors.take(count)
        val updatedList = _listShapes.value - distractorsToRemove.toSet()
        _listShapes.value = updatedList
    }

    fun secondQuestionAnswer(
        questionNumber: Int,
        shapeNames: List<String>
    ) {
        val selected = SelectedShapesStringList(
            value = shapeNames,
            dateTime = getNow()
        )

        val newAnswer = SecondQuestionItem(
            selectedShapes = selected,
            wrongShapes = MeasureObjectInt(value = 5 - correctShapesCount, dateTime = getNow())
        )

        when (questionNumber) {
            2 -> {
                val updatedList = _cogData.value.secondQuestion + newAnswer
                _cogData.value = _cogData.value.copy(secondQuestion = ArrayList(updatedList))
            }

            9 -> {
                val updatedList = _cogData.value.ninthQuestion + newAnswer
                _cogData.value = _cogData.value.copy(ninthQuestion = ArrayList(updatedList))
            }

            else -> {
                println("שאלה לא מוכרת: $questionNumber")
            }
        }

        _selectedShapes.value = emptyList()
        correctShapesCount = 0
    }


    fun resetSelectedShapes() {
        _selectedShapes.value = emptyList()
        correctShapesCount = 0
        distractorToRemove = 0
        _attempt.value = 1
        _listShapes.value = shapeList
    }

    //concentration Question (3/10)
    private val _startButtonIsVisible = MutableStateFlow(true)
    val startButtonIsVisible: StateFlow<Boolean> =
        _startButtonIsVisible.asStateFlow()

    private var numberAppearedAt: Int = 0
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

    fun thirdQuestionAnswer(answer: Int) {
        if (_isNumberClickable.value) {
            val reactionTime = (Clock.System.now().toEpochMilliseconds() - numberAppearedAt) / 1000.0

            val answerItem = ThirdQuestionItem(
                number = MeasureObjectInt(value = answer, dateTime = getNow()),
                time = MeasureObjectInt(value = reactionTime.toInt(), dateTime = getNow()),
                isPressed = MeasureObjectBoolean(value = true, dateTime = getNow())
            )

            val updatedList = ArrayList(_cogData.value.thirdQuestion).apply {
                add(answerItem)
            }

            _cogData.value = _cogData.value.copy(thirdQuestion = updatedList)
            _isNumberClickable.value = false

            println("🧠 New answer saved: $answerItem")
            println("✨ All answers: ${_cogData.value.thirdQuestion}")
        }
    }

    fun startRandomNumberGeneration() {
        viewModelScope.launch {
            while (elapsedTime < 60) {
                delay(2500)
                numberAppearedAt = Clock.System.now().toEpochMilliseconds().toInt()
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

    fun fourthQuestionAnswer(answer1: String, answer2: String, correct1: String, correct2: String) {
        val now = getNow()
        val newFourthQuestionList = arrayListOf(
            MeasureObjectString(value = correct1, dateTime = now),
            MeasureObjectString(value = answer1, dateTime = now),
            MeasureObjectString(value = correct2, dateTime = now),
            MeasureObjectString(value = answer2, dateTime = now)
        )

        _cogData.value = _cogData.value.copy(fourthQuestion = newFourthQuestionList)

        println("New Fourth Question List: $newFourthQuestionList")
    }

    fun setRandomCouple() {
        _selectedCouple.value = imageCouples.random()
    }

    //Understanding Question (6/10)
    private val _audioResourceId = MutableStateFlow<StringResource?>(null)
    val audioResourceId: StateFlow<StringResource?> get() = _audioResourceId.asStateFlow()

    private val _selectedItem = MutableStateFlow<DrawableResource?>(null)
    val selectedItem: StateFlow<DrawableResource?> get() = _selectedItem.asStateFlow()

    private val _selectedNapkin = MutableStateFlow<DrawableResource?>(null)
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

    private val _isFridgeOpened = MutableStateFlow(false)
    val isFridgeOpened: StateFlow<Boolean> get() = _isFridgeOpened.asStateFlow()

    private val _isItemMovedCorrectly = MutableStateFlow(false)
    val isItemMovedCorrectly: StateFlow<Boolean> get() = _isItemMovedCorrectly.asStateFlow()

    private val _isNapkinPlacedCorrectly = MutableStateFlow(false)

    fun setFridgeOpened() {
        _isFridgeOpened.value = true
    }

    fun setItemMovedCorrectly() {
        _isItemMovedCorrectly.value = true
    }

    fun setNapkinPlacedCorrectly() {
        _isNapkinPlacedCorrectly.value = true
    }

    fun sixthQuestionAnswer() {
        val time = getNow()
        val updatedQuestion = SixthQuestionType.SixthQuestionItem(
            fridgeOpened = MeasureObjectBoolean(value = _isFridgeOpened.value, dateTime = time),
            correctProductDragged = MeasureObjectBoolean(value = _isItemMovedCorrectly.value, dateTime = time),
            placedOnCorrectNap = MeasureObjectBoolean(value = _isNapkinPlacedCorrectly.value, dateTime = time)
        )

        val currentList = _cogData.value.sixthQuestion.toCollection(ArrayList())
        currentList.add(updatedQuestion)

        _cogData.value = _cogData.value.copy(sixthQuestion = currentList)

        println("✅ Updated cogData.sixthQuestion: $currentList")
    }


    //Drag and drop Question (7/10)

    private val _instructionsResourceId = MutableStateFlow<StringResource?>(null)
    val instructionsResourceId: StateFlow<StringResource?> get() = _instructionsResourceId.asStateFlow()

    private val _targetCircleColor = MutableStateFlow<Color?>(null)
    val targetCircleColor: StateFlow<Color?> get() = _targetCircleColor

    fun setRandomInstructions() {
        val (randomInstruction, color) = instructions.random()
        _instructionsResourceId.value = randomInstruction
        _targetCircleColor.value = color
    }

    fun seventhQuestionAnswer(isCorrect: Boolean) {
        val updatedItem = SeventhQuestionType.SeventhQuestionItem(
            isCorrect = MeasureObjectBoolean(value = isCorrect, dateTime = getNow())
        )

        val currentList = ArrayList(_cogData.value.seventhQuestion)
        currentList.add(updatedItem)

        _cogData.value = _cogData.value.copy(seventhQuestion = currentList)
        println("SeventhQuestionItem saved: ${_cogData.value.seventhQuestion}")
    }


    //Writing Question (8/10)
    private val _slotsWords = MutableStateFlow(slotsList)
    val slotsWords: StateFlow<List<WordSlotState>> = _slotsWords

    private val _activeSlotIndex = MutableStateFlow<Int?>(null)
    val activeSlotIndex: StateFlow<Int?> = _activeSlotIndex

    private val _sentence = MutableStateFlow<List<String>>(emptyList())

    private val _allFinished = MutableStateFlow(false)
    val allFinished: StateFlow<Boolean> = _allFinished

    private val _answerSentences = MutableStateFlow(sentencesResourceId)
    val answerSentences: StateFlow<List<StringResource>> = _answerSentences

    fun eighthQuestionAnswer(sentences: List<String>) {
        val userSentence = _sentence.value.joinToString(" ")
        println("User sentence: '$userSentence'")

        val isCorrect = sentences.any { it.trim().equals(userSentence.trim(), ignoreCase = true) }
        val answer = MeasureObjectBoolean(value = isCorrect, dateTime = getNow())
        val item = EighthQuestionItem(writtenSentence = answer)

        _cogData.value = _cogData.value.copy(
            eighthQuestion = ArrayList(_cogData.value.eighthQuestion).apply { add(item) }
        )

        println("✍️ EighthQuestionItem saved: $item")
    }

    fun isWordOnSlot(
        wordState: Offset,
        screenSize: IntSize,
        density: Density,
    ): Int? {
        val foundIndex = _slotsWords.value.indexOfFirst { slot ->
            val slotWidthPx = with(density) { 150.dp.toPx() }
            val slotHeightPx = with(density) { 100.dp.toPx() }

            val slotLeft =   slot.initialOffset.x * screenSize.width - slotWidthPx / 2
            val slotRight =   slot.initialOffset.x * screenSize.width + slotWidthPx / 2
            val slotTop = slot.initialOffset.y * screenSize.height - slotHeightPx / 2
            val slotBottom = slot.initialOffset.y * screenSize.height + slotHeightPx / 2

            wordState.x * screenSize.width in slotLeft..slotRight &&
                    wordState.y * screenSize.height in slotTop..slotBottom
        }.takeIf { it >= 0 }

        _activeSlotIndex.value = foundIndex
        return foundIndex

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

    //BuildShape Question (10/10)
    fun tenthQuestionAnswer(shape: String, grade: Double) {
        val answer = TenthQuestionType.TenthQuestionItem(
            shape = MeasureObjectString(value = shape, dateTime = getNow()),
            grade = MeasureObjectDouble(value = grade, dateTime = getNow())
        )
        val updatedList = _cogData.value.tenthQuestion + answer
        _cogData.value = _cogData.value.copy(tenthQuestion = ArrayList(updatedList))    }

}




