package org.example.hit.heal

import androidx.compose.runtime.Composable
import org.example.hit.heal.navigation.NavigationGraph
import presentation.contatcts.ContactsScreen
import cafe.adriel.voyager.navigator.Navigator
import presentation.dialScreen.DialScreen
import presentation.endScreen.EndScreen
import presentation.entryScreen.EntryScreen

@Composable
fun App(context: Any? = null) {
    Navigator(EntryScreen())
}