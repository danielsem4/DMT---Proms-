package org.example.hit.heal.hitber.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.Image


actual fun ImageBitmap.toByteArray(): ByteArray {
    val skiaBitmap = this.asSkiaBitmap()
    val image = Image.makeFromBitmap(skiaBitmap)
    val data = image.encodeToData()
    requireNotNull(data) { "Failed to encode image to data" }
    return data.bytes
}


