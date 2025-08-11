package org.example.hit.heal.hitber.presentation.buildShape

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberTitle
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.components.TenthQuestionShapesLayout
import org.example.hit.heal.hitber.presentation.buildShape.model.draggableShapesItem
import org.example.hit.heal.hitber.presentation.buildShape.model.staticShapesItem
import org.example.hit.heal.hitber.presentation.summary.SummaryScreen
import org.example.hit.heal.core.presentation.utils.CapturableWrapper
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.core.presentation.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.utils.platformCapturable
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class BuildShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val tenthQuestionViewModel: TenthQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }
        var screenSize by remember { mutableStateOf(0f to 0f) }

        val itemPositions = remember(screenSize) {
            mutableStateListOf<Offset>().apply {
                draggableShapesItem.forEach { shape ->
                    val initialX = screenSize.first * shape.xRatio
                    val initialY = screenSize.second * shape.yRatio
                    add(Offset(initialX, initialY))
                }
            }
        }

        // Dynamically calculate triangle dimensions based on screen size
        val triangleWidth by remember(screenSize) {
            derivedStateOf { 0.4f * screenSize.second }
        }
        val triangleHeight by remember(screenSize) {
            derivedStateOf { 0.5f * screenSize.second }
        }

        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            BaseScreen(
                title = stringResource(tenthQuestionHitberTitle),
                config = ScreenConfig.TabletConfig,
                topRightText = "10/10",
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InstructionText(stringResource(tenthQuestionHitberInstructions))

                        // Screenshot capture wrapper that captures the shape layout below
                        capturable = platformCapturable(
                            modifier = Modifier.weight(1f),
                            onCaptured = { imageBitmap ->
                                val timestamp = getCurrentFormattedDateTime()

                                // Checks if the shapes are positioned correctly based on their current offsets and reference static shapes
                                tenthQuestionViewModel.uploadTenthQuestionImageAnswer(
                                    itemPositions,
                                    draggableShapesItem,
                                    staticShapesItem,
                                    triangleWidth,
                                    triangleHeight
                                )

                                viewModel.setTenthQuestion(
                                    tenthQuestionViewModel.answer,
                                    timestamp
                                )

                                viewModel.saveBitmap3(imageBitmap)

                                navigator?.replace(SummaryScreen())
                            }
                        ) {
                            // Layout for draggable + static shapes
                            TenthQuestionShapesLayout(
                                staticShapesItem = staticShapesItem,
                                draggableShapesItem = draggableShapesItem,
                                itemPositions = itemPositions,
                                triangleWidth = triangleWidth,
                                triangleHeight = triangleHeight,
                                density = density,
                                onScreenSizeChanged = { screenSize = it },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        RoundedButton(
                            text = stringResource(`continue`),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(200.dp),
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
}



