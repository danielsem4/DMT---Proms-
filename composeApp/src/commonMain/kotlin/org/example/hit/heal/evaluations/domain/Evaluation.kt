package org.example.hit.heal.evaluations.domain

data class Evaluation(
    val id: Int,
    val evaluationName: String,
    val isPublic: Boolean,
    val isMultilingual: Boolean,
    val evaluationType: Int,
    val evaluationObjects: List<EvaluationObject>,
)