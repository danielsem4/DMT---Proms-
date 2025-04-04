package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class WordSlotState(
    val id: Int,
    val initialOffset: Offset = Offset.Zero,
    var word: String? = null,
    var color: Color = Color.Gray
)

val slotsList = listOf(
    WordSlotState(0, initialOffset = Offset(0.1f, 0.6f)),
    WordSlotState(1,initialOffset = Offset(0.25f, 0.6f)),
    WordSlotState(2, initialOffset = Offset(0.4f, 0.6f)),
    WordSlotState(3,initialOffset = Offset(0.55f, 0.6f)),
    WordSlotState(4,initialOffset = Offset(0.7f, 0.6f)),
    WordSlotState(5, initialOffset = Offset(0.85f, 0.6f))
)
