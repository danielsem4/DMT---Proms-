package org.example.hit.heal

import androidx.compose.runtime.Composable
import org.example.hit.heal.di.networkModule
import org.example.hit.heal.navigation.NavigationGraph
import org.example.hit.heal.navigation.navigationModule
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {
        modules(navigationModule, networkModule)
    }) {
        NavigationGraph()
    }
}