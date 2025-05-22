package presentation.components

import platform.AVFoundation.*
import platform.Foundation.*
import platform.darwin.NSObject
import kotlinx.cinterop.*
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import kotlin.native.concurrent.ThreadLocal

actual class AudioPlayerMp3 {
    private var player: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun playMp3(onCompletion: () -> Unit) {
        val path = NSBundle.mainBundle.pathForResource("dial_tone", "mp3")
        if (path == null) {
            println("dial_tone.mp3 not found in iOS bundle")
            return
        }

        val url = NSURL.fileURLWithPath(path)
        player = AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
            prepareToPlay()
            play()
            delegate = object : NSObject(), AVAudioPlayerDelegateProtocol {
                override fun audioPlayerDidFinishPlaying(
                    player: AVAudioPlayer,
                    successfully: Boolean
                ) {
                    onCompletion()
                }
            }
        }
    }
}