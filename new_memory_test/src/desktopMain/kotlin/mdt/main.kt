package org.example.hit.heal.mdt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.hit.heal.mdt.presentation.MDTApp

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DMT - Proms",
        ) {
            MDTApp()
        }
    }
}