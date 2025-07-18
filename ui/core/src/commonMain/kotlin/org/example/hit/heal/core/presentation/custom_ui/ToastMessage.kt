package org.example.hit.heal.core.presentation.custom_ui

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.example.hit.heal.core.presentation.ToastType

/**
 * A simple, customizable toast system for Compose Multiplatform.
 */

@Composable
fun ToastMessage(
    message: String,
    type: ToastType = ToastType.Normal,
    alignUp: Boolean = false,
    onDismiss: () -> Unit
) {

    LaunchedEffect(message) {
        kotlinx.coroutines.delay(3000)
        onDismiss()
    }
    Popup(
        alignment = if (alignUp) Alignment.TopCenter else Alignment.BottomCenter,
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        onDismiss()
                    }
                },
            contentAlignment = if (alignUp) Alignment.TopCenter else Alignment.BottomCenter
        ) {
            ToastView(
                message = message,
                type = type,
            )
        }
    }
}