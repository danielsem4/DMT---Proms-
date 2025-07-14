package core.utils

actual class AudioPlayer actual constructor() {
    actual suspend fun play(url: String, onCompletion: () -> Unit) {
    }

    actual fun stop() {
    }

}