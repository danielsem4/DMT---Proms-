package org.example.hit.heal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.hit.heal.domain.EvaluationViewModel
import org.example.hit.heal.presentaion.EvaluationsList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = remember { EvaluationViewModel() }
    EvaluationsList(viewModel, onItemClick = { evaluation ->
        println("Clicked: ${evaluation.name}")
    },{},{})
}