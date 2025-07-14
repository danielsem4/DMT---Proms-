package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow

class CountdownTimerUseCase(private val scope: CoroutineScope) {

    private var dialogJob: Job? = null

    fun execute(
        isPlaying: StateFlow<Boolean>,
        onCountdownTick: (Int) -> Unit,
        onCountdownFinished: () -> Unit
    ) {
        dialogJob?.cancel()

        dialogJob = scope.launch {
            var remainingTime = 9

            delay(1000L)

            while (remainingTime >= 0) {
                if (!isPlaying.value) {
                    onCountdownTick(remainingTime)
                }

                delay(1000L)
                remainingTime--
            }
            onCountdownFinished()
        }
    }

    fun cancel() {
        dialogJob?.cancel()
    }
}

