package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.home.HomeScreen

@Composable
fun App() {

//    MaterialTheme {
//        KoinContext { Navigator(UnderstandingScreen()) { navigator ->
//            BottomToTopTransition(navigator = navigator)
//        } }
//        KoinContext { Navigator(EntryScreen()) { navigator ->
//            LeftToRightTransition(navigator = navigator)
//        } }
//    }
    MaterialTheme {
        Navigator(HomeScreen())
    }
}