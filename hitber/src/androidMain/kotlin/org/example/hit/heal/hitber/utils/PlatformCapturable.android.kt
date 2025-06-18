package org.example.hit.heal.hitber.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import io.github.suwasto.capturablecompose.Capturable
import io.github.suwasto.capturablecompose.rememberCaptureController

@Composable
actual fun PlatformCapturable(
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper {
    val controller = rememberCaptureController()

    Capturable(
        captureController = controller,
        onCaptured = onCaptured
    ) {
        content()
    }

    return CapturableWrapper(
        capture = { controller.capture() }
    )
}

