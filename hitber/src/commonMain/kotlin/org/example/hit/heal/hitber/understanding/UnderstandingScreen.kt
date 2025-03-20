package org.example.hit.heal.hitber.understanding

import TabletBaseScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.onSizeChanged
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
import dmt_proms.hitber.generated.resources.table
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.dragAndDrop.DragAndDropScreen
import org.example.hit.heal.hitber.understanding.components.fridgeItems
import org.jetbrains.compose.resources.painterResource


class UnderstandingScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        var isClicked by remember { mutableStateOf(true) }
        var fridgeSize by remember { mutableStateOf(0f to 0f) }
        val viewModel: ActivityViewModel = viewModel()

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
                        Text(
                            "הקשב", color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(4))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { isClicked = !isClicked }
                                .onSizeChanged { size ->
                                    fridgeSize = size.width.toFloat() to size.height.toFloat()
                                }
                        ) {
                            Image(
                                painter = if (isClicked) painterResource(Res.drawable.open_fridge)
                                else painterResource(Res.drawable.close_fridge),
                                contentDescription = if (isClicked) "open fridge" else "close fridge",
                                modifier = Modifier.fillMaxSize()
                            )

                            if (isClicked) {
                                fridgeItems.forEachIndexed { index, item ->
                                    val itemWidth = fridgeSize.first * 0.09f
                                    val itemHeight = fridgeSize.second * 0.045f
                                    val x = fridgeSize.first * item.xRatio
                                    val y = fridgeSize.second * item.yRatio

                                    val currentPosition by viewModel.itemPositions[index]

                                    Box(
                                        modifier = Modifier
                                            .offset(
                                                x = (currentPosition.first + x).dp,
                                                y = (currentPosition.second + y).dp
                                            )
                                            .size(itemWidth.dp, itemHeight.dp)
                                            .pointerInput(Unit) {
                                                detectDragGestures { _, dragAmount ->
                                                    viewModel.updateItemPosition(
                                                        index,
                                                        Pair(dragAmount.x, dragAmount.y)
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
                        Spacer(modifier = Modifier.width(80.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight().zIndex(-1f)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.table),
                                contentDescription = "table",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
            })

    }
}

