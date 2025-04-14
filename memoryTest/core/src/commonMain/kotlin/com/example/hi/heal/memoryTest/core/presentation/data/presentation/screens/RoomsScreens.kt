package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.hi.heal.memoryTest.core.presentation.data.backgroundColor
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState

import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.app
import dmt_proms.memorytest.core.generated.resources.backpack
import dmt_proms.memorytest.core.generated.resources.bedroom_block
import dmt_proms.memorytest.core.generated.resources.book
import dmt_proms.memorytest.core.generated.resources.bottle
import dmt_proms.memorytest.core.generated.resources.coffee
import dmt_proms.memorytest.core.generated.resources.dress
import dmt_proms.memorytest.core.generated.resources.glasses
import dmt_proms.memorytest.core.generated.resources.keys
import dmt_proms.memorytest.core.generated.resources.kitchen_block
import dmt_proms.memorytest.core.generated.resources.phone
import dmt_proms.memorytest.core.generated.resources.salon_block
import dmt_proms.memorytest.core.generated.resources.shoes
import dmt_proms.memorytest.core.generated.resources.wallet
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.core.time.Timer


data class Item(
    val id: Int,
    val resId: DrawableResource,
    var isPlaced: Boolean = false,
    var position: Offset = Offset.Zero
)



class RoomsScreens( val txtMemoryPage: Int = 1): Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var draggedOffset by remember { mutableStateOf(Offset.Zero) }
        var isDragging by remember { mutableStateOf(false) }



        val dragAndDropState = rememberDragAndDropState<Item>()
        var droppedItems by remember { mutableStateOf(mutableListOf<Item>()) }

        var selectedRoom by remember { mutableStateOf("חדר שינה") }
        val roomButtons = listOf("חדר שינה", "סלון", "מטבח")

        val itemsFirst = listOf(
            Res.drawable.glasses, Res.drawable.book, Res.drawable.dress,
            Res.drawable.phone, Res.drawable.keys, Res.drawable.wallet
        )

        val itemsImagesRest = listOf(
            Res.drawable.glasses, Res.drawable.book, Res.drawable.dress,
            Res.drawable.phone, Res.drawable.keys, Res.drawable.wallet,
            Res.drawable.backpack, Res.drawable.shoes, Res.drawable.bottle,
            Res.drawable.coffee,Res.drawable.app
        )

        val currentItems = if (txtMemoryPage!= 1)  itemsImagesRest else itemsFirst


        var timeLeft by remember { mutableStateOf(4 * 60) }

        LaunchedEffect(Unit) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft -= 1
            }

            navigator.push(CallScreen(txtMemoryPage = 2))
        }

        fun formatTime(seconds: Int): String {
            val minutesPart = (seconds / 60).toString().padStart(2, '0')
            val secondsPart = (seconds % 60).toString().padStart(2, '0')
            return "$minutesPart:$secondsPart"
        }

        Box(modifier = Modifier.fillMaxSize()) {
            droppedItems.forEach { item ->
                Image(
                    painter = painterResource(item.resId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .absoluteOffset { IntOffset(item.position.x.toInt(), item.position.y.toInt()) }
                )
            }
        }
        BaseTabletScreen(title = "Rooms", page = txtMemoryPage, totalPages = 6) {


            Row ( modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(16.dp)) {


                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .zIndex(0f)
                        .background(color = backgroundColor)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "גרור ומקם את שבעת החפצים בכל שלשת החדרים, כפי שהיית ממקם בביתך. עליך לזכור את מיקום החפצים. בסיום לחץ על המשך",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        roomButtons.forEach { room ->
                            val isSelected = selectedRoom == room
                            Button(
                                onClick = { selectedRoom = room },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Gray else primaryColor
                                ),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    text = room,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left: items list
                            DragAndDropContainer(state = dragAndDropState) {
                            Box(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(8.dp)
                                    .zIndex(1f)
                                    .border(1.dp, Color.Black)
                                    .background(Color.White)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    modifier = Modifier.padding(12.dp)
                                        .zIndex(1f),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(currentItems.size) { index ->
                                        DraggableItem(

                                            imageRes = currentItems[index],
                                            onDrop = { offset ->
                                                droppedItems.add(
                                                    Item(
                                                        id = index,
                                                        resId = currentItems[index],
                                                        isPlaced = true,
                                                        position = offset
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Bottom: time and continue
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatTime(timeLeft),
                                fontSize = 20.sp,
                                color = primaryColor,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = {
                                    if (txtMemoryPage == 1) {
                                        navigator.push(CallScreen(txtMemoryPage = 2))
                                    } else {
                                        navigator.push(AgendaScreen(txtMemoryPage = 4))
                                    }

                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 100.dp)
                                    .height(50.dp)
                            ) {
                                Text(
                                    text = "המשך",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .zIndex(0f)
                            .padding(8.dp)
                    ) {
                        Image(

                            painter = painterResource(getRoomImageRes(selectedRoom)),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        }
    }
}


fun getRoomImageRes(room: String): DrawableResource {
    return when (room) {
        "חדר שינה" -> Res.drawable.bedroom_block
        "סלון" -> Res.drawable.salon_block
        "מטבח" -> Res.drawable.kitchen_block
        else -> Res.drawable.bedroom_block // на всякий случай дефолтная
    }
}

@Composable
fun DraggableItem(
    imageRes: DrawableResource,
    onDrop: (Offset) -> Unit
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var dragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationX = offset.x
                translationY = offset.y

            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { dragging = true },
                    onDragEnd = {
                        dragging = false
                        onDrop(offset)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                    }
                )
            }
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent)
        )
    }
}