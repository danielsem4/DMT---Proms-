package org.example.hit.heal.hitber.utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun ByteArray.toBase64(): String {
    return this.usePinned { pinned ->
        val nsData = NSData.create(
            bytes = pinned.addressOf(0),
            length = size.toULong()
        )
        nsData.base64EncodedStringWithOptions(0u)
    }
}
