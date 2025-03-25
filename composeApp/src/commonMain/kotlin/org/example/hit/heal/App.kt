package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {

    MaterialTheme {
        KoinContext { Navigator (screen = UnderstandingScreen()) }
    }


}