package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinContext
import presentation.entryScreen.EntryScreen
import utils.LeftToRightTransition


@Composable
fun App() {
    MaterialTheme {
        KoinContext { Navigator(EntryScreen()) { navigator ->
            LeftToRightTransition(navigator = navigator)
        } }
    }
}