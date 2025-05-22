package org.example.hit.heal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.data.storage.createDataStore
import core.data.storage.dataStoreFileName
import org.example.hit.heal.di.initKoin

fun main() {
    initKoin()
    val prefs = createDataStore {
        dataStoreFileName
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DMT - Proms",
        ) {
            App()
        }
    }
}