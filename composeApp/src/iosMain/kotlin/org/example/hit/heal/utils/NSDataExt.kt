package org.example.hit.heal.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.cinterop.refTo
import platform.Foundation.NSData
import platform.posix.memcpy

@ExperimentalForeignApi
fun NSData.toByteArray(): ByteArray {
    val size = this.length.toInt()
    val byteArray = ByteArray(size)
    memcpy(byteArray.refTo(0), this.bytes, size.convert())
    return byteArray
}
