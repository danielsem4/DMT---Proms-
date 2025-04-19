package org.example.hit.heal.evaluations.presentaion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.evaluations.domain.EvaluationViewModel

@Composable
fun EvaluationNavigator(
    viewModel: EvaluationViewModel,
    onComplete: () -> Unit
) {
    val evaluations by viewModel.evaluations.collectAsState()
    val allQuestions = remember(evaluations) {
        evaluations.flatMap { it.evaluationObjects }
    }

    var currentIndex by remember { mutableStateOf(0) }
    val currentObject = allQuestions.getOrNull(currentIndex) ?: return
    val isFirst = currentIndex == 0
    val isLast = currentIndex == allQuestions.lastIndex

    // Track the live answer from ViewModel
    val answer = remember(currentObject.id, evaluations) {
        currentObject.answer
    }

    val currentEval = evaluations.find { it.evaluationObjects.contains(currentObject) }
    val title = currentEval?.evaluationName ?: "Evaluation"

    BaseScreen(
        title = title,
        onPrevClick = if (!isFirst) ({ currentIndex-- }) else null,
        onNextClick = { if (!isLast && answer.isNotBlank()) currentIndex++ },
        onDoneClick = if (isLast) ({ if (answer.isNotBlank()) onComplete() }) else null,
        isNextEnabled = !isLast && answer.isNotBlank(),
        isDoneEnabled = isLast && answer.isNotBlank()
    ) {
        EvaluationObjectItem(
            obj = currentObject,
            viewModel = viewModel
        )
    }

}
