package org.example.hit.heal.evaluations.presentaion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.example.hit.heal.app.BaseScreen
import org.example.hit.heal.evaluations.domain.Evaluation
import org.example.hit.heal.evaluations.domain.EvaluationViewModel

@Composable
fun EvaluationsList(
    viewModel: EvaluationViewModel,
    onItemClick: (Evaluation) -> Unit, // Handle item click action
    onPrevClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null
) {
    val evaluations by viewModel.evaluations.collectAsState()

    BaseScreen(
        title = "Evaluation",
        onPrevClick = onPrevClick,
        onNextClick = onNextClick
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(evaluations) { evaluation ->
                EvaluationItem(evaluation, onItemClick)
            }
        }
    }
}

// Helper function to format date using `kotlinx.datetime`
fun formatDate(inputDate: String) = try {
    LocalDate.parse(inputDate).toString() // Convert to LocalDate
} catch (e: Exception) {
    inputDate // Return original if parsing fails
}

// Composable Function to Render Each Evaluation Item
@Composable
fun EvaluationItem(
    evaluation: Evaluation,
    onItemClick: (Evaluation) -> Unit // Click event
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onItemClick(evaluation) }, // Handle Click
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = evaluation.name,
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Date: ${formatDate(evaluation.date)}")

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Frequency: ${evaluation.frequency.name.lowercase()}")
        }
    }
}
