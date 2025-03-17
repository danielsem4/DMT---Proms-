package org.example.hit.heal

import LoginScreen
import androidx.compose.runtime.*
import org.example.hit.heal.Home.HomeScreen

import androidx.compose.runtime.Composable
//import org.example.hit.heal.core.presentation.SampleScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = {
                isLoggedIn = true
            }
        )
    } else {
        HomeScreen(
            onLogout = {
                isLoggedIn = false
            }
        )
    }
}