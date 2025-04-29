package org.example.hit.heal.cdt.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        Navigator(screen = LandingScreen()) { navigator ->
            FadeTransition(navigator)
        }
    }
}