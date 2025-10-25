package com.example.finalprojectnew.di

import com.example.finalprojectnew.stage2.data.repository.InMemoryCartRepository
import com.example.finalprojectnew.stage2.domain.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import com.example.finalprojectnew.assessment.stage2.GroundTruth

// Single place to create/provide Stage2 services (cart, alerts, monitors)
object Stage2Locator {

    // Build the cart repository only when first used
    private val cartRepo by lazy { InMemoryCartRepository() }

    // Use cases facade for the cart (observe/add/update/remove/clear)
    val cart by lazy { CartUseCases(cartRepo) }

    // App-wide coroutine scope for background work
    // SupervisorJob = one thing failure dont cancel the others
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // POP UP: Aunt yaffa"
    // Internal: a stream to emit popup events (buffer size 1 just in case UI is busy)
    private val _globalAuntAlert = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    // Public read-only stream. UI collects this and shows the dialog.
    val globalAuntAlert: SharedFlow<Unit> = _globalAuntAlert

    // do it each threshold only once per session
    private var firedAt2 = false
    private var firedAt4 = false

    // The running job that monitors the cart (null when not running)
    private var monitorJob: Job? = null

    // Start listening to the cart. When user picks 2 and then 4 correct items -> emit alerts.
    fun startStage2Monitors() {
        // Prevent double-start
        if (monitorJob != null) return

        monitorJob = scope.launch {
            // Keys of the "correct" answers
            val correctIds = GroundTruth.shoppingExpected.keys

            // Observe the cart continuously
            cart.observe.collectLatest { items ->
                // Count how many correct items are currently in cart (quantity > 0)
                val countCorrect = items.count { it.product.id in correctIds && it.quantity > 0 }

                // Threshold 1: first alert when reaching 2 correct items
                if (!firedAt2 && countCorrect >= 2) {
                    firedAt2 = true
                    _globalAuntAlert.tryEmit(Unit) // tell UI to show the popup
                }

                // Threshold 2: second alert when reaching 4 correct items
                if (!firedAt4 && countCorrect >= 4) {
                    firedAt4 = true
                    _globalAuntAlert.tryEmit(Unit) // tell UI to show the popup
                }
            }
        }
    }

    // Stop listening (cancel the background job)
    fun stopStage2Monitors() {
        monitorJob?.cancel()
        monitorJob = null
    }

    // Reset internal flags so alerts can shown again on a new session
    fun resetStage2Alerts() {
        firedAt2 = false
        firedAt4 = false
    }

    // POP UP: shmarim cake!
    // The screen checks these counters when entering the "pastries" category.
    // If shown count < max, so it shows the pastries dialog and increments the count.
    var pastriesAdShownCount: Int = 0
    var pastriesAdMaxTimes: Int = 2

    // Call this to allow showing the promo again from zero
    fun resetPastriesAdCounter() {
        pastriesAdShownCount = 0
    }
}
