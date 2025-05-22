package org.example.hit.heal

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import core.data.storage.createDataStore
import org.example.hit.heal.di.initKoin


fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) { App() }