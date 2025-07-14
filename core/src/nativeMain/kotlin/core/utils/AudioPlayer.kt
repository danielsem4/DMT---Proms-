package core.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.*
import platform.AVFAudio.*
import platform.Foundation.*
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual class AudioPlayer {
    private var player: AVAudioPlayer? = null
    private var delegate: AudioPlayerDelegate? = null

    actual suspend fun play(url: String, onCompletion: () -> Unit) {
        stop()

        val nsUrl = NSURL(string = url)
        val data = withContext(Dispatchers.Default) {
            NSData.dataWithContentsOfURL(nsUrl)
        } ?: throw Exception("Failed to load audio data")

        val tempFilePath = NSTemporaryDirectory() + "temp_audio.mp3"
        val success = data.writeToFile(tempFilePath, atomically = true)
        if (!success) throw Exception("Failed to write temp audio file")

        val fileUrl = NSURL.fileURLWithPath(tempFilePath)

        suspendCoroutine<Unit> { continuation ->
            try {
                val audioPlayer = AVAudioPlayer(fileUrl, null)
                val audioDelegate = AudioPlayerDelegate {
                    onCompletion()
                }
                audioPlayer.delegate = audioDelegate
                this.player = audioPlayer
                this.delegate = audioDelegate

                audioPlayer.prepareToPlay()
                val started = audioPlayer.play()
                if (started) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(Exception("Failed to start audio playback"))
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
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
