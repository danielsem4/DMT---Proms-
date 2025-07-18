package core.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.*
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.Foundation.*
import platform.darwin.NSObjectProtocol
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
actual class AudioPlayer {
    private var player: AVPlayer? = null
    private var observer: NSObjectProtocol? = null

    actual suspend fun play(url: String, onCompletion: () -> Unit) {
        stop()

        val nsUrl = NSURL.URLWithString(url)
            ?: throw IllegalArgumentException("Invalid URL: $url")

        val playerItem = AVPlayerItem(nsUrl)
        player = AVPlayer(playerItem = playerItem)

        suspendCancellableCoroutine<Unit> { continuation ->
            observer = NSNotificationCenter.defaultCenter.addObserverForName(
                name = AVPlayerItemDidPlayToEndTimeNotification,
                `object` = playerItem,
                queue = NSOperationQueue.mainQueue
            ) {
                onCompletion()
            }

            player?.play()
            continuation.resume(Unit)
        }
    }

    actual fun stop() {
        player?.pause()
        player = null

        observer?.let {
            NSNotificationCenter.defaultCenter.removeObserver(it)
            observer = null
        }
    }
}