package org.example.hit.heal

import androidx.compose.runtime.Composable
import di.appModule
import org.example.hit.heal.cdt.di.platformModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.example.hit.heal.cdt.presentation.App as AppCdt

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule, platformModule)
    }) {
        AppCdt()
    }
}