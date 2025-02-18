package org.example.hit.heal

import androidx.compose.runtime.Composable
import org.example.hit.heal.presentaion.EvaluationSlider
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
//    val viewModel = remember { EvaluationViewModel() }
//    EvaluationsList(viewModel, onItemClick = { evaluation ->
//        println("Clicked: ${evaluation.name}")
//    },{},{})
    EvaluationSlider {  }
}