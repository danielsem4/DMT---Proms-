package org.example.hit.heal.evaluations.domain

data class EvaluationObject(
    val id: Int,
    val evaluationQuestion: String,
    val evaluationScreen: Int,
    val evaluationOrder: Int,
    var returnValue: Boolean,
    val numberOfValues: Int,
    val predefinedValues: Boolean,
    val randomSelection: Boolean,
    val orderImportant: Boolean,
    val showIcon: Boolean,
    val answer: String,
    val style: String,
    val isGrade: Boolean,
    val evaluationId: Int,
    val objectType: Int,
    val language: Int,
    val availableValues: List<EvaluationValue>? = null
)