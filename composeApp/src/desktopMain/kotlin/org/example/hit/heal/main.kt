package org.example.hit.heal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.hit.heal.core.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DMT - Proms",
        ) {
            App()
        }
    }
}