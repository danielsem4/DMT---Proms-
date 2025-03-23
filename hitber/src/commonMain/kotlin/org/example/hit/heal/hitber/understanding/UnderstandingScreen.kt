package org.example.hit.heal.hitber.understanding

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_fridge
import dmt_proms.hitber.generated.resources.open_fridge
import dmt_proms.hitber.generated.resources.speaker
import dmt_proms.hitber.generated.resources.table
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.understanding.components.fridgeItems
import org.example.hit.heal.hitber.understanding.components.napkins
import org.jetbrains.compose.resources.painterResource


class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var isFridgeOpen by remember { mutableStateOf(true) }
        var fridgeSize by remember { mutableStateOf(0f to 0f) }
        var tableSize by remember { mutableStateOf(0f to 0f) }
        val viewModel: ActivityViewModel = viewModel()
        val density = LocalDensity.current

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
                    onClick = {},
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
                            modifier = Modifier .padding(end = 8.dp)  // Add some space between image and text
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
                            .clickable { isFridgeOpen = !isFridgeOpen
                                viewModel.openFridge()}
                    ) {
                        Image(
                            painter = if (isFridgeOpen) painterResource(Res.drawable.open_fridge)
                            else painterResource(Res.drawable.close_fridge),
                            contentDescription = if (isFridgeOpen) "open fridge" else "close fridge",
                            modifier = Modifier .fillMaxHeight()
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
                                            detectDragGestures { _, dragAmount ->
                                                val newX = currentPosition.first + dragAmount.x
                                                val newY = currentPosition.second + dragAmount.y

                                                val clampedX = newX.coerceIn(
                                                    0f,
                                                    fridgeSize.first - itemWidthPx
                                                )
                                                val clampedY = newY.coerceIn(
                                                    0f,
                                                    fridgeSize.second - itemHeightPx
                                                )

                                                viewModel.updateItemPosition(
                                                    index,
                                                    Pair(clampedX, clampedY)
                                                )
                                            }
                                        }.zIndex(1f)
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

                        napkins.forEachIndexed { index, item ->
                            val itemWidthPx = tableSize.first * 0.2f  // גודל המפיות על השולחן
                            val itemHeightPx = tableSize.second * 0.1f
                            val xPx = tableSize.first * item.xRatio  // יחס המיקום של המפיות
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
    }

}

