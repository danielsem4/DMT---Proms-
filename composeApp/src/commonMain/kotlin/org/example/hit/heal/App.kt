package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.hitber.presentation.entry.HitberEntryScreen
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen
import org.example.hit.heal.presentation.splash.SplashScreen
import presentation.dialScreen.DialScreen
import presentation.endScreen.EndScreen
import presentation.entryScreen.PassEntryScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(SplashScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}