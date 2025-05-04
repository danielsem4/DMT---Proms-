package org.example.hit.heal

import androidx.compose.ui.window.ComposeUIViewController
import org.example.hit.heal.di.initKoin


fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) { App() }