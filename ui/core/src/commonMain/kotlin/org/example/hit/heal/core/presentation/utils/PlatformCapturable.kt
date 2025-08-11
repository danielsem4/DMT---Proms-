package org.example.hit.heal.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

class CapturableWrapper(
    val capture: () -> Unit
)

@Composable
expect fun platformCapturable(
    modifier: Modifier = Modifier,
    onCaptured: (ImageBitmap) -> Unit,
    content: @Composable () -> Unit
): CapturableWrapper


