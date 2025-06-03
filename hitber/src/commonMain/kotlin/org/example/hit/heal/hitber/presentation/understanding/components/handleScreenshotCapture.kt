package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.understanding.SixthQuestionViewModel
import org.jetbrains.compose.resources.DrawableResource

fun handleScreenshotCapture(
    imageBitmap: ImageBitmap,
    viewModel: ActivityViewModel,
    sixthQuestionViewModel: SixthQuestionViewModel,
    napkinResourceId: DrawableResource?,
    napkinPosition: Offset,
    napkinSize: Pair<Float, Float>,
    itemSize: Pair<Float, Float>,
    itemLastPositions: Map<Int, Offset>,
    onNavigate: () -> Unit
) {
    viewModel.uploadImage(
        bitmap = imageBitmap,
        date = getCurrentFormattedDateTime(),
        currentQuestion = 6,
        onSuccess = {},
        onFailure = {}
    )

    sixthQuestionViewModel.evaluateAnswer(
        napkinResourceId,
        napkinPosition,
        napkinSize,
        itemSize,
        itemLastPositions
    )

    viewModel.setSixthQuestion(
        sixthQuestionViewModel.isFridgeOpened,
        sixthQuestionViewModel.isItemMovedCorrectly,
        sixthQuestionViewModel.isNapkinPlacedCorrectly,
        date = getCurrentFormattedDateTime()
    )

    onNavigate()
}
