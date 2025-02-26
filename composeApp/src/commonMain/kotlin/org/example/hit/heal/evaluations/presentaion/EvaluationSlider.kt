package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedFilledSlider

@Composable
fun EvaluationSlider(onPrevClick: (() -> Unit)? = null, onNextClick: (() -> Unit)? = null) {
    var sliderValue by remember { mutableStateOf(5f) }

    BaseScreen(
        title = "Evaluation", onPrevClick, onNextClick
    ) {
        Text(text = "How do you feel?", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        RoundedFilledSlider(0f, 10f) { sliderValue = it }

        Text("Value: ${sliderValue.toInt()}", fontSize = 30.sp)
    }
}
