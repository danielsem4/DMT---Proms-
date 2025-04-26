package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.ui.geometry.Offset
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class SixthQuestionState(
    val audioResourceId: StringResource? = null,
    val selectedItem: DrawableResource? = null,
    val selectedNapkin: DrawableResource? = null,
    val itemLastPositions: Map<Int, Offset> = emptyMap(),
    val isFridgeOpened: Boolean = false,
    val isItemMovedCorrectly: Boolean = false,
    val isNapkinPlacedCorrectly: Boolean = false
)