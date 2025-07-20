package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.oriantation.feature.presentation.NumberSelectScreen
import org.example.hit.heal.oriantation.feature.presentation.OriantationWelcomeScreen
import org.example.hit.heal.presentation.splash.SplashScreen


@Composable
fun App() {
    MaterialTheme {
        val screen = SplashScreen()
        Navigator(screen) { navigator ->
            SlideTransition(navigator)
        }
    }
}