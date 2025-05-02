package entryScreen

import BaseTabletScreen
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class EntryScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        BaseTabletScreen(title = "אפליקציות המכשיר", content =
        {

        })
    }
}