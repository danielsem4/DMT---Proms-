package org.example.hit.heal.evaluations.domain.data

sealed class EvaluationAnswer {
    data class Text(val value: String) : EvaluationAnswer()
    data class Number(val value: Float) : EvaluationAnswer()
    data class MultiChoice(val values: List<String>) : EvaluationAnswer()
    data class Image(val url: String) : EvaluationAnswer()
    data class Toggle(val value: Boolean) : EvaluationAnswer()
    data object Unanswered : EvaluationAnswer()
}
