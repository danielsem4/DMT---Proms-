package org.example.hit.heal


import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.core.presentation.AppTheme
import org.example.hit.heal.presentation.splash.SplashScreen

@Composable
fun App() {
    AppTheme {
        val screen = SplashScreen()
        Navigator(screen) { navigator ->
            SlideTransition(navigator)
        }
    }
}