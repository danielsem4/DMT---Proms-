package org.example.hit.heal.hitber.presentation.understanding.components

import android.media.MediaPlayer

actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    // מטרה להחזיר callback על סיום השמיעה
    actual fun play(url: String, onCompletion: () -> Unit) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
            setOnCompletionListener {
                onCompletion()  // תקרא ל-callback כשהשמיעה מסתיימת
            }
        }
    }

    actual fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
