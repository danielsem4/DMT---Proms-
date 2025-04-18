
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.evaluations.domain.Evaluation
import org.example.hit.heal.evaluations.domain.EvaluationViewModel
import org.example.hit.heal.evaluations.presentaion.EvaluationObjectItem

@Composable
fun EvaluationItem(
    evaluation: Evaluation,
    viewModel: EvaluationViewModel,
    onItemClick: (Evaluation) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onItemClick(evaluation) },
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = evaluation.evaluationName,
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Type: ${evaluation.evaluationType}")
            Text(text = "Multilingual: ${evaluation.isMultilingual}")
            Text(text = "ID: ${evaluation.id}")

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Scrollable block for questions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(rememberScrollState())
            ) {
                evaluation.evaluationObjects.forEach { obj ->
                    EvaluationObjectItem(obj, viewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
