package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.hi.heal.memoryTest.core.presentation.data.backgroundColor
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.ViewModelMemoryTest.ViewModelMemoryTest
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.dialogs.RatingDialog
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.AgendaScreen
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.BaseTabletScreen
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.CallScreen
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.DraggableItem
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.enum.Room
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.components.zonePosition.getZoneForPosition
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.data.DataItem
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.app
import dmt_proms.memorytest.core.generated.resources.backpack
import dmt_proms.memorytest.core.generated.resources.book
import dmt_proms.memorytest.core.generated.resources.bottle
import dmt_proms.memorytest.core.generated.resources.coffee
import dmt_proms.memorytest.core.generated.resources.dress
import dmt_proms.memorytest.core.generated.resources.glasses
import dmt_proms.memorytest.core.generated.resources.keys
import dmt_proms.memorytest.core.generated.resources.phone
import dmt_proms.memorytest.core.generated.resources.records
import dmt_proms.memorytest.core.generated.resources.room_title
import dmt_proms.memorytest.core.generated.resources.shoes
import dmt_proms.memorytest.core.generated.resources.wallet
import io.github.suwasto.capturablecompose.Capturable
import io.github.suwasto.capturablecompose.CompressionFormat
import io.github.suwasto.capturablecompose.rememberCaptureController
import io.github.suwasto.capturablecompose.toByteArray

import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel



