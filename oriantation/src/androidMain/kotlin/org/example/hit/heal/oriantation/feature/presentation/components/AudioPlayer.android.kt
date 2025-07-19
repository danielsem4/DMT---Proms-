package org.example.hit.heal.hitber.presentation.understanding.components

import android.media.MediaPlayer

//actual class AudioPlayer actual constructor() {
//    actual fun play(url: String, onCompletion: () -> Unit) {
//    }
//}
actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual fun play(url: String, onCompletion: () -> Unit) {
        try {
            // Stop any existing playback
            stop()

            // Create and start new MediaPlayer
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepare()
                setOnCompletionListener {
                    onCompletion()
                }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }
}