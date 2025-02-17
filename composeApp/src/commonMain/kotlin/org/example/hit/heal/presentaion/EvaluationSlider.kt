package org.example.hit.heal.presentaion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.presentaion.components.CustomSlider

@Composable
fun EvaluationSlider(onPrevClick: (() -> Unit)? = null, onNextClick: (() -> Unit)? = null) {
    var sliderValue by remember { mutableStateOf(5) }

    BaseScreen(
        title = "Evaluation", onPrevClick, onNextClick
    ) {
        Text(text = "How do you feel?", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomSlider(0, 10) { sliderValue = it }
        }

        Text(text = sliderValue.toString(), style = MaterialTheme.typography.body1)
    }
}
