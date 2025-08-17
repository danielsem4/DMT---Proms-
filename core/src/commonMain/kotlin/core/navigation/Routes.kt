package core.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {
    @Serializable
    data object Home : AppDestination
   @Serializable
    data class Details(val id: String) : AppDestination
    @Serializable
    data object Login : AppDestination
}