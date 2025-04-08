package org.example.hit.heal.hitber.presentation.understanding

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_fridge
import dmt_proms.hitber.generated.resources.hitbear_continue
import dmt_proms.hitber.generated.resources.open_fridge
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_close_fridge
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_instructions
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_item
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_listen
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_napkin
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_open_fridge
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_table
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_title
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_volume_icon
import dmt_proms.hitber.generated.resources.speaker
import dmt_proms.hitber.generated.resources.table
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayer
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayingDialog
import org.example.hit.heal.hitber.presentation.understanding.components.fridgeItems
import org.example.hit.heal.hitber.presentation.understanding.components.napkins
import org.example.hit.heal.hitber.utils.isObjectInsideTargetArea
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val density = LocalDensity.current
        val viewModel: ActivityViewModel = viewModel()

        var isFridgeOpen by remember { mutableStateOf(true) }
        var isAudioClicked by remember { mutableStateOf(false) }
        var fridgeSize by remember { mutableStateOf(0f to 0f) }
        var tableSize by remember { mutableStateOf(0f to 0f) }
        val audioResourceId by viewModel.audioResourceId.collectAsState()
        val itemResourceId by viewModel.selectedItem.collectAsState()
        val napkinResourceId by viewModel.selectedNapkin.collectAsState()

        val audioPlayer = remember { AudioPlayer() }
        val audioUrl = audioResourceId?.let { stringResource(it) }

        val fridgeOpen by viewModel.isFridgeOpened.collectAsState()
        val correctItem by viewModel.isItemMovedCorrectly.collectAsState()

        var isDialogVisible by remember { mutableStateOf(false) }
        val itemPositions by viewModel.itemPositions.collectAsState()
        val itemLastPositions by viewModel.itemLastPositions.collectAsState()

        val selectedNapkin = napkins.find { it.image == napkinResourceId }
        var napkinPosition by remember { mutableStateOf(Offset.Zero) }
        var itemPosition by remember { mutableStateOf(Offset.Zero) }
        val itemWidthPx =  fridgeSize.second * 0.1f
        val itemHeightPx = fridgeSize.second * 0.1f
        val napkinWidthPx = tableSize.second * 0.1f
        val napkinHeightPx = tableSize.second * 0.1f

        LaunchedEffect(isAudioClicked) {
            if (isAudioClicked) {
                audioUrl?.let {
                    isDialogVisible = true
                    audioPlayer.play(it) {
                        isDialogVisible = false
                    }
                    isAudioClicked = false
                }
            }
        }
        val isRtl = false
        CompositionLocalProvider(LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            TabletBaseScreen(
                title = stringResource(Res.string.sixth_question_hitbear_title),
                onNextClick = {

                    if (selectedNapkin != null) {
                        val isItemInNapkin = itemLastPositions.values.any { itemPosition ->
                            isObjectInsideTargetArea(
                                targetPosition = napkinPosition,
                                draggablePosition = itemPosition,
                                targetSize = napkinWidthPx to napkinHeightPx,
                                draggableSize = itemWidthPx to itemHeightPx,
                                threshold = 40f,
                                isCircle = false

                            )
                        }

                        if (isItemInNapkin) {
                            viewModel.setNapkinPlacedCorrectly()
                        }
                    }
                    navigator?.push(DragAndDropScreen())
                },
                question = 6,
                buttonText = stringResource(Res.string.hitbear_continue),
                buttonColor = primaryColor,
                content = {
                    Text(
                        stringResource(Res.string.sixth_question_hitbear_instructions),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(bottom = 30.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.setRandomAudio()
                            isAudioClicked = true

                        },
                        colors = ButtonDefaults.buttonColors(primaryColor),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(30)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.speaker),
                                contentDescription = stringResource(Res.string.sixth_question_hitbear_volume_icon),
                                modifier = Modifier.padding(end = 8.dp)
                                    .size(30.dp).background(color = Color.Transparent)
                            )
                            Text(
                                stringResource(Res.string.sixth_question_hitbear_listen),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(color = Color.White, shape = RoundedCornerShape(4))
                    ) {
                        Box(
                            modifier = Modifier.wrapContentWidth()
                                .fillMaxHeight().align(Alignment.CenterStart)
                                .clickable {
                                    isFridgeOpen = !isFridgeOpen
                                    if (!fridgeOpen) {
                                        viewModel.setFridgeOpened()
                                    }
                                }
                        ) {
                            Image(
                                painter = if (isFridgeOpen) painterResource(Res.drawable.open_fridge)
                                else painterResource(Res.drawable.close_fridge),
                                contentDescription = if (isFridgeOpen) stringResource(Res.string.sixth_question_hitbear_open_fridge) else stringResource(Res.string.sixth_question_hitbear_close_fridge),
                                modifier = Modifier.fillMaxHeight()
                                    .wrapContentWidth().onSizeChanged { size ->
                                        fridgeSize = size.width.toFloat() to size.height.toFloat()
                                    },
                                contentScale = ContentScale.FillHeight
                            )

                            if (isFridgeOpen) {
                                fridgeItems.forEachIndexed { index, item ->
                                    val xPx = fridgeSize.first * item.xRatio
                                    val yPx = fridgeSize.second * item.yRatio

                                    val itemWidthDp = with(density) { itemWidthPx.toDp() }
                                    val itemHeightDp = with(density) { itemHeightPx.toDp() }
                                    val xDp = with(density) { xPx.toDp() }
                                    val yDp = with(density) { yPx.toDp() }

                                    val currentPosition = itemPositions[index]

                                    Box(
                                        modifier = Modifier
                                            .offset(
                                                x = (currentPosition.first.dp + xDp),
                                                y = (currentPosition.second.dp + yDp)
                                            )
                                            .size(itemWidthDp, itemHeightDp)
                                            .pointerInput(Unit) {
                                                detectDragGestures { change, dragAmount ->
                                                    val dragAmountXInDp =
                                                        with(density) { dragAmount.x.toDp() }
                                                    val dragAmountYInDp =
                                                        with(density) { dragAmount.y.toDp() }
                                                    viewModel.updateItemPosition(
                                                        index,
                                                        Pair(
                                                            dragAmountXInDp.value,
                                                            dragAmountYInDp.value
                                                        )
                                                    )

                                                    if (change.positionChanged()) {
                                                        if (item.image == itemResourceId && !correctItem) {
                                                            viewModel.setItemMovedCorrectly()
                                                        }
                                                    }
                                                }
                                            }.onGloballyPositioned { coordinates ->
                                                itemPosition = coordinates.positionInRoot()
                                                viewModel.updateItemLastPosition(
                                                    index,
                                                    itemPosition
                                                )
                                            }
                                            .zIndex(1f)
                                    ) {
                                        Image(
                                            painter = painterResource(item.image),
                                            contentDescription = stringResource(Res.string.sixth_question_hitbear_item),
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd).fillMaxHeight(0.6f).fillMaxWidth(0.4f)

                                .zIndex(-1f).onSizeChanged { size ->
                                    tableSize = size.width.toFloat() to size.height.toFloat()
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.table),
                                contentDescription = stringResource(Res.string.sixth_question_hitbear_table),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds

                            )

                            napkins.forEachIndexed { _, item ->
                                val xPx = tableSize.first * item.xRatio
                                val yPx = tableSize.second * item.yRatio

                                val napkinWidthDp = with(density) { napkinWidthPx.toDp() }
                                val napkinHeightDp = with(density) { napkinHeightPx.toDp() }
                                val xDp = with(density) { xPx.toDp() }
                                val yDp = with(density) { yPx.toDp() }

                                Box(
                                    modifier = Modifier
                                        .offset(
                                            x = xDp,
                                            y = yDp
                                        ).onGloballyPositioned { coordinates ->
                                            if (selectedNapkin?.image == item.image) {
                                                napkinPosition = coordinates.positionInRoot()
                                            }
                                        }
                                        .size(napkinWidthDp, napkinHeightDp)
                                        .zIndex(1f)
                                ) {
                                    Image(
                                        painter = painterResource(item.image),
                                        contentDescription = stringResource(Res.string.sixth_question_hitbear_napkin),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }
                        }
                    }
                }
            )

            if (isDialogVisible) {
                AudioPlayingDialog()
            }
        }
    }
}
