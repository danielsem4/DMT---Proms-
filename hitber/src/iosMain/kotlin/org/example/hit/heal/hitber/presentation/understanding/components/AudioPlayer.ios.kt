package org.example.hit.heal.hitber.presentation.understanding.components

// קובץ ב-iosMain
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class AudioPlayer {
    private var player: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun play(url: String) {
        val url = NSURL(string = url)
        player = AVAudioPlayer(contentsOfURL = url, error = null)
        player?.play()
    }

    actual fun stop() {
        player?.stop()
        player = null
    }
}

