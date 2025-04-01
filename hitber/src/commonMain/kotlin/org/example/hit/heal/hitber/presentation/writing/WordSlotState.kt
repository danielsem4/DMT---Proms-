package org.example.hit.heal.hitber.presentation.writing

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset

data class WordSlotState(
    val id: Int,
    val initialOffset: Offset = Offset.Zero,
    var word: String? = null,
    var isOccupied: Boolean = false
)

val slotsList = mutableStateListOf(
    WordSlotState(0, initialOffset = Offset(0.2f, 0.2f)),
    WordSlotState(1,initialOffset = Offset(0.3f, 0.2f)),
    WordSlotState(2, initialOffset = Offset(0.4f, 0.2f)),
    WordSlotState(3,initialOffset = Offset(0.5f, 0.2f)),
    WordSlotState(4,initialOffset = Offset(0.6f, 0.2f)),
    WordSlotState(5, initialOffset = Offset(0.7f, 0.2f))
)
