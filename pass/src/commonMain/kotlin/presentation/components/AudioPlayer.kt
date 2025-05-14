package presentation.components

expect class AudioPlayer() {
    fun play(url: String, onCompletion: () -> Unit)
}