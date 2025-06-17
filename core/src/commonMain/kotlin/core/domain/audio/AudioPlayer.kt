package core.domain.audio

expect class AudioPlayer() {
    fun play(url: String, onCompletion: () -> Unit)
}