package core.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.*
import platform.AVFAudio.*
import platform.Foundation.*
import platform.darwin.NSObject

actual class AudioPlayer {
    private var player: AVAudioPlayer? = null
    private var delegate: AudioPlayerDelegate? = null
    private val scope = MainScope()

    @OptIn(ExperimentalForeignApi::class)
    actual fun play(url: String, onCompletion: () -> Unit) {
        val nsUrl = NSURL(string = url)

        scope.launch {
            val data = withContext(Dispatchers.Default) {
                NSData.dataWithContentsOfURL(nsUrl)
            } ?: return@launch

            val tempFilePath = NSTemporaryDirectory() + "temp_audio.mp3"
            val success = data.writeToFile(tempFilePath, atomically = true)
            if (!success) return@launch

            val fileUrl = NSURL.fileURLWithPath(tempFilePath)
            player = AVAudioPlayer(fileUrl, null).apply {
                delegate = AudioPlayerDelegate(onCompletion)
                this.delegate = delegate
                prepareToPlay()
                play()
            }
        }
    }

    actual fun stop() {
        player?.let {
            if (it.isPlaying()) it.stop()
        }
        player = null
        delegate = null
    }

    private class AudioPlayerDelegate(val onCompletion: () -> Unit) : NSObject(), AVAudioPlayerDelegateProtocol {
        override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
            onCompletion()
        }
    }
}
