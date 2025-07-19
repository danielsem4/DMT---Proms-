package com.example.new_memory_test.presentation.screens.RoomScreen.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.dialogs.CustomDialog
import com.example.new_memory_test.presentation.components.dialogs.RatingDialog
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import org.jetbrains.compose.resources.painterResource
import com.example.new_memory_test.presentation.screens.CallScreen.CallScreen
import com.example.new_memory_test.presentation.screens.FinalScreenMemoryTest
import com.example.new_memory_test.presentation.screens.RoomScreen.components.DraggableItem
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.components.zonePosition.getZoneForPosition
import com.example.new_memory_test.presentation.screens.RoomScreen.data.DataItem
import com.example.new_memory_test.presentation.screens.ScheduleInformationScreen.ScheduleInformationScreen
import com.example.new_memory_test.presentation.screens.ScheduleScreen.screen.ScheduleScreen
import core.utils.CapturableWrapper
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import core.utils.platformCapturable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import kotlinx.datetime.Clock
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime


class RoomsScreens(val pageNumber: Int) : Screen {
    @OptIn(ExperimentalTime::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()
        var selectedRoom by remember { mutableStateOf(Room.Bedroom) }
        val roomButtons = Room.values().toList()
        var showDialog by remember { mutableStateOf(false) }
        var showDialogEndTime by remember { mutableStateOf(false) }
        var rating by remember { mutableStateOf(0f) }

        var timeNotifications by remember { mutableStateOf(60) }
        var roomPosition by remember { mutableStateOf(Offset.Companion.Zero) }
        var roomSize by remember { mutableStateOf(IntSize.Companion.Zero) }
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }
        var autoSwitchingRooms by remember { mutableStateOf(false) } //switch rooms
        val coroutineScope = rememberCoroutineScope()


        //autoSwitchingRooms = false
        val allItems : List<Pair<Int, DrawableResource>> = listOf(
            Resources.Icon.glassesImage,
            Resources.Icon.bookImage,
            Resources.Icon.dressImage,
            Resources.Icon.phoneImage,
            Resources.Icon.keysImage,
            Resources.Icon.walletImage,
            Resources.Icon.coffeeImage,
            Resources.Icon.backpackIcon,
            Resources.Icon.appIcon,
            Resources.Icon.shoesImages,
            Resources.Icon.recordsIcon,
            Resources.Icon.bottleImage
        ).mapIndexed { index, res -> index to res }
        var lastInteractionTime by remember { mutableStateOf(Clock.System.now()) }
        var showInactivityDialog by remember { mutableStateOf(false) }
        var inactivityCount by remember { mutableStateOf(0) }
        var timeLeft by remember { mutableStateOf(4 * 60) }


        var allItemsPlaced =false
        //Doing random schake of items
        val itemsToShowWithIds = viewModel.getItemsForPage(pageNumber, allItems)

        LaunchedEffect(Unit) {
            while (timeLeft > 0 && inactivityCount < 3) {
                delay(1000L)
                timeLeft -= 1

                val nowMillis = Clock.System.now().toEpochMilliseconds()
                val lastMillis = lastInteractionTime.toEpochMilliseconds()
                val secondsSinceLastInteraction = (nowMillis - lastMillis) / 1000


                if (!showInactivityDialog && secondsSinceLastInteraction >= 60) {
                    showInactivityDialog = true
                }
            }
            if (timeLeft == 0 && inactivityCount < 3 ) {
                showInactivityDialog = true
            }
        }

