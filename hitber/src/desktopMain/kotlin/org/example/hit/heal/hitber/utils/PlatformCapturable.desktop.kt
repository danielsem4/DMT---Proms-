package org.example.hit.heal.hitber.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun PlatformCapturable(
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper {
    content()

    return CapturableWrapper(
        capture = { }
    )
}

