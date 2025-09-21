package org.example.hit.heal.hitber.presentation.timeAndPlace

import androidx.lifecycle.ViewModel
import core.data.model.MeasureObjectString
import core.utils.getCurrentFormattedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.example.hit.heal.core.presentation.components.DropDownItem

/**
 * Holds dynamic questions for screen=1 (time & place), tracks answers by label,
 * handles 3-per-page paging, and builds the final result payload.
 *
 * Each question is Pair<ArrayList<String> /*answers*/, String /*label(object_label)*/>
 */
class FirstQuestionViewModel : ViewModel() {

    // All dynamic questions provided by ActivityViewModel.setFirstQuestion()
    private val _questions =
        MutableStateFlow<List<Pair<ArrayList<String>, String>>>(emptyList())
    val questions: StateFlow<List<Pair<ArrayList<String>, String>>> get() = _questions

    // Answers by label (object_label -> chosen text)
    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers: StateFlow<Map<String, String>> get() = _answers

    // Paging (index of chunk of 3)
    private val _currentGroupIndex = MutableStateFlow(0)
    val currentGroupIndex: StateFlow<Int> get() = _currentGroupIndex

    /** Call once after you fetch questions from ActivityViewModel.setFirstQuestion(). */
    fun initializeQuestions(list: List<Pair<ArrayList<String>, String>>) {
        _questions.value = list
        _answers.value = emptyMap()
        _currentGroupIndex.value = 0
    }

    /** Store selection for a label. Keep API accepting DropDownItem for convenience. */
    fun firstQuestionAnswer(label: String, selected: DropDownItem) {
        _answers.update { it + (label to selected.text) }
    }

    /** Current selected text for a label (empty if none chosen). */
    fun getSelected(label: String): String = _answers.value[label].orEmpty()

    /** Are all keys in the current group answered? */
    fun isGroupCompleted(keys: List<String>): Boolean =
        keys.all { _answers.value[it].orEmpty().isNotBlank() }

    /** Move to the next 3-question group if possible. */
    fun nextGroup(totalGroups: Int): Boolean {
        val next = _currentGroupIndex.value + 1
        return if (next in 0 until totalGroups) {
            _currentGroupIndex.value = next
            true
        } else {
            false
        }
    }

    /**
     * Build the result list ordered exactly like questions.
     * IDs are injected later by ActivityViewModel.setFirstQuestionResults(...)
     */
    fun buildResult(date: String = getCurrentFormattedDateTime()): ArrayList<MeasureObjectString> {
        val list = _questions.value.map { (_, label) ->
            MeasureObjectString(
                measureObject = 0, // placeholder; set by setFirstQuestionResults()
                value = _answers.value[label].orEmpty(),
                dateTime = date
            )
        }
        return ArrayList(list)
    }
}
