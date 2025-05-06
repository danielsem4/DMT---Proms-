//package org.example.hit.heal.navigation
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//
//class NavigationViewModel : ViewModel() {
//    private val _currentRoute = mutableStateOf(Screen.Login.route)
//    val currentRoute: State<String> = _currentRoute
//
//    fun navigateTo(route: String) {
//        _currentRoute.value = route
//    }
//}