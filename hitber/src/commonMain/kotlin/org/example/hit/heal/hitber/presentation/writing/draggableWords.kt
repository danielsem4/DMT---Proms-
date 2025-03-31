package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.ui.geometry.Offset

data class DraggableWordState(
    val word: String,
    var offset: Offset = Offset(0f, 0f)
)

val draggableWordsList = (
    listOf(
        DraggableWordState("סבתא"),
        DraggableWordState("ארוחת"),
        DraggableWordState("עם"),
        DraggableWordState("אתמול"),
        DraggableWordState("אכלתי"),
        DraggableWordState("ערב")
    )
)
