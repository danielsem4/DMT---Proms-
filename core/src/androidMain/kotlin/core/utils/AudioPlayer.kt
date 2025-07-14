package core.utils

import android.media.MediaPlayer
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    actual suspend fun play(url: String, onCompletion: () -> Unit) {
        mediaPlayer = suspendCancellableCoroutine { continuation ->
            val player = MediaPlayer()
            mediaPlayer = player
            try {
                player.setDataSource(url)
                player.setOnPreparedListener {
                    it.start()
                    continuation.resume(player)
                }
                player.setOnCompletionListener {
                    onCompletion()
                }
                player.prepareAsync()
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }

    actual fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }
}
