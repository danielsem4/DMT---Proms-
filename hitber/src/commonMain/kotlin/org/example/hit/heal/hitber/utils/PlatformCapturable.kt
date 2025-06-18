package org.example.hit.heal.hitber.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

class CapturableWrapper(
    val capture: () -> Unit
)

@Composable
expect fun PlatformCapturable(
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper

