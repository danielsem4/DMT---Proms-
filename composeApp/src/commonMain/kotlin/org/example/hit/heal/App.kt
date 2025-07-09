package org.example.hit.heal

import LoginScreen
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator



@Composable
fun App(context: Any? = null) {
//    NavigationGraph()
    MaterialTheme {
       Navigator(LoginScreen())


    }
}