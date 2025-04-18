package org.example.hit.heal.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import kotlinx.cinterop.*
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

private const val kCGImageAlphaPremultipliedLastValue: Int = 1
private const val kCGBitmapByteOrder32BigValue: Int = (4 shl 12)

private val bitmapInfo: UInt = (kCGImageAlphaPremultipliedLastValue or kCGBitmapByteOrder32BigValue).toUInt()


@OptIn(ExperimentalForeignApi::class)
actual fun imageBitmapToPngByteArray(image: ImageBitmap): ByteArray {
    val pixelMap = image.toPixelMap()
    val width = pixelMap.width
    val height = pixelMap.height
    val bytesPerPixel = 4
    val bitsPerComponent = 8
    val bytesPerRow = bytesPerPixel * width

    val bufferSize = bytesPerRow * height
    val rawData = nativeHeap.allocArray<ByteVar>(bufferSize)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = pixelMap[x, y]
            val offset = y * bytesPerRow + x * bytesPerPixel
            rawData[offset + 0] = pixel.red.times(255).toInt().toByte()
            rawData[offset + 1] = pixel.green.times(255).toInt().toByte()
            rawData[offset + 2] = pixel.blue.times(255).toInt().toByte()
            rawData[offset + 3] = pixel.alpha.times(255).toInt().toByte()
        }
    }

    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val bitmapInfo: UInt = (1 or (4 shl 12)).toUInt() // manually define bitmapInfo

    val context = CGBitmapContextCreate(
        data = rawData,
        width = width.toULong(),
        height = height.toULong(),
        bitsPerComponent = bitsPerComponent.toULong(),
        bytesPerRow = bytesPerRow.toULong(),
        space = colorSpace,
        bitmapInfo = bitmapInfo
    )


    val cgImage = CGBitmapContextCreateImage(context)
    val uiImage = UIImage.imageWithCGImage(cgImage!!)
    val nsData = UIImagePNGRepresentation(uiImage)

    val byteArray = nsData?.toByteArray() ?: ByteArray(0)

    nativeHeap.free(rawData)

    return byteArray
}
