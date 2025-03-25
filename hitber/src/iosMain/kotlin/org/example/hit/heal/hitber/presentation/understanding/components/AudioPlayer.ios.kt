package org.example.hit.heal.hitber.presentation.understanding.components

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSURL
import platform.darwin.NSObject

// מחלקה המיישמת את ה-Delegate
class AudioPlayerDelegate(val onCompletion: () -> Unit) : NSObject(), AVAudioPlayerDelegateProtocol {
    override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
        // כששמיעת האודיו מסתיימת, נקרא ל-onCompletion
        onCompletion()
    }
}

actual class AudioPlayer {
    private var player: AVAudioPlayer? = null
    private var delegate: AudioPlayerDelegate? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun play(url: String, onCompletion: () -> Unit) {
        val nsUrl = NSURL(string = url)
        player = AVAudioPlayer(contentsOfURL = nsUrl, error = null)

        // יצירת delegate עם ה-callback
        delegate = AudioPlayerDelegate(onCompletion)

        // הגדרת ה-delegate לשחקן
        player?.delegate = delegate

        player?.play()
    }

    actual fun stop() {
        player?.stop()
        player = null
        delegate = null
    }
}
