package org.example.hit.heal.hitber.presentation.dragAndDrop.components

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.navigator.Navigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.SeventhQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.WritingScreen


fun handleSeventhQuestionCapture(
    circleOffsets: SnapshotStateList<Offset>,
    imageBitmap: ImageBitmap,
    viewModel: ActivityViewModel,
    targetColor: Color?,
    circleColors: List<Color>,
    targetBoxXRange: ClosedFloatingPointRange<Float>,
    targetBoxYRange: ClosedFloatingPointRange<Float>,
    radiusPx: Float,
    seventhQuestionViewModel: SeventhQuestionViewModel,
    navigator: Navigator?
) {
    viewModel.uploadImage(
        bitmap = imageBitmap,
        date = getCurrentFormattedDateTime(),
        currentQuestion = 7,
        onSuccess = { },
        onFailure = { }
    )

    targetColor?.let {
        seventhQuestionViewModel.evaluateAnswer(
            circlePositions = circleOffsets,
            circleColors = circleColors,
            targetColor = it,
            targetBoxXRange = targetBoxXRange,
            targetBoxYRange = targetBoxYRange,
            radiusPx = radiusPx
        )
        viewModel.setSeventhQuestion(
            seventhQuestionViewModel.answer,
            getCurrentFormattedDateTime()
        )
    }

    navigator?.push(WritingScreen())
}

