package core.utils

import androidx.compose.runtime.Composable

@Composable
expect fun ObserveLifecycle(onStop: () -> Unit, onStart: () -> Unit = {})



