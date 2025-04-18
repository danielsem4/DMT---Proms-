package org.example.hit.heal.app

import androidx.compose.runtime.Composable
import org.example.hit.heal.evaluations.domain.EvaluationViewModel
import org.example.hit.heal.evaluations.presentaion.EvaluationNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = EvaluationViewModel()
    EvaluationNavigator(viewModel) {
        // Handle evaluation completion
        println("Evaluation completed")
    }
}