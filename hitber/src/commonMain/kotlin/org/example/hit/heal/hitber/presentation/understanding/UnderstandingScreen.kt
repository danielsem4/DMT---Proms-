package org.example.hit.heal.hitber.presentation.understanding

import TabletBaseScreen
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_fridge
import dmt_proms.hitber.generated.resources.dialog_speaker
import dmt_proms.hitber.generated.resources.open_fridge
import dmt_proms.hitber.generated.resources.speaker
import dmt_proms.hitber.generated.resources.table
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.presentation.understanding.components.AudioPlayer
import org.example.hit.heal.hitber.presentation.understanding.components.fridgeItems
import org.example.hit.heal.hitber.presentation.understanding.components.napkins
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var isFridgeOpen by remember { mutableStateOf(true) }
        var isAudioClicked by remember { mutableStateOf(false) }
        var fridgeSize by remember { mutableStateOf(0f to 0f) }
        var tableSize by remember { mutableStateOf(0f to 0f) }
        val viewModel: ActivityViewModel = viewModel()
        val audioResourceId by viewModel.audioResourceId.collectAsState()
        val itemResourceId by viewModel.selectedItem.collectAsState()
        val napkinResourceId by viewModel.selectedNapkin.collectAsState()

        val fridgeOpen by viewModel.isFridgeOpened.collectAsState()
        val correctItem by viewModel.isItemMovedCorrectly.collectAsState()
        val napkinPlaced by viewModel.isNapkinPlacedCorrectly.collectAsState()

        val audioUrl = audioResourceId?.let { stringResource(it) }
        val density = LocalDensity.current
        val audioPlayer = remember { AudioPlayer() }
        var isDialogVisible by remember { mutableStateOf(false) }

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



        TabletBaseScreen(
            title = "הבנת הוראות",
            onNextClick = { navigator?.push(DragAndDropScreen()) },
            question = 6,
            buttonText = "המשך",
            buttonColor = primaryColor,
            content = {
                Text(
                    "בחלק זה תתבקש לבצע מטלה. לשמיעת המטלה לחץ על הקשב. בסיום לחץ על המשך.",
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
                            contentDescription = "Volume Icon",
                            modifier = Modifier.padding(end = 8.dp)
                                .size(30.dp).background(color = Color.Transparent)
                        )
                        Text(
                            "הקשב",
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
                                if(!fridgeOpen){
                                viewModel.setFridgeOpened()}
                            }
                    ) {
                        Image(
                            painter = if (isFridgeOpen) painterResource(Res.drawable.open_fridge)
                            else painterResource(Res.drawable.close_fridge),
                            contentDescription = if (isFridgeOpen) "open fridge" else "close fridge",
                            modifier = Modifier.fillMaxHeight()
                                .wrapContentWidth().onSizeChanged { size ->
                                    fridgeSize = size.width.toFloat() to size.height.toFloat()
                                },
                            contentScale = ContentScale.FillHeight
                        )

                        if (isFridgeOpen) {
                            fridgeItems.forEachIndexed { index, item ->
                                val itemWidthPx = fridgeSize.first * 0.2f
                                val itemHeightPx = fridgeSize.second * 0.1f
                                val xPx = fridgeSize.first * item.xRatio
                                val yPx = fridgeSize.second * item.yRatio

                                val itemWidthDp = with(density) { itemWidthPx.toDp() }
                                val itemHeightDp = with(density) { itemHeightPx.toDp() }
                                val xDp = with(density) { xPx.toDp() }
                                val yDp = with(density) { yPx.toDp() }

                                val currentPosition by viewModel.itemPositions[index]

                                Box(
                                    modifier = Modifier
                                        .offset(
                                            x = (currentPosition.first.dp + xDp),
                                            y = (currentPosition.second.dp + yDp)
                                        )
                                        .size(itemWidthDp, itemHeightDp)
                                        .pointerInput(Unit) {
                                            detectDragGestures { change, dragAmount ->
                                                viewModel.updateItemPosition(index, Pair(dragAmount.x, dragAmount.y))

                                                if (change.positionChanged()) {
                                                    if (item.image == itemResourceId && !correctItem) {
                                                        viewModel.setItemMovedCorrectly()
                                                    }
                                                }
                                            }
                                        }
                                        .zIndex(1f)
                                ) {
                                    Image(
                                        painter = painterResource(item.image),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(500.dp)
                            .align(Alignment.BottomEnd)
                            .zIndex(-1f).onSizeChanged { size ->
                                tableSize = size.width.toFloat() to size.height.toFloat()
                            }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.table),
                            contentDescription = "table",
                            modifier = Modifier.fillMaxSize()
                        )

                        napkins.forEachIndexed { _, item ->
                            val itemWidthPx = tableSize.first * 0.2f
                            val itemHeightPx = tableSize.second * 0.1f
                            val xPx = tableSize.first * item.xRatio
                            val yPx = tableSize.second * item.yRatio

                            val itemWidthDp = with(density) { itemWidthPx.toDp() }
                            val itemHeightDp = with(density) { itemHeightPx.toDp() }
                            val xDp = with(density) { xPx.toDp() }
                            val yDp = with(density) { yPx.toDp() }

                            Box(
                                modifier = Modifier
                                    .offset(
                                        x = xDp,
                                        y = yDp
                                    )
                                    .size(itemWidthDp, itemHeightDp)
                                    .zIndex(1f)
                            ) {
                                Image(
                                    painter = painterResource(item.image),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
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

@Composable
fun AudioPlayingDialog() {
    val speakerYOffset by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Dialog(
        onDismissRequest = {},
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.White, shape = RoundedCornerShape(4))
            ) {
                Image(
                    painter = painterResource(Res.drawable.dialog_speaker),
                    contentDescription = "Speaker",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = speakerYOffset.dp) // האנימציה של הרמקול
                )
            }
        }
    )
}