class RoomsScreens(val pageNumber: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()
        var selectedRoom by remember { mutableStateOf(Room.Bedroom) }
        val roomButtons = Room.values().toList()
        var showDialog by remember { mutableStateOf(false) }
        var rating by remember { mutableStateOf(0f) }
        var timeLeft by remember { mutableStateOf(4 * 60) }
        var roomPosition by remember { mutableStateOf(Offset.Zero) }
        var roomSize by remember { mutableStateOf(IntSize.Zero) }
        val captureController = rememberCaptureController()

        var autoSwitchingRooms by remember { mutableStateOf(false) }

        //autoSwitchingRooms = false
        val allItems : List<Pair<Int, DrawableResource>> = listOf(
            Res.drawable.glasses, Res.drawable.book, Res.drawable.dress,
            Res.drawable.phone, Res.drawable.keys, Res.drawable.wallet,
            Res.drawable.coffee, Res.drawable.backpack, Res.drawable.app,
            Res.drawable.shoes, Res.drawable.records, Res.drawable.bottle
        ).mapIndexed { index, res -> index to res }

        //Doing random schake of items
        val itemsToShowWithIds = viewModel.getItemsForPage(pageNumber, allItems)

        LaunchedEffect(Unit) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft -= 1
            }
            if(timeLeft==0) {
                if (pageNumber == 2) {
                    navigator.push(CallScreen(pageNumber = 3))
                }
                if (pageNumber == 4) {
                    navigator.push(AgendaScreen(pageNumber = 5))
                }
                if (pageNumber == 6) {
                    viewModel.checkFinalScore()
                    showDialog = true
                }
            }
        }

        // Инициализация ID один раз
        LaunchedEffect(Unit) {
            viewModel.initializeItemIdsIfNeeded(allItems.indices.toList())
        }

        LaunchedEffect(Unit) {
            if (pageNumber == 4 || pageNumber == 6) {
                viewModel.clearPlacedItems()
            }
        }

        fun formatTime(seconds: Int): String {
            val minutesPart = (seconds / 60).toString().padStart(2, '0')
            val secondsPart = (seconds % 60).toString().padStart(2, '0')
            return "$minutesPart:$secondsPart"
        }


        BaseTabletScreen(title = stringResource(Res.string.room_title), page = pageNumber, totalPages = 6) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .zIndex(1f)
                        .background(color = backgroundColor)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "גרור ומקם את שבעת החצפים בכל שלשת החדרים, כפי שהיית ממקם בביתך. עליך לזכור את מיקום החפצים. בסיום לחץ על המשך",
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
                                    text = room.displayName,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .zIndex(1f)
                                .border(1.dp, Color.Black)
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .zIndex(1f)
                            ) {
                                itemsToShowWithIds.chunked(4).forEachIndexed { rowIndex, rowItems ->
                                    Row(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .zIndex(1f)
                                    ) {
                                        rowItems.forEach { (itemId, res) ->
                                            val shouldShowOnPalette = when (pageNumber) {
                                                2 -> viewModel.placedItems.none { it.id == itemId }
                                                4, 6 -> true
                                                else -> true
                                            }

                                            if (shouldShowOnPalette) {
                                                DraggableItem(
                                                    id = itemId,
                                                    imageRes = res,
                                                    onDrop = { id, globalOffset ->
                                                        val relativeOffset = globalOffset - roomPosition
                                                        if (isWithinRoom(globalOffset, roomPosition, roomSize)) {
                                                            val newZone = getZoneForPosition(globalOffset, roomPosition, roomSize, selectedRoom)
                                                            val newItem = DataItem(
                                                                id = id,
                                                                resId = res,
                                                                isPlaced = true,
                                                                position = relativeOffset,
                                                                room = selectedRoom,
                                                                zone = newZone
                                                            )
                                                            viewModel.saveItemForRound(newItem, pageNumber)
                                                        } else {
                                                            viewModel.removeItem(id)
                                                        }
                                                    },
                                                    selectedRoom = selectedRoom,
                                                    placedItems = viewModel.placedItems
                                                )
                                            } else {
                                                Spacer(modifier = Modifier.size(80.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        text = "Размещенные предметы: (Количество: ${viewModel.placedItems.size})",
                        fontSize = 16.sp,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                    if (viewModel.placedItems.isEmpty()) {
                        Text(
                            text = "Нет размещенных предметов",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    } else {
                        viewModel.placedItems.forEach { item ->
                            Text(
                                text = "Предмет ID: ${item.id}, Зона: ${item.zone?.displayName ?: "Неизвестно"}, Комната: ${item.room?.displayName ?: "Нет"}",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
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
                                    autoSwitchingRooms = true
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
                        if (showDialog) {
                            RatingDialog(
                                rating = rating,
                                onRatingChanged = { newRating -> rating = newRating },
                                onDismiss = { showDialog = false },
                                onSubmit = { showDialog = false }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                ) {

                //Area of screenshot of the picture
                Capturable(
                    captureController = captureController,
                    onCaptured = { imageBitmap ->
                        //check where we need to save screen
                        if (imageBitmap != null) {
                           if (pageNumber == 2){
                               viewModel.saveRoomScreenshotFirst(selectedRoom,imageBitmap)
                           }
                            if (pageNumber == 4){
                               viewModel.saveRoomScreenshotSecond(selectedRoom,imageBitmap)
                            }
                            if (pageNumber == 6) {
                                viewModel.saveRoomScreenshotThird(selectedRoom, imageBitmap)
                            }
                        }
                        val byteArray = imageBitmap.toByteArray(compressionFormat = CompressionFormat.PNG, quality = 100)
                        viewModel.saveScreenshot(byteArray)
                    }

                )
                {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                        .onGloballyPositioned { coordinates ->
                            roomPosition = coordinates.localToWindow(Offset.Zero)
                            roomSize = coordinates.size
                        }
                ) {
                    Image(
                        painter = painterResource(selectedRoom.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(0f),
                        contentScale = ContentScale.Crop
                    )

                    viewModel.placedItems.filter { it.room == selectedRoom }.forEach { item ->
                        DraggableItem(
                            id = item.id,
                            imageRes = item.resId,
                            onDrop = { id, globalOffset ->
                                val relativeOffset = globalOffset - roomPosition
                                if (isWithinRoom(globalOffset, roomPosition, roomSize)) {
                                    val newZone = getZoneForPosition(
                                        globalOffset,
                                        roomPosition,
                                        roomSize,
                                        selectedRoom
                                    )
                                    val updatedItem = item.copy(
                                        position = relativeOffset,
                                        room = selectedRoom,
                                        zone = newZone
                                    )
                                    viewModel.saveItemForRound(updatedItem, pageNumber)
                                } else {
                                    viewModel.removeItem(id)
                                }
                            },
                            selectedRoom = selectedRoom,
                            placedItems = viewModel.placedItems,
                            isOnRoom = true,
                            roomPosition = roomPosition // ← теперь передаём положение всей комнаты
                        )
                    }
                }
                }
                    LaunchedEffect(autoSwitchingRooms) {
                        if (autoSwitchingRooms) {
                            val delayBetween = 400L
                            val switchOrder = listOf(Room.Bedroom, Room.LivingRoom, Room.Kitchen)

                            for (room in switchOrder) {
                                selectedRoom = room
                                delay(delayBetween)
                                //We do a screenshoot of the rooms
                                captureController.capture()
                                delay(100L)//need to check if we need a delay after
                            }
                            autoSwitchingRooms = false
                            if (pageNumber == 2) {
                                viewModel.setPage(viewModel.txtMemoryPage + 1)
                                navigator.push(CallScreen(pageNumber = 3))
                            } else if (pageNumber == 4) {
                                viewModel.setPage(viewModel.txtMemoryPage + 1)
                                navigator.push(AgendaScreen(pageNumber = 5))
                            } else if (pageNumber == 6) {
                                viewModel.checkFinalScore()
                                showDialog = true
                            }
                        }
                    }
            }
            }
        }
    }
    private fun isWithinRoom(position: Offset, roomPosition: Offset, roomSize: IntSize): Boolean {
        return position.x >= roomPosition.x && position.x <= roomPosition.x + roomSize.width &&
                position.y >= roomPosition.y && position.y <= roomPosition.y + roomSize.height
    }
}