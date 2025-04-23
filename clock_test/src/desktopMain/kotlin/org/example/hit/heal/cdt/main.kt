package org.example.hit.heal.cdt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.hit.heal.cdt.presentation.App

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DMT - Proms",
        ) {
            App()
        }
    }
}