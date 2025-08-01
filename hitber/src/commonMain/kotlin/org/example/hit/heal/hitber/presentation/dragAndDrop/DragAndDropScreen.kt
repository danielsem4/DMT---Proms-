package org.example.hit.heal.hitber.presentation.dragAndDrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.seventhQuestionHitberTitle
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.components.DraggableCanvas
import org.example.hit.heal.hitber.presentation.dragAndDrop.model.circleColors
import org.example.hit.heal.hitber.presentation.dragAndDrop.model.circlesPositions
import org.example.hit.heal.hitber.presentation.writing.WritingScreen
import core.utils.CapturableWrapper
import org.example.hit.heal.hitber.presentation.components.InstructionText
import core.utils.RegisterBackHandler
import core.utils.platformCapturable
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class DragAndDropScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val seventhQuestionViewModel: SeventhQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        var screenSize by remember { mutableStateOf(0f to 0f) }
        val targetColor by seventhQuestionViewModel.targetCircleColor.collectAsState()
        val instructionsResourceId by seventhQuestionViewModel.instructionsResourceId.collectAsState()
        val instructions = instructionsResourceId?.let { stringResource(it) }
        var targetBoxXRange by remember { mutableStateOf(0f..0f) }
        var targetBoxYRange by remember { mutableStateOf(0f..0f) }
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }

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

        BaseScreen(
            title = stringResource(seventhQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "7/10",
            content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (instructions != null) {
                        InstructionText(instructions)
                    }
                    // Screenshot capture wrapper that captures the draggable circles layout below
                    capturable = platformCapturable(
                        modifier = Modifier.weight(1f),
                        onCaptured = { imageBitmap ->
                            val timestamp = getCurrentFormattedDateTime()

                            viewModel.saveBitmap2(imageBitmap)

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
                                    timestamp
                                )
                            }

                            navigator?.replace(WritingScreen())
                        }
                    ) {
                        Box(modifier = Modifier.weight(1f)) {

                            // Canvas area displaying draggable circles with dynamic positions and colors
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
                    }

                    RoundedButton(
                        text = stringResource(`continue`),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                            .padding(vertical = paddingMd),
                        onClick = {
                            capturable?.capture?.invoke()
                        }
                    )
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.pop()
        }
    }
}


