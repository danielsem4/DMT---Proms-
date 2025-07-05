package org.example.hit.heal.hitber.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.hitber.presentation.entry.HitberEntryScreen
import org.example.hit.heal.core.presentation.utils.animations.BottomToTopTransition
import org.koin.compose.KoinContext

class HitberScreen : Screen {
    @Composable
    override fun Content() {
        KoinContext {
            Navigator(HitberEntryScreen()) { navigator ->
                BottomToTopTransition(navigator)
            }
        }
    }
}
