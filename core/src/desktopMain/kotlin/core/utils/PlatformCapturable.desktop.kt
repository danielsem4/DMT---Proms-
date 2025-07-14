package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun platformCapturable(
    modifier: Modifier,
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper {
    content()

    return CapturableWrapper(
        capture = { }
    )
}