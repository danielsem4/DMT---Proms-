import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.ToastDuration
import org.example.hit.heal.core.presentation.ToastType
import org.example.hit.heal.core.presentation.custom_ui.ToastView

/**
 * A simple, customizable toast system for Compose Multiplatform.
 */

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ToastMessage(
    message: String,
    type: ToastType = ToastType.Normal,
    alignUp: Boolean = false,
    duration: ToastDuration = ToastDuration.MEDIUM,
    onDismiss: () -> Unit
) {
    val visible = remember { mutableStateOf(false) }

    val delayBeforeOnDismiss: Long = 200

    LaunchedEffect(message) {
        visible.value = true
        delay(duration.millis)
        visible.value = false
        delay(delayBeforeOnDismiss) // let animation finish
        onDismiss()
    }

    Popup(
        alignment = if (alignUp) Alignment.TopCenter else Alignment.BottomCenter,
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = false)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    awaitPointerEventScope {
                        awaitFirstDown()
                    }
                visible.value = false
                GlobalScope.launch {
                    delay(delayBeforeOnDismiss)
                        onDismiss()
                    }
            }, contentAlignment = if (alignUp) Alignment.TopCenter else Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = visible.value, enter = fadeIn(), exit = fadeOut()
            ) {
                ToastView(
                    message = message,
                    type = type,
                )
            }
        }
    }
}
