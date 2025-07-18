package core.utils

import android.media.MediaPlayer
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual suspend fun play(url: String, onCompletion: () -> Unit) {
        stop() // stop any existing playback
        suspendCancellableCoroutine<Unit> { continuation ->
            try {
                val player = MediaPlayer().apply {
                    setDataSource(url)
                    setOnPreparedListener {
                        it.start()
                        continuation.resume(Unit)
                    }
                    setOnCompletionListener {
                        onCompletion()
                    }
                    setOnErrorListener { _, what, extra ->
                        continuation.resumeWithException(
                            Exception("MediaPlayer error: what=$what, extra=$extra")
                        )
                        true
                    }
                    prepareAsync()
                }
                mediaPlayer = player
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    actual fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
    }
}