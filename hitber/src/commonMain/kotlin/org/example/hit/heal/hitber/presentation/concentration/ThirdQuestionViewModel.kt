package org.example.hit.heal.hitber.presentation.concentration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class ThirdQuestionViewModel: ViewModel() {

    var thirdQuestionAnswers: MutableList<Pair<Int, Int>> = mutableListOf()

    private val _startButtonIsVisible = MutableStateFlow(true)
    val startButtonIsVisible: StateFlow<Boolean> =
        _startButtonIsVisible.asStateFlow()

    private var numberAppearedAt: Long  = 0
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
            val reactionTime: Int = ((Clock.System.now().toEpochMilliseconds() - numberAppearedAt) / 1000).toInt()

            thirdQuestionAnswers.add(
                Pair(answer, reactionTime)
            )
            _isNumberClickable.value = false
        }
    }

    // Starts the number generation loop, shows new number every 2.5 seconds for 60 seconds total
    fun startRandomNumberGeneration() {
        viewModelScope.launch {
            val now = Clock.System.now().toEpochMilliseconds()
            numberAppearedAt = now

            withContext(Dispatchers.Main) {
                _number.value = (0..9).random()
                _isNumberClickable.value = true
            }

            while (elapsedTime < 60) {
                delay(2500)

                val nowLoop = Clock.System.now().toEpochMilliseconds()
                numberAppearedAt = nowLoop

                withContext(Dispatchers.Main) {
                    _number.value = (0..9).random()
                    _isNumberClickable.value = true
                }

                elapsedTime += 2.5
            }
            _isFinished.value = true
        }
    }

}