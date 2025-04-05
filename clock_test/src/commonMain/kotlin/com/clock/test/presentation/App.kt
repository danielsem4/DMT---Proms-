package com.clock.test.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.clock.test.di.createKoinConfiguration
import org.koin.compose.KoinMultiplatformApplication

@Composable
fun App() {
    KoinMultiplatformApplication(
        config = createKoinConfiguration()
    ) {
        Navigator(screen = LandingScreen()) { navigator ->
            FadeTransition(navigator)
        }
    }
}