package presentation.components

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

        reminderJob = coroutineScope.launch {
            var elapsedTime = 0

            while (isActive && getDidNothingCount() <= maxAttempts) {
                delay(1_000)

                if (showDialog()) {
                    continue
                }

                elapsedTime++

                if (elapsedTime >= intervalSeconds) {
                    getReminderDidNotingText()
                    updateCloseIconDialog(true)
                    elapsedTime = 0
                }
            }
        }
    }

    fun stop() {
        reminderJob?.cancel()
    }
}

