package org.example.hit.heal

import MedicationScreen
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.presentaion.screens.AlarmReportMedicationScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = AlarmReportMedicationScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}

