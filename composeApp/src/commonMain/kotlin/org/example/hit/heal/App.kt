package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.core.presentation.utils.animations.BottomToTopTransition
import org.example.hit.heal.core.presentation.utils.animations.LeftToRightTransition
import org.example.hit.heal.hitber.presentation.understanding.UnderstandingScreen
import org.example.hit.heal.splash.SplashScreen
import org.example.hit.heal.home.HomeScreen
import org.koin.compose.KoinContext
import presentation.entryScreen.EntryScreen

@Composable
fun App() {

  //  MaterialTheme {
//        KoinContext { Navigator(UnderstandingScreen()) { navigator ->
//            BottomToTopTransition(navigator = navigator)
//        } }
//        KoinContext { Navigator(EntryScreen()) { navigator ->
//            LeftToRightTransition(navigator = navigator)
//        } }
//    }
        //}
        MaterialTheme {
            Navigator(SplashScreen()) { navigator ->
                SlideTransition(navigator) // This defines the animation for ALL transitions
            }
        }
    }
