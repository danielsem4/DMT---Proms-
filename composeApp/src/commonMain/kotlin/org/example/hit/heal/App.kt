package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.hitber.presentation.concentration.ConcentrationScreen
import org.example.hit.heal.hitber.presentation.entry.EntryScreen
import org.example.hit.heal.hitber.presentation.naming.NamingScreen
import org.example.hit.heal.hitber.presentation.summary.SummaryScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {

    MaterialTheme {
        KoinContext { Navigator (screen = NamingScreen()) }
    }


}