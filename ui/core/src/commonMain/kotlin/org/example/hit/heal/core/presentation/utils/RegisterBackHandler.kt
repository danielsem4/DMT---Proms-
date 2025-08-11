package org.example.hit.heal.core.presentation.utils

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

@Composable
expect fun RegisterBackHandler(screen: Screen, onPop: () -> Unit)

