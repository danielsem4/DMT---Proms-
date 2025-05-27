package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.hit.heal.oriantation.feature.presentation.DrawScreen
import org.example.hit.heal.oriantation.feature.presentation.FeedbackScreen
import org.example.hit.heal.oriantation.feature.presentation.ShapeResizeScreen
import org.example.hit.heal.oriantation.feature.presentation.ShapesDragScreen


@Composable
fun App(context: Any? = null) {
//    NavigationGraph()
    MaterialTheme {
       Navigator(ShapeResizeScreen())


    }
}