package com.clock.test.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Navigator(screen = LandingScreen()) { navigator ->
        LaunchedEffect(Unit) {
            delay(100)
            navigator.push(ClockTestScreen(onStartClick = {
                navigator.push(DrawClockScreen(onFinishClick = {

                }))
            }))
        }
        FadeTransition(navigator)
    }

}