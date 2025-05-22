package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinContext
import presentation.dialScreen.DialScreen
import presentation.entryScreen.EntryScreen
import utils.LeftToRightTransition


@Composable
fun App(context: Any? = null) {
    Navigator(EntryScreen())

    MaterialTheme {
        KoinContext { Navigator(DialScreen()) { navigator ->
            LeftToRightTransition(navigator = navigator)
        } }
    }
}