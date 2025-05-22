package org.example.hit.heal.hitber.presentation.dragAndDrop

import TabletBaseScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.seventh_question_hitbear_title
import io.github.suwasto.capturablecompose.Capturable
import io.github.suwasto.capturablecompose.rememberCaptureController
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.DraggableCanvas
import org.example.hit.heal.hitber.presentation.dragAndDrop.model.circleColors
import org.example.hit.heal.hitber.presentation.dragAndDrop.model.circlesPositions
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.handleSeventhQuestionCapture
import org.example.hit.heal.hitber.utils.InstructionText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class DragAndDropScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val seventhQuestionViewModel: SeventhQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        val captureController = rememberCaptureController()
        var screenSize by remember { mutableStateOf(0f to 0f) }
        val targetColor by seventhQuestionViewModel.targetCircleColor.collectAsState()
        val instructionsResourceId by seventhQuestionViewModel.instructionsResourceId.collectAsState()
        val instructions = instructionsResourceId?.let { stringResource(it) }
        var targetBoxXRange by remember { mutableStateOf(0f..0f) }
        var targetBoxYRange by remember { mutableStateOf(0f..0f) }

        val radiusPx = with(density) { 25.dp.toPx() }

        val circleOffsets = remember(screenSize) {
            mutableStateListOf<Offset>().apply {
                circlesPositions.forEach { (xRatio, yRatio) ->
                    val x = screenSize.first * xRatio
                    val y = screenSize.second * yRatio
                    add(Offset(x, y))
                }
            }
        }

        TabletBaseScreen(
            title = stringResource(Res.string.seventh_question_hitbear_title),
            onNextClick = {
                captureController.capture()
            },
            buttonText = stringResource(Res.string.hitbear_continue),
            buttonColor = primaryColor,
            question = 7,
            content = {
                if (instructions != null) {
                    InstructionText(instructions)
                }

                Capturable(
                    captureController = captureController,
                    onCaptured = { imageBitmap ->
                        handleSeventhQuestionCapture(
                            circleOffsets = circleOffsets,
                            imageBitmap = imageBitmap,
                            viewModel = viewModel,
                            targetColor = targetColor,
                            circleColors = circleColors,
                            targetBoxXRange = targetBoxXRange,
                            targetBoxYRange = targetBoxYRange,
                            radiusPx = radiusPx,
                            seventhQuestionViewModel = seventhQuestionViewModel,
                            navigator = navigator
                        )
                    }
                )
                {
                    DraggableCanvas(
                        circleOffsets = circleOffsets,
                        circleColors = circleColors,
                        radiusPx = radiusPx,
                        onScreenSizeChanged = { screenSize = it },
                        onTargetBoxRangeCalculated = { xRange, yRange ->
                            targetBoxXRange = xRange
                            targetBoxYRange = yRange
                        }
                    )
                }
            })
    }
}


