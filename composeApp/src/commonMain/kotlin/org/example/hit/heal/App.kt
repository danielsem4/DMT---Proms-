package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.splash.SplashScreen
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
        Navigator(SplashScreen()) { navigator ->
            SlideTransition(navigator) // This defines the animation for ALL transitions
        }
    }
}