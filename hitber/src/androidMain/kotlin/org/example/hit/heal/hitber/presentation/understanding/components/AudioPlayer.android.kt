package org.example.hit.heal.hitber.presentation.understanding.components

import android.media.MediaPlayer

actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun play(url: String, onCompletion: () -> Unit) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
            setOnCompletionListener {
                onCompletion()
            }
        }
    }
}
