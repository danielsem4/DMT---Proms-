package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.splash.SplashScreen

@Composable
fun App() {
        MaterialTheme {
            Navigator(SplashScreen()) { navigator ->
                SlideTransition(navigator) // This defines the animation for ALL transitions
            }
        }
    }
