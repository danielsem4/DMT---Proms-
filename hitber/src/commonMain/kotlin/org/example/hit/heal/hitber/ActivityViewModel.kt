package org.example.hit.heal.hitber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.shapes.shapeSets
import org.example.hit.heal.hitber.timeAndPlacee.ui.components.DropDownItem
import org.jetbrains.compose.resources.DrawableResource
import kotlin.random.Random


class ActivityViewModel : ViewModel() {

    private val _timeAndPlaceFocusedQuestion = MutableStateFlow<String?>(null)
    val timeAndPlaceFocusedQuestion: StateFlow<String?> = _timeAndPlaceFocusedQuestion.asStateFlow()

    private val _timeAndPlaceAnswersMap = MutableStateFlow<Map<String, DropDownItem>>(emptyMap())
    val timeAndPlaceAnswersMap: StateFlow<Map<String, DropDownItem>> =
        _timeAndPlaceAnswersMap.asStateFlow()

    fun timeAndPlaceSetFocusedQuestion(question: String?) {
        _timeAndPlaceFocusedQuestion.value = question
    }

    fun timeAndPlaceSetAnswer(question: String, answer: DropDownItem) {
        _timeAndPlaceAnswersMap.value = _timeAndPlaceAnswersMap.value.toMutableMap().apply {
            this[question] = answer
        }
    }

    private val _concentrationStartButtonIsVisible = MutableStateFlow(true)
    val concentrationStartButtonIsVisible: StateFlow<Boolean> =
        _concentrationStartButtonIsVisible.asStateFlow()


    private val _concentrationAnswers = MutableStateFlow<List<Int>>(emptyList())
    val concentrationAnswers: StateFlow<List<Int>> = _concentrationAnswers.asStateFlow()

    private val _number = MutableStateFlow((0..9).random())  // שימוש ב-StateFlow כדי לשמור על המספר
    val number: StateFlow<Int> = _number.asStateFlow()
    private var elapsedTime by mutableStateOf(0)

    private val _isFinished = MutableStateFlow(false)
    val isFinished : StateFlow<Boolean> = _isFinished.asStateFlow()
    
    private val _selectedSet = MutableStateFlow(shapeSets.random())
    val selectedSet : StateFlow<List<DrawableResource>> = _selectedSet.asStateFlow()

    // להתחיל את המשחק (הסתרת כפתור ההתחלה)
    fun concentrationStartButtonSetVisible(visible: Boolean) {
        _concentrationStartButtonIsVisible.value = visible
    }

    // הוספת תשובות
    fun addConcentrationAnswer(answer: Int) {
        _concentrationAnswers.value += answer
    }

    // עדכון מספרים והזמן
    fun startRandomNumberGeneration(scope: CoroutineScope) {
        scope.launch {
            while (elapsedTime < 60) {
                delay(3000)
                _number.value = (0..9).random()
                elapsedTime += 3
            }
            _isFinished.value = true
        }
    }

    fun setRandomShapeSet(){
        _selectedSet.value = shapeSets[Random.nextInt(shapeSets.size)]
    }
}