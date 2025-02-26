package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.BaseScreen
import org.example.hit.heal.core.presentation.components.CustomMultilineTextField

@Composable
fun EvaluationScreenOpen(onPrevClick: (() -> Unit)? = null, onNextClick: (() -> Unit)? = null) {
    var text by remember { mutableStateOf("") }

    BaseScreen("Evaluation", onPrevClick, onNextClick) {
        Text(text = "How do you feel?",Modifier.padding(8.dp), style = MaterialTheme.typography.h5)

        CustomMultilineTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(8.dp),
            hintText = "Feeling good!",
            maxLines = 7,
        )
    }
}