package com.clock.test.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition

@Composable
fun App() {
    Navigator(screen = LandingScreen()) { navigator ->
        FadeTransition(navigator)
    }
}