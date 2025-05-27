package org.example.hit.heal.hitber.presentation.understanding.components

expect class AudioPlayer() {
    fun play(url: String, onCompletion: () -> Unit)
}




