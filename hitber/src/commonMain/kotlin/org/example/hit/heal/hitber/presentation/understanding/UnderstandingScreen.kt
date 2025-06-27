package org.example.hit.heal.hitber.presentation.understanding

import org.example.hit.heal.core.presentation.components.HorizontalTabletBaseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.getCurrentFormattedDateTime
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberTitle
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.presentation.understanding.components.AudioButton
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayingDialog
import org.example.hit.heal.hitber.presentation.understanding.components.TableWithNapkinsBox
import org.example.hit.heal.hitber.presentation.understanding.components.FridgeWithItemsBox
import org.example.hit.heal.hitber.presentation.understanding.model.fridgeItems
import core.utils.CapturableWrapper
import org.example.hit.heal.hitber.presentation.components.InstructionText
import core.utils.PlatformCapturable
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
        var isAudioClicked by remember { mutableStateOf(false) }

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
        val napkinWidthPx = tableSize.second * 0.1f
        val napkinHeightPx = tableSize.second * 0.1f

        var isFridgeOpen by remember { mutableStateOf(false) }

        LaunchedEffect(isAudioClicked) {
            if (isAudioClicked) {
                audioUrl?.let {
                    sixthQuestionViewModel.onPlayAudio(it)
                    isAudioClicked = false
                }
            }
        }
        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            HorizontalTabletBaseScreen(
                title = stringResource(sixthQuestionHitberTitle),
                onNextClick = {
                    capturable?.capture?.let { it() }
                },
                question = 6,
                buttonText = stringResource(`continue`),
                buttonColor = primaryColor,
                content = {

                    InstructionText(stringResource(sixthQuestionHitberInstructions))

                    AudioButton(
                        onClick = {
                            sixthQuestionViewModel.setRandomAudio()
                            isAudioClicked = true
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    capturable = PlatformCapturable(
                        onCaptured = { imageBitmap ->
                            val timestamp = getCurrentFormattedDateTime()

                            viewModel.uploadImage(
                                bitmap = imageBitmap,
                                date = timestamp,
                                currentQuestion = 6,
                                onSuccess = {},
                                onFailure = {}
                            )

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

                            navigator?.push(DragAndDropScreen())
                        }
                    )
                    {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(color = Color.White, shape = RoundedCornerShape(4))
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
                }
            )

            if (isPlaying) {
                AudioPlayingDialog()
            }
        }
    }
}



