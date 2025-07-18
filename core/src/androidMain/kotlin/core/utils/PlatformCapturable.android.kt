package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import io.github.suwasto.capturablecompose.Capturable
import io.github.suwasto.capturablecompose.rememberCaptureController

@Composable
actual fun platformCapturable(
    modifier: Modifier,
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper {
    val controller = rememberCaptureController()

    Capturable(
        modifier = modifier,
        captureController = controller,
        onCaptured = onCaptured
    ) {
        content()
    }

    return CapturableWrapper(
        capture = { controller.capture() }
    )
}
