package core.data.model.evaluation

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.Serializable

@Serializable
sealed class EvaluationAnswer {
    data class Text(val value: String) : EvaluationAnswer()
    data class Number(val value: Float) : EvaluationAnswer()
    data class MultiChoice(val values: List<String>) : EvaluationAnswer()
    data class Image(val url: String) : EvaluationAnswer()
    data class Toggle(val value: Boolean) : EvaluationAnswer()
    data class HumanModelPoints(
        val front: Set<Offset>,
        val back: Set<Offset>
    ) : EvaluationAnswer()

    data object Unanswered : EvaluationAnswer()
}

// Extension function moved here to be accessible
fun EvaluationAnswer.toRawString(): String = when (this) {
    is EvaluationAnswer.Text -> value
    is EvaluationAnswer.Number -> value.toString()
    is EvaluationAnswer.MultiChoice -> values.joinToString(",")
    is EvaluationAnswer.Image -> url
    is EvaluationAnswer.Toggle -> value.toString()
    is EvaluationAnswer.HumanModelPoints ->
        "front=${front.joinToString(";") { "${it.x},${it.y}" }}|back=${back.joinToString(";") { "${it.x},${it.y}" }}"

    EvaluationAnswer.Unanswered -> ""
}