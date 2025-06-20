package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.Home.HomeScreen
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen
import org.example.hit.heal.hitber.utils.BottomToTopTransition
import org.example.hit.heal.splash.SplashScreen
import presentation.entryScreen.EntryScreen

import utils.LeftToRightTransition

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