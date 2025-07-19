package org.example.hit.heal.presentation.components.evaluation

enum class EvaluationObjectType(val type: Int) {
    OPEN_QUESTION(1),
    RADIO(2),
    DROPDOWN(3),
    CHECKBOX(4),
    TOGGLE(5),
    INSTRUCTION(10),
    REPORT(11),
    DRAWING(21),
    SLIDER(34),
    HUMAN_BODY(35);

    companion object {
        fun fromInt(value: Int): EvaluationObjectType? =
            entries.find { it.type == value }
    }
}