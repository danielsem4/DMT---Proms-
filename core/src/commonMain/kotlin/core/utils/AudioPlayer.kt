package core.utils

expect class AudioPlayer() {
    suspend fun play(url: String, onCompletion: () -> Unit)
    fun stop()
}
