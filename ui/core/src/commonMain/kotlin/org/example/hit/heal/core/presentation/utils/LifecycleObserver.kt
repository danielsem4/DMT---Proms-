package org.example.hit.heal.core.presentation.utils

import androidx.compose.runtime.Composable

@Composable
expect fun ObserveLifecycle(onStop: () -> Unit, onStart: () -> Unit = {})



