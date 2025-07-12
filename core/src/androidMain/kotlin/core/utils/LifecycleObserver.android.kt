package core.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.*
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
actual fun ObserveLifecycle(onStop: () -> Unit, onStart: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val observer = remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> onStop()
                Lifecycle.Event.ON_START -> onStart()
                else -> {}
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

