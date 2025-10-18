package com.example.finalprojectnew.presentation.stage1.conveyor_game

import com.example.finalprojectnew.di.ServiceLocator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

// ✅ ייבוא נכון של מודל/רישום של שלב 1
import com.example.finalprojectnew.assessment.SessionRecorder
import com.example.finalprojectnew.assessment.Stage1EndReason
import com.example.finalprojectnew.assessment.Stage1Result

data class ConveyorUiState(
    val ingredients: List<String>, // the correct ingredients for the specific recipe
    val timeLeft: Int,
    val checked: List<Boolean>, // checkbox for correct ingredients
    val selectedNames: Set<String>, // ingredients we that already press them
    val movingItems: List<ProductItem>, // ingredients on coveyor - live
    val offset: Float, // how much the conveyor moved
    val finished: Boolean, // the game is over?
    val stride: Float
)

class ConveyorGameViewModel(
    private val recipeId: String,
    private val timeLimitSeconds: Int = 1
) {

    // coroutines help at the background - we have 2 - one for conveyor one for the timer
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(
        ConveyorUiState(
            ingredients = emptyList(),
            timeLeft = timeLimitSeconds,
            checked = emptyList(),
            selectedNames = emptySet(),
            movingItems = emptyList(),
            offset = 0f,
            finished = false,
            stride = 260f
        )
    )
    val state: StateFlow<ConveyorUiState> = _state.asStateFlow()

    // conveyor fixed settings:
    private val visibleCount = 6 // always 6 ingredient on conveyor
    private val speedPerTick = 2.2f // every tick the conveyor moves 2.2f
    private val tickMillis = 24L // time of ticks: 24 millisec
    private val minGapRecent = 7 // distance between identical items


    // recipe data:
    // to make sure this is the correct recipe+names
    private val recipe = ServiceLocator.getRecipeById(recipeId)
        ?: error("Recipe $recipeId not found")
    private val ingredients = recipe.ingredients.map { it.name }

    // universe = all ingredients from all recipes
    private val universe: Set<String> = run {
        ServiceLocator.getRecipes()
            .flatMap { it.ingredients.map { ing -> ing.name } }
            .toSet()
    }

    // building catalog -> correct+distracting items
    private val catalog: List<ProductItem> = buildCatalogForRecipe(ingredients, universe)

    private val rng: Random = Random.Default // random choice
    private val correctPoolBase = catalog.filter { it.isCorrect } // correct items
    private val wrongPoolBase   = catalog.filter { !it.isCorrect } // wrong items
    private var consecutiveCorrect = 0 // Count how many consecutive correct answers

    // Outcome measures
    private var recorded = false // Prevents recording a result twice
    private var correctClicks = 0 // for the report
    private var wrongClicks = 0 // for the report

    // Rules for selecting a new item to the conveyor
    private fun drawNext(
        forbiddenRecent: Set<String>, // items that appeared recently
        selected: Set<String> // items that already clicked
    ): ProductItem {
        val mustPickWrong = consecutiveCorrect >= 2 // if 2 correct in row - must have a distractor
        val pickCorrect = if (mustPickWrong) false else rng.nextDouble() < 0.6 // 60% correct unless already 2 correct in a row → must pick wrong

        // "poolBase" means the main list (or "pool") of items we choose from —
        // either the correct items or the wrong ones (distractors).
        // Each time we pick a new product for the conveyor, we draw it randomly from this pool.
        // If we can't find a valid one (for example, it appeared too recently or was already selected),
        // we try again — sometimes from the opposite pool ("fallback") — until we find a valid item.

        val poolBase = if (pickCorrect) correctPoolBase else wrongPoolBase
        val fallback = if (pickCorrect) wrongPoolBase else correctPoolBase

        var tries = 0
        fun pickFrom(list: List<ProductItem>): ProductItem = list.random(rng)

        // Try to find a valid candidate (not forbidden and not already selected)
        var candidate = pickFrom(poolBase.ifEmpty { fallback })
        while (
            (candidate.name in forbiddenRecent || candidate.name in selected) &&
            tries < 80 // Safety cap to avoid infinite loop
        ) {
            // Alternate between base and fallback while searching
            val source = if (tries % 2 == 0) poolBase else fallback
            candidate = pickFrom(source.ifEmpty { fallback })
            tries++
        }

        // Update streak counter: add if correct, reset if wrong
        consecutiveCorrect = if (candidate.isCorrect) consecutiveCorrect + 1 else 0
        return candidate
    }

    init {
        // Initial "checked" = all false (nothing collected yet)
        val initialChecked = MutableList(ingredients.size) { false }

        val initial = mutableListOf<ProductItem>() // empty list - start to fill the conveyor
        repeat(visibleCount) { // we defins that visiblecount=6 items on the conveyor
            val forbidden = initial.take(minGapRecent).map { it.name }.toSet()
            initial.add(drawNext(forbiddenRecent = forbidden, selected = emptySet()))
        }

        _state.value = ConveyorUiState(
            ingredients = ingredients,
            timeLeft = timeLimitSeconds,
            checked = initialChecked,
            selectedNames = emptySet(),
            movingItems = initial,
            offset = 0f,
            finished = false,
            stride = 260f
        )

        // the coroutines - background loops
        runBeltLoop() // moves the conveyor every 24ms and swaps items when needed.
        runTimerLoop() // decrements by one second at a time, stopping when time runs out.
    }


    // when the user click on item:
    fun onProductClicked(item: ProductItem) {
        val s = _state.value
        if (s.finished || item.name in s.selectedNames) return // Ignore duplicates or end

        if (item.isCorrect) correctClicks++ else wrongClicks++ // Count taps for assessment

        val newSelected = s.selectedNames + item.name // Add this name to the "already selected" set
        val newChecked = s.checked.toMutableList().also { list ->  // If correct: mark it as collected in the 'checked' list (parallel to 'ingredients')

            if (item.isCorrect) {
                val idx = ingredients.indexOf(item.name) // Find its position
                if (idx >= 0) list[idx] = true // Mark as collected
            }
        }

        // Finished if every required ingredient is collected
        val done = newChecked.all { it }

        // If done, record the result once (COMPLETED)
        if (done) recordOnce(Stage1EndReason.COMPLETED)

        // update state to the UI
        _state.value = s.copy(
            selectedNames = newSelected,
            checked = newChecked,
            finished = done
        )
    }

    // Must be called when leaving the screen to stop background loops
    fun clear() { scope.cancel() }

    // conveyor loop: moves the belt every 'tickMillis', and when passing 'stride',
    // shifts items (remove the oldest, add a new one at the front)
    private fun runBeltLoop() = scope.launch {
        while (isActive && !_state.value.finished && _state.value.timeLeft > 0) {
            delay(tickMillis)
            var s = _state.value // take current state
            var newOffset = s.offset + speedPerTick // calculate new offset
            var newList = s.movingItems

            // If we passed a full stride: perform a "step" (shift items)
            if (newOffset >= s.stride) {
                newOffset -= s.stride
                if (newList.isNotEmpty()) {
                    val mutable = newList.toMutableList()
                    mutable.removeAt(mutable.lastIndex) // Remove the oldest item

                    // Build a "recently used" set to avoid close repeats
                    val recentForbidden = mutable.take(minGapRecent).map { it.name }.toSet()

                    // Pick a valid next item (respects spacing + not already collected)
                    val next = drawNext(
                        forbiddenRecent = recentForbidden,
                        selected = s.selectedNames
                    )
                    // Add new item at the front (head)
                    mutable.add(0, next)
                    newList = mutable
                }
            }

            // Publish updated belt state
            _state.value = s.copy(offset = newOffset, movingItems = newList)
        }
    }

    // Timer loop: decrease time every second; if it reaches zero and not finished,
    // mark finished and record TIME_UP.
        private fun runTimerLoop() = scope.launch {
        while (isActive && !_state.value.finished && _state.value.timeLeft > 0) {
            delay(1_000) // one second
            _state.value = _state.value.copy(timeLeft = _state.value.timeLeft - 1)
        }
        // If time ended and game wasn't completed, finish and record TIME_UP
        if (!_state.value.finished) {
            _state.value = _state.value.copy(finished = true)
            recordOnce(Stage1EndReason.TIME_UP)
        }
    }

    // Record the result once (avoid double submissions).
    private fun recordOnce(reason: Stage1EndReason) {
        if (recorded) return
        recorded = true

        // How many seconds actually elapsed
        val elapsed = timeLimitSeconds - _state.value.timeLeft
        val elapsedSec = if (elapsed < 0) 0 else elapsed

        // send to the report the result ot the stage
        // (name of recipe, how many correct\wrong, why it ended, time of play)
        SessionRecorder.recordStage1(
            Stage1Result(
                recipeId = recipeId,
                correctClicks = correctClicks,
                wrongClicks = wrongClicks,
                endReason = reason,
                elapsedSeconds = elapsedSec
            )
        )
    }

    // Build the item catalog: correct items for this recipe + all other items as distractors.
    private fun buildCatalogForRecipe(correct: List<String>, universe: Set<String>): List<ProductItem> {
        val correctSet = correct.toSet()
        val distractors = universe - correctSet
        val correctItems = correct.map { ProductItem(it, true) }
        val wrongItems   = distractors.map { ProductItem(it, false) }
        return correctItems + wrongItems
    }
}
