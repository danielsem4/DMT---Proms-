package org.example.hit.heal.evaluations.presentaion

import EvaluationItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.evaluations.domain.EvaluationViewModel

@Composable
fun EvaluationsList(
    viewModel: EvaluationViewModel,
    onEvaluationDone: () -> Unit
) {
    val evaluations by viewModel.evaluations.collectAsState()
    var currentIndex by remember { mutableStateOf(0) }

    if (evaluations.isEmpty()) {
        // Optional: handle empty state
        BaseScreen(title = "Evaluations") {
            Text("No evaluations available")
        }
        return
    }

    val isLastEvaluation = currentIndex == evaluations.lastIndex
    val currentEvaluation = evaluations[currentIndex]

    BaseScreen(
        title = "Evaluations",
        onPrevClick = if (currentIndex > 0) ({ currentIndex-- }) else null,
        onNextClick = if (!isLastEvaluation) ({ currentIndex++ }) else null,
        onDoneClick = if (isLastEvaluation) onEvaluationDone else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            EvaluationItem(
                evaluation = currentEvaluation,
                onItemClick = {}
            )
        }
    }
}
