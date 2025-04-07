package org.example.hit.heal

import androidx.compose.runtime.Composable

import org.example.hit.heal.navigation.NavigationGraph
import org.example.hit.heal.navigation.navigationModule
import org.koin.compose.KoinApplication

import org.example.hit.heal.core.di.platformModule
import org.example.hit.heal.core.di.sharedModule

@Composable
fun App() {
    KoinApplication(application = {
//        modules(sharedModule, platformModule)
        modules(sharedModule, platformModule, navigationModule)
    }) {
        NavigationGraph()
    }
}