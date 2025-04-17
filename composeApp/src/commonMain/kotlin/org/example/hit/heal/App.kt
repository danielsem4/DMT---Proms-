package org.example.hit.heal

import androidx.compose.runtime.Composable
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
@Preview
fun App(
    platformModule: Module = Module()
) {
    KoinApplication(application = {
        modules(appModule, platformModule)
    }){
        com.clock.test.presentation.App()
    }
}