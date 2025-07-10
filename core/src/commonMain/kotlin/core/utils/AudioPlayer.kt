package core.utils

expect class AudioPlayer() {
    fun play(url: String, onCompletion: () -> Unit)
    fun stop()
}