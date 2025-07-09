package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.hitber.presentation.HitberScreen
import org.example.hit.heal.hitber.presentation.naming.NamingScreen
import presentation.entryScreen.PassEntryScreen

@Composable
fun App() {
        MaterialTheme {
            Navigator(NamingScreen()) { navigator ->
                SlideTransition(navigator) // This defines the animation for ALL transitions
            }
        }
    }
