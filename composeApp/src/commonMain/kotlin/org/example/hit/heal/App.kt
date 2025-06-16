package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import LoginScreen
import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import presentation.entryScreen.EntryScreen
import utils.LeftToRightTransition
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.splash.SplashScreen

@Composable
fun App() {
    MaterialTheme {
        KoinContext { Navigator(EntryScreen()) { navigator ->
            LeftToRightTransition(navigator = navigator)
        } }
    }
    MaterialTheme {
        Navigator(SplashScreen())
    }
}