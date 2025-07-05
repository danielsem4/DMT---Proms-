package presentation.dialScreen.components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StartCheckingIfUserDidSomethingUseCase(
    private val coroutineScope: CoroutineScope,
    private val getReminderDidNotingText: () -> Unit,
    private val updateCloseIconDialog: (Boolean) -> Unit
) {

    private var reminderJob: Job? = null

    fun start(
        getDidNothingCount: () -> Int,
        maxAttempts: Int,
        intervalSeconds: Int = 15,
        showDialog: () -> Boolean
    ) {
        reminderJob?.cancel()

        var elapsedSeconds = 0

        reminderJob = coroutineScope.launch {

            while (isActive && getDidNothingCount() <= maxAttempts) {
                delay(1_000)

                if (showDialog()) {
                    continue
                }

                elapsedSeconds++

                if (elapsedSeconds >= intervalSeconds) {
                    getReminderDidNotingText()
                    updateCloseIconDialog(true)
                    elapsedSeconds = 0
                }
            }
        }
    }

    fun stop() {
        reminderJob?.cancel()
        reminderJob = null
    }
}