        if (showDialogEndTime) {
            CustomDialog(
                onDismiss = { },
                icon = { Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White) },
                title = stringResource(Resources.String.time_ended),
                description = stringResource(Resources.String.time_ended_for_this_question_memory),
                buttons = listOf(stringResource(Resources.String.next) to {
                    showDialogEndTime = false

                    when (pageNumber) {
                        2 -> navigator.push(CallScreen(pageNumber = 3))
                        4 -> navigator.push(ScheduleScreen(pageNumber = 5))
                        6 -> {
                           // viewModel.resultRoom ()
                            showDialog = true

                        }
                    }
                })
            )
        }


        if (showInactivityDialog) {
            CustomDialog(
                onDismiss = { },
                icon = {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White)
                },
                 title = "",
                description = if (inactivityCount <2 ){stringResource(Resources.String.first_one_minute_end_body_memory)}
                else{stringResource(Resources.String.second_one_minute_end_body_memory)},
                buttons = listOf(
                    stringResource(Resources.String.continue_button) to {
                        showInactivityDialog = false
                        lastInteractionTime = Clock.System.now()
                        inactivityCount++
                        viewModel.recordInactivity()
                        if (inactivityCount < 3) {
                            timeLeft = 4 * 60
                        } else {

                            when (pageNumber) {
                                2 -> navigator.push(CallScreen(pageNumber = 3))
                                4 -> navigator.push(ScheduleScreen(pageNumber = 5))
                                6 -> {

                                   // viewModel.resultRoom ()
                                    showDialog = true
                                }
                            }
                        }
                    }
                )
            )
        }


        if (showDialog) {
            RatingDialog(
                rating = rating,
                onRatingChanged = { newRating -> rating = newRating },
                onDismiss = { showDialog = false },
                onSubmit = {
                    showDialog = false
                    coroutineScope.launch {
                        viewModel.rawUserRating = rating
                        navigator.replace(FinalScreenMemoryTest())
                    }
                }
            )
        }


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

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseTabletScreen(
            title =stringResource(Resources.String.room_title),
            page = pageNumber,
            totalPages = 6,
            modifier = Modifier.Companion.fillMaxSize().background(color = backgroundColor).pointerInput(Unit) {
                while (true) {
                    awaitPointerEventScope {
                        awaitPointerEvent()
                        lastInteractionTime = Clock.System.now()
                    }
                }

            }
        ) {

            Row(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .background(color = backgroundColor)


            ) {
                Column(
                    modifier = Modifier.Companion
                        .weight(0.4f)
                        .zIndex(1f)
                        .background(color = backgroundColor)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier

                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(vertical = 7.dp, horizontal = 7.dp)
                    ) {
                        Text(
                            text = if(pageNumber==2){stringResource(Resources.String.drag_and_place_instruction)}else{stringResource(Resources.String.instructions_text_memory_question_1)},
                            fontSize = 18.sp,
                            textAlign = TextAlign.Companion.Center,
                            fontWeight = FontWeight.Companion.Bold,
                            color = primaryColor,
                            modifier = Modifier.Companion.padding(bottom = 16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier.Companion.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        roomButtons.forEach { room ->
                            val isSelected = selectedRoom == room
                            Button(
                                onClick = { selectedRoom = room },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Companion.Gray else primaryColor
                                ),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(30),
                                modifier = Modifier.Companion.height(50.dp).width(150.dp)
                            ) {
                                Text(
                                    text = stringResource(room.displayName),
                                    color = Color.Companion.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.Companion.height(20.dp))
                    Row(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier.Companion
                                .background(Color.Companion.White)
                                .zIndex(1f)
                                .border(1.dp, Color.Companion.Black)
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            Column(
                                modifier = Modifier.Companion
                                    .padding(8.dp)
                                    .zIndex(1f)
                            ) {
                                itemsToShowWithIds.chunked(4).forEachIndexed { rowIndex, rowItems ->
                                    Row(
                                        modifier = Modifier.Companion
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
                                                        val relativeOffset =
                                                            globalOffset - roomPosition
                                                        if (isWithinRoom(
                                                                globalOffset,
                                                                roomPosition,
                                                                roomSize
                                                            )
                                                        ) {
                                                            val newZone = getZoneForPosition(
                                                                globalOffset,
                                                                roomPosition,
                                                                roomSize,
                                                                selectedRoom
                                                            )
                                                            val newItem = DataItem(
                                                                id = id,
                                                                resId = res,
                                                                isPlaced = true,
                                                                position = relativeOffset,
                                                                room = selectedRoom,
                                                                zone = newZone
                                                            )
                                                            viewModel.saveItemForRound(
                                                                newItem,
                                                                pageNumber
                                                            )

                                                        } else {
                                                            viewModel.removeItem(id)
                                                        }
                                                    },
                                                    selectedRoom = selectedRoom,
                                                    placedItems = viewModel.placedItems
                                                )
                                            } else {
                                                Spacer(modifier = Modifier.Companion.size(80.dp))
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
                          fontWeight = FontWeight.Companion.Bold
                      )
                      if (viewModel.placedItems.isEmpty()) {
                          Text(
                              text = "Нет размещенных предметов",
                              fontSize = 14.sp,
                              color = Color.Companion.Gray
                          )
                      } else {
                          viewModel.placedItems.forEach { item ->
                              Text(
                                  text = "Предмет ID: ${item.id}, Зона: ${item.zone?.displayName ?: "Неизвестно"}, Комната: ${item.room?.displayName ?: "Нет"}",
                                  fontSize = 14.sp,
                                  color = Color.Companion.Black
                              )
                          }
                      }
                    if (pageNumber == 2 ){
                        if( viewModel.placedItems.size == 8) {
                            allItemsPlaced = true
                        }
                    }
                    else{
                        allItemsPlaced = true
                    }

                    Spacer(modifier = Modifier.Companion.height(12.dp))
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Companion.CenterVertically
                    ) {
                        Text(
                            text = formatTime(timeLeft),
                            fontSize = EXTRA_MEDIUM,
                            color = primaryColor,
                            fontWeight = FontWeight.Companion.Bold
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    autoSwitchingRooms = true
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                shape = RoundedCornerShape(30),
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 100.dp)
                                    .width(250.dp)
                                    .height(50.dp) ,
                                enabled = allItemsPlaced,

                            ) {
                                Text(
                                    text = stringResource(Resources.String.next),
                                    fontSize = EXTRA_MEDIUM,
                                    fontWeight = FontWeight.Companion.Bold,
                                    color = Color.Companion.White
                                )
                            }
                        }

                    }
                }
                Column(
                    modifier = Modifier.Companion
                        .weight(0.6f)
                        .fillMaxHeight()
                ) {
                    //Area of screenshot of the picture
                    capturable = platformCapturable(
                        modifier = Modifier.weight(1f),
                        onCaptured = { imageBitmap ->
                            val timestamp = getCurrentFormattedDateTime()
                            when (pageNumber) {
                                2 -> {
                                    viewModel.image1.value = viewModel.image1.value.plus(imageBitmap)
                                    viewModel.timeForImage1.value = timestamp
                                    viewModel.pageNumForImage1.value =  2
                                    println("Image captured: $imageBitmap")
                                    println("Image captured: $timestamp")
                                }
                                4 -> {
                                    viewModel.image2.value = viewModel.image2.value.plus(imageBitmap)
                                    viewModel.timeForImage2.value = timestamp
                                    viewModel.pageNumForImage2.value =  4
                                    println("Image captured: $imageBitmap")
                                }
                                6 -> {
                                    viewModel.image3.value = viewModel.image3.value.plus(imageBitmap)
                                    viewModel.timeForImage3.value = timestamp
                                    viewModel.pageNumForImage3.value =  6

                                }
                            }
                        }
                    )
                    {
                        Box(
                            modifier = Modifier.Companion
                                .weight(0.6f)
                                .fillMaxHeight()
                                .onGloballyPositioned { coordinates ->
                                    roomPosition = coordinates.localToWindow(Offset.Companion.Zero)
                                    roomSize = coordinates.size
                                }
                        ) {
                            Image(
                                painter = painterResource(selectedRoom.imageRes),
                                contentDescription = null,
                                modifier = Modifier.Companion
                                    .fillMaxSize()
                                    .zIndex(0f),
                                contentScale = ContentScale.Companion.Crop
                            )
                            viewModel.placedItems.filter { it.room == selectedRoom }
                                .forEach { item ->
                                    DraggableItem(
                                        id = item.id,
                                        imageRes = item.resId,
                                        onDrop = { id, globalOffset ->
                                            val relativeOffset = globalOffset - roomPosition
                                            if (isWithinRoom(
                                                    globalOffset,
                                                    roomPosition,
                                                    roomSize
                                                )
                                            ) {
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
                                        roomPosition = roomPosition
                                    )
                                }
                        }
                    }
                    }
                    LaunchedEffect(autoSwitchingRooms) {
                        if (autoSwitchingRooms) {
                            val delayBetween = 500L
                            val switchOrder = listOf(Room.Bedroom, Room.LivingRoom, Room.Kitchen)

                            for (room in switchOrder) {
                                selectedRoom = room

                                delay(delayBetween)
                                //need to check if we need a delay after
                                capturable?.capture?.invoke()
                                delay(30L)
                            }

                            autoSwitchingRooms = false

                            if (pageNumber == 2) {
                                viewModel.setPage(viewModel.txtMemoryPage + 1)
                                navigator.push(CallScreen(pageNumber = 3))
                            } else if (pageNumber == 4) {
                                viewModel.setPage(viewModel.txtMemoryPage + 1)
                                navigator.push(ScheduleInformationScreen(pageNumber = 5))
                            } else if (pageNumber == 6) {

                                showDialog = true



                            }

                        }
                    }
                }
            }
        }

        RegisterBackHandler(this)
        {
            navigator.popUntilRoot()
        }
    }
    private fun isWithinRoom(position: Offset, roomPosition: Offset, roomSize: IntSize): Boolean {
        return position.x >= roomPosition.x && position.x <= roomPosition.x + roomSize.width &&
                position.y >= roomPosition.y && position.y <= roomPosition.y + roomSize.height
    }
}