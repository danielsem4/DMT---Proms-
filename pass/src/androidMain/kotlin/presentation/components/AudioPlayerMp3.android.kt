package presentation.components

import android.content.Context
import android.media.MediaPlayer

actual class AudioPlayerMp3(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    actual fun playMp3(onCompletion: () -> Unit) {
        mediaPlayer?.release()
        val resId = context.resources.getIdentifier("dial_tone", "raw", context.packageName)
        if (resId == 0) {
            println("dial_tone.mp3 not found in res/raw/")
            return
        }

        mediaPlayer = MediaPlayer.create(context, resId).apply {
            setOnCompletionListener {
                onCompletion()
                release()
                mediaPlayer = null
            }
            start()
        }
    }
}