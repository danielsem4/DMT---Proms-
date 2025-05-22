package presentation.components

expect class AudioPlayerMp3 {
    fun playMp3(onCompletion: () -> Unit)
}