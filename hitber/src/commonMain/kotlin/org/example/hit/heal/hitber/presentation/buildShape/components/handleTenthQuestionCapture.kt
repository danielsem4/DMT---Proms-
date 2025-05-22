package org.example.hit.heal.hitber.presentation.buildShape.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.navigator.Navigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.TenthQuestionViewModel
import org.example.hit.heal.hitber.presentation.buildShape.model.BuildShapes
import org.example.hit.heal.hitber.presentation.summary.SummaryScreen

fun handleTenthQuestionCapture(
    imageBitmap: ImageBitmap,
    viewModel: ActivityViewModel,
    tenthQuestionViewModel: TenthQuestionViewModel,
    itemPositions: List<Offset>,
    draggableShapes: List<BuildShapes>,
    staticShapes: List<BuildShapes>,
    triangleWidth: Float,
    triangleHeight: Float,
    navigator: Navigator?
) {
    val timestamp = getCurrentFormattedDateTime()

    viewModel.uploadImage(
        bitmap = imageBitmap,
        date = timestamp,
        currentQuestion = 10,
        onSuccess = { },
        onFailure = { }
    )

    tenthQuestionViewModel.uploadTenthQuestionImageAnswer(
        itemPositions,
        draggableShapes,
        staticShapes,
        triangleWidth,
        triangleHeight
    )

    viewModel.setTenthQuestion(
        tenthQuestionViewModel.answer,
        timestamp
    )

    navigator?.push(SummaryScreen())
}


