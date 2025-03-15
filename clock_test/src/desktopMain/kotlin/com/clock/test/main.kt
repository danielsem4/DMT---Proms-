package com.clock.test

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.clock.test.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DMT - Proms",
    ) {
        App()
    }
}