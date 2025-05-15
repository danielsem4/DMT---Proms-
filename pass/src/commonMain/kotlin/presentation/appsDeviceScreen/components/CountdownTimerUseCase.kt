import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownTimerUseCase(private val scope: CoroutineScope) {

    private var dialogJob: Job? = null

    fun execute(
        countdownStart: Int,
        onCountdownTick: (Int) -> Unit,
        onCountdownFinished: () -> Unit
    ) {
        dialogJob?.cancel()

        dialogJob = scope.launch {
            val audioDuration = 10 - countdownStart - 1
            delay(audioDuration * 1000L)

            var remainingTime = countdownStart

            while (remainingTime >= 0) {
                onCountdownTick(remainingTime)
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
