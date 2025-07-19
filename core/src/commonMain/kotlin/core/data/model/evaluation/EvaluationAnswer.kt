package core.data.model.evaluation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

@Serializable
sealed class EvaluationAnswer {
    data class Text(val value: String) : EvaluationAnswer()
    data class Number(val value: Float) : EvaluationAnswer()
    data class MultiChoice(val values: List<String>) : EvaluationAnswer()
    data class Image(
        val bitmap: ImageBitmap? = null,
        var url: String = "" // initially empty, filled after upload
    ) : EvaluationAnswer()
    data class Toggle(val value: Boolean?) : EvaluationAnswer()

    data class HumanModelPoints(
        val front: Set<Offset>, val back: Set<Offset>
    ) : EvaluationAnswer()

    data object Unanswered : EvaluationAnswer()
    data object Answered : EvaluationAnswer()

    val EvaluationAnswer.isAnswered: Boolean
        get() = when (this) {
            is Text -> value.isNotBlank()
            is Number -> true
            is MultiChoice -> values.isNotEmpty()
            is Image -> url.isNotBlank() || bitmap != null
            is Toggle -> value != null
            is HumanModelPoints -> true//front.isNotEmpty() || back.isNotEmpty()
            Answered -> true
            Unanswered -> false
        }

}

// Extension function moved here to be accessible
fun EvaluationAnswer.toRawString(): String = when (this) {
    is EvaluationAnswer.Text -> value
    is EvaluationAnswer.Number -> value.toString()
    is EvaluationAnswer.MultiChoice -> values.joinToString(",")
    is EvaluationAnswer.Image -> url
    is EvaluationAnswer.Toggle -> value.toString()
    is EvaluationAnswer.HumanModelPoints -> {
        val joinedFront = front.joinToString(";") { "${it.x},${it.y}" }
        val joinedBack = back.joinToString(";") { "${it.x},${it.y}" }
        "front=$joinedFront|back=$joinedBack"
    }
    EvaluationAnswer.Unanswered -> ""
    EvaluationAnswer.Answered -> "Answered"
}