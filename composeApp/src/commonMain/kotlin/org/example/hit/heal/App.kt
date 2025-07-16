package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.hitber.presentation.buildShape.BuildShapeScreen
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes
import org.example.hit.heal.hitber.presentation.entry.HitberEntryScreen
import org.example.hit.heal.hitber.presentation.summary.SummaryScreen
import org.example.hit.heal.presentation.splash.SplashScreen


@Composable
fun App() {
    MaterialTheme {
        Navigator(SplashScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}