package presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinContext
import presentation.entryScreen.EntryScreen
import org.example.hit.heal.core.presentation.utils.animations.LeftToRightTransition

class PassScreen : Screen {
    @Composable
    override fun Content() {
        KoinContext {
            Navigator(EntryScreen()) { navigator ->
                LeftToRightTransition(navigator)
            }
        }
    }
}
