package org.example.hit.heal.hitber.presentation.concentration.components

data class ThirdQuestionState(
    val number: Int? = null,
    val isNumberClickable: Boolean = false,
    val isFinished: Boolean = false,
    val startButtonIsVisible: Boolean = true
)