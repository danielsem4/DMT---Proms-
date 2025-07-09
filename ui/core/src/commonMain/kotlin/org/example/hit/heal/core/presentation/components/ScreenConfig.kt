package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.TextUnit
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Sizes.paddingSm

data class ScreenConfig(
    val titleFontSize: TextUnit,
    val topBarPaddingHorizontal: Int,
    val topBarPaddingVertical: Int,
    val contentPadding: Int,
    val buttonArrangement: Arrangement.Horizontal,
    val buttonSpacing: Int,
    val showNavigationButtons: Boolean, // For phone
    val showTopRightText: Boolean // For tablet
) {
    companion object {
        val PhoneConfig = ScreenConfig(
            titleFontSize = EXTRA_MEDIUM,
            topBarPaddingHorizontal = paddingSm.value.toInt(),
            topBarPaddingVertical = paddingSm.value.toInt(),
            contentPadding = paddingSm.value.toInt(),
            buttonArrangement = Arrangement.SpaceBetween,
            buttonSpacing = 0, // Not applicable for phone's spaceBetween
            showNavigationButtons = true,
            showTopRightText = false
        )

        val TabletConfig = ScreenConfig(
            titleFontSize = EXTRA_LARGE,
            topBarPaddingHorizontal = 12,
            topBarPaddingVertical = 12,
            contentPadding = 8,
            buttonArrangement = Arrangement.Center,
            buttonSpacing = 16,
            showNavigationButtons = false, // Tablet uses `buttons` array
            showTopRightText = true
        )
    }
}