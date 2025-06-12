package com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.player

import androidx.compose.runtime.Composable

@Composable
expect fun rememberSimplePlayer(resourceName: String): SimplePlayer

interface SimplePlayer {
    fun play()
    fun stop()
}