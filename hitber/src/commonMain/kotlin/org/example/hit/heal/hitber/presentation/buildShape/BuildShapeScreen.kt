package org.example.hit.heal.hitber.presentation.buildShape

import TabletBaseScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.tenthQuestionHitberTitle
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.components.TenthQuestionShapesLayout
import org.example.hit.heal.hitber.presentation.buildShape.components.handleTenthQuestionCapture
import org.example.hit.heal.hitber.presentation.buildShape.model.draggableShapesItem
import org.example.hit.heal.hitber.presentation.buildShape.model.staticShapesItem
import org.example.hit.heal.hitber.utils.CapturableWrapper
import org.example.hit.heal.hitber.utils.InstructionText
import org.example.hit.heal.hitber.utils.PlatformCapturable
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

        val triangleWidth by remember(screenSize) {
            derivedStateOf { 0.4f * screenSize.second }
        }
        val triangleHeight by remember(screenSize) {
            derivedStateOf { 0.5f * screenSize.second }
        }


        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            TabletBaseScreen(
                title = stringResource(tenthQuestionHitberTitle),
                onNextClick = {
                    capturable?.capture?.let { it() }
                },

                buttonText = stringResource(`continue`),
                question = 10,
                buttonColor = primaryColor,
                content = {
                    InstructionText(stringResource(tenthQuestionHitberInstructions))

                    capturable = PlatformCapturable(
                        onCaptured = { imageBitmap ->
                            handleTenthQuestionCapture(
                                imageBitmap = imageBitmap,
                                viewModel = viewModel,
                                tenthQuestionViewModel = tenthQuestionViewModel,
                                itemPositions = itemPositions,
                                draggableShapes = draggableShapesItem,
                                staticShapes = staticShapesItem,
                                triangleWidth = triangleWidth,
                                triangleHeight = triangleHeight,
                                navigator = navigator
                            )
                        }
                    )
                    {
                        TenthQuestionShapesLayout(
                            staticShapesItem = staticShapesItem,
                            draggableShapesItem = draggableShapesItem,
                            itemPositions = itemPositions,
                            triangleWidth = triangleWidth,
                            triangleHeight = triangleHeight,
                            density = density,
                            onScreenSizeChanged = { screenSize = it }
                        )
                    }
                })
        }
    }
}



