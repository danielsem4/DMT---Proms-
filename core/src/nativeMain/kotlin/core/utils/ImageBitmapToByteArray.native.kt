package core.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.Data
import org.jetbrains.skia.Image

actual fun ImageBitmap.toByteArray(): ByteArray {
    val skiaImage = Image.makeFromBitmap(this.asSkiaBitmap())
    val encoded: Data = skiaImage.encodeToData() ?: error("Failed to encode image")
    return encoded.bytes
}