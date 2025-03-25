package org.example.hit.heal.hitber.presentation.understanding.components

import android.media.MediaPlayer

// קובץ ב-androidMain
actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun play(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
        }
    }

    actual fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

