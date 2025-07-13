package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.hitber.presentation.entry.HitberEntryScreen
import org.example.hit.heal.presentation.splash.SplashScreen
import presentation.dialScreen.DialScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(SplashScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}