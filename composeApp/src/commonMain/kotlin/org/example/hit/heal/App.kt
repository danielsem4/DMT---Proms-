package org.example.hit.heal

import MedicationScreen
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(
            screen = MedicationScreen(),
            content = { navigator ->
                SlideTransition(navigator)
            }
            // Other navigator options...
            //screen =BaseScreen()
        )
    }
}

