package com.example.finalprojectnew.di

import com.example.finalprojectnew.stage2.data.repository.InMemoryCartRepository
import com.example.finalprojectnew.stage2.domain.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import com.example.finalprojectnew.assessment.stage2.GroundTruth

object Stage2Locator {
    // --- Repositories ---
    private val cartRepo     by lazy { InMemoryCartRepository() }

    // --- Use Cases ---

    val cart                  by lazy { CartUseCases(cartRepo) }

    // ------------------------------------------------------------------
    //        🔔 לוגיקה חדשה: “הודעת דודה יפה” לפי ספי 2 ו-4 פריטים נכונים
    // ------------------------------------------------------------------

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _globalAuntAlert = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val globalAuntAlert: SharedFlow<Unit> = _globalAuntAlert

    private var firedAt2 = false
    private var firedAt4 = false
    private var monitorJob: Job? = null

    fun startStage2Monitors() {
        if (monitorJob != null) return
        monitorJob = scope.launch {
            val correctIds = GroundTruth.shoppingExpected.keys
            cart.observe.collectLatest { items ->
                val countCorrect = items.count { it.product.id in correctIds && it.quantity > 0 }

                if (!firedAt2 && countCorrect >= 2) {
                    firedAt2 = true
                    _globalAuntAlert.tryEmit(Unit)
                }
                if (!firedAt4 && countCorrect >= 4) {
                    firedAt4 = true
                    _globalAuntAlert.tryEmit(Unit)
                }
            }
        }
    }

    fun stopStage2Monitors() {
        monitorJob?.cancel()
        monitorJob = null
    }

    fun resetStage2Alerts() {
        firedAt2 = false
        firedAt4 = false
    }

    // --- ישנים (נשארו לתאימות, לא בשימוש) ---
    @Deprecated("Replaced by new global alerts")
    var cartAlertShownCount: Int = 0
    @Deprecated("Replaced by new global alerts")
    var cartAlertMaxTimes: Int = 2
    fun resetCartAlertCounter() { cartAlertShownCount = 0 }

    var pastriesAdShownCount: Int = 0
    var pastriesAdMaxTimes: Int = 2
    fun resetPastriesAdCounter() { pastriesAdShownCount = 0 }
}
