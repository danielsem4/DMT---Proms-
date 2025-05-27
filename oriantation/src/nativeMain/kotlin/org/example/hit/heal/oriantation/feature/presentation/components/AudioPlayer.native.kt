package org.example.hit.heal.hitber.presentation.understanding.components

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSURL
import platform.darwin.NSObject


class AudioPlayerDelegate(val onCompletion: () -> Unit) : NSObject(),
    AVAudioPlayerDelegateProtocol {
    override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
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
        delegate = AudioPlayerDelegate(onCompletion)
        player?.delegate = delegate
        player?.play()
    }
}
