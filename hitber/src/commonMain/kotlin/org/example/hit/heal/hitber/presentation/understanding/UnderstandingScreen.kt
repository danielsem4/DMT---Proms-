package org.example.hit.heal.hitber.presentation.understanding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberTitle
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.presentation.understanding.components.AudioButton
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayingDialog
import org.example.hit.heal.hitber.presentation.understanding.components.TableWithNapkinsBox
import org.example.hit.heal.hitber.presentation.understanding.components.FridgeWithItemsBox
import org.example.hit.heal.hitber.presentation.understanding.model.fridgeItems
import core.utils.CapturableWrapper
import core.utils.ObserveLifecycle
import org.example.hit.heal.hitber.presentation.components.InstructionText
import core.utils.RegisterBackHandler
import core.utils.platformCapturable
import kotlinx.coroutines.launch
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val viewModel: ActivityViewModel = koinViewModel()
        val sixthQuestionViewModel: SixthQuestionViewModel = koinViewModel()
        val audioResourceId by sixthQuestionViewModel.audioResourceId.collectAsState()
        val itemResourceId by sixthQuestionViewModel.selectedItem.collectAsState()
        val napkinResourceId by sixthQuestionViewModel.selectedNapkin.collectAsState()
        val isPlaying by sixthQuestionViewModel.isPlaying.collectAsState()

        val itemLastPositions by sixthQuestionViewModel.itemLastPositions.collectAsState()
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }
        val audioUrl = audioResourceId?.let { stringResource(it) }
        val coroutineScope = rememberCoroutineScope()

        var fridgeSize by remember { mutableStateOf(0f to 0f) }
        var tableSize by remember { mutableStateOf(0f to 0f) }
        var napkinPosition by remember { mutableStateOf(Offset.Zero) }

        val itemPositions = remember(fridgeSize) {
            mutableStateListOf<Offset>().apply {
                fridgeItems.forEach { item ->
                    val initialX = fridgeSize.first * item.xRatio
                    val initialY = fridgeSize.second * item.yRatio
                    add(Offset(initialX, initialY))
                }
            }
        }

        val itemWidthPx = fridgeSize.second * 0.1f
        val itemHeightPx = fridgeSize.second * 0.1f
        val napkinWidthPx = tableSize.second * 0.18f
        val napkinHeightPx = tableSize.second * 0.18f

        var isFridgeOpen by remember { mutableStateOf(false) }

        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            BaseScreen(
                title = stringResource(sixthQuestionHitberTitle),
                config = ScreenConfig.TabletConfig,
                topRightText = "6/10",
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InstructionText(stringResource(sixthQuestionHitberInstructions))

                        AudioButton(
                            onClick = {
                                audioUrl?.let { url ->
                                    coroutineScope.launch {
                                        sixthQuestionViewModel.onPlayAudio(url)
                                    }
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        capturable = platformCapturable(
                            onCaptured = { imageBitmap ->
                                val timestamp = getCurrentFormattedDateTime()
                                viewModel.uploadImage(imageBitmap, timestamp, 6)

                                sixthQuestionViewModel.evaluateAnswer(
                                    napkinResourceId,
                                    napkinPosition,
                                    napkinWidthPx to napkinHeightPx,
                                    itemWidthPx to itemHeightPx,
                                    itemLastPositions
                                )
                                viewModel.setSixthQuestion(
                                    sixthQuestionViewModel.isFridgeOpened,
                                    sixthQuestionViewModel.isItemMovedCorrectly,
                                    sixthQuestionViewModel.isNapkinPlacedCorrectly,
                                    date = timestamp
                                )

                                navigator?.replace(DragAndDropScreen())
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White, shape = RoundedCornerShape(4))
                            ) {
                                FridgeWithItemsBox(
                                    isFridgeOpen = isFridgeOpen,
                                    onFridgeToggle = { isFridgeOpen = !isFridgeOpen },
                                    onFridgeSizeChanged = { fridgeSize = it },
                                    itemPositions = itemPositions,
                                    itemWidthPx = itemWidthPx,
                                    itemHeightPx = itemHeightPx,
                                    itemResourceId = itemResourceId,
                                    density = density,
                                    viewModel = sixthQuestionViewModel,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )

                                TableWithNapkinsBox(
                                    tableSize = tableSize,
                                    onTableSizeChanged = { tableSize = it },
                                    napkinWidthPx = napkinWidthPx,
                                    napkinHeightPx = napkinHeightPx,
                                    napkinResourceId = napkinResourceId,
                                    onNapkinPositionCalculated = { napkinPosition = it },
                                    density = density,
                                    modifier = Modifier.align(Alignment.BottomEnd)
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

            ObserveLifecycle(
                onStop = {
                    sixthQuestionViewModel.stopAudio()
                },
                onStart = {}
            )

            RegisterBackHandler(this) {
                navigator?.pop()
            }

            if (isPlaying) {
                AudioPlayingDialog()
            }
        }
    }
}



