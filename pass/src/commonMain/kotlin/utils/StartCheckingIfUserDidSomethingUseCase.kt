package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * UseCase used in DialScreen to monitor user inactivity.
 * Triggers reminders after 15 seconds without interaction, up to a max number of attempts.
 */

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

