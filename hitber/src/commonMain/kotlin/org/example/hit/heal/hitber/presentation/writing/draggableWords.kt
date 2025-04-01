package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.example.hit.heal.core.presentation.Colors.primaryColor

data class DraggableWordState(
    val word: String,
    val initialOffset: Offset,
    var offset: Offset = initialOffset,
    val wordSlotIndex: Int? = null,
    var color: Color = primaryColor // צבע ברירת מחדל
)



// כאן המילים יכילו את המיקום ההתחלתי כ-Offset
val draggableWordsList = listOf(
    DraggableWordState("סבתא", initialOffset = Offset(0.2f, 0.6f)),
    DraggableWordState("ארוחת", initialOffset = Offset(0.3f, 0.6f)),
    DraggableWordState("עם", initialOffset = Offset(0.4f, 0.6f)),
    DraggableWordState("אתמול", initialOffset = Offset(0.5f, 0.6f)),
    DraggableWordState("אכלתי", initialOffset = Offset(0.6f, 0.6f)),
    DraggableWordState("ערב", initialOffset = Offset(0.7f, 0.6f))
)
