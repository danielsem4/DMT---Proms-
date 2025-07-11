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
    ) {
        reminderJob?.cancel()

        reminderJob = coroutineScope.launch {

            if (getDidNothingCount() <= maxAttempts) {
                delay(15_000)

                    getReminderDidNotingText()
                    updateCloseIconDialog(true)
            }
        }
    }

    fun stop() {
        reminderJob?.cancel()
        reminderJob = null
    }
}

