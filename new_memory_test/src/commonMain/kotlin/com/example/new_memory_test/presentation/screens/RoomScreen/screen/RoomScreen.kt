package com.example.new_memory_test.presentation.screens.RoomScreen.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.Text
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.dialogs.RatingDialog
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
import kotlinx.datetime.Clock
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.buttonHeightMd
import org.example.hit.heal.core.presentation.Sizes.elevationSm
import org.example.hit.heal.core.presentation.Sizes.height7Xl
import org.example.hit.heal.core.presentation.Sizes.heightMd
import org.example.hit.heal.core.presentation.Sizes.heightXl
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.spacing6Xl
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.widthMd_Lg
import org.example.hit.heal.core.presentation.Sizes.widthXl
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.dialogs.CustomDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
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
        val coroutineScope = rememberCoroutineScope()

        viewModel.txtMemoryPage = pageNumber
        //Dialogs and raiting

        var showDialogEndTime by remember { mutableStateOf(false) }
        var showInactivityDialog by remember { mutableStateOf(false) }
        var rating by remember { mutableStateOf(0f) }

        //Rooms
        var selectedRoom by remember { mutableStateOf(Room.Bedroom) }
        val roomButtons = Room.values().toList()
        var roomPosition by remember { mutableStateOf(Offset.Companion.Zero) }
        var roomSize by remember { mutableStateOf(IntSize.Zero) }

        //Screnschot photo
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }
        var autoSwitchingRooms by remember { mutableStateOf(false) }


        var showDialog by remember { mutableStateOf(false) }
        var triggerNavigation by remember { mutableStateOf(false) }

        //Items
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
        var allItemsPlaced =false//if all items placed (page 2)
        val itemsToShowWithIds = viewModel.getItemsForPage(pageNumber, allItems)   //Doing random schake of items


        //Time and inactivity  places
        var lastInteractionTime by remember { mutableStateOf(Clock.System.now()) }
        var inactivityCount by remember { mutableStateOf(0) }
        var timeLeft by remember { mutableStateOf(4 * 60) }


        val draggableSize = rememberItemSizePx()


        //---------------LaunchedEffect
        //if person don't do  something for 1 minutes and less than 3 times  - async
        LaunchedEffect(Unit) {
            while (inactivityCount < 3) {
                if (!showInactivityDialog && timeLeft > 0) {
                    delay(1000L)
                    timeLeft -= 1
                } else {
                    delay(100L)
                }

                val nowMillis = Clock.System.now().toEpochMilliseconds()
                val lastMillis = lastInteractionTime.toEpochMilliseconds()
                val secondsSinceLastInteraction = (nowMillis - lastMillis) / 1000

                if (!showInactivityDialog && secondsSinceLastInteraction >= 60) {
                    showInactivityDialog = true
                }

                if (timeLeft == 0 && inactivityCount < 3) {
                    showDialogEndTime = true
                    break
                }
            }
        }
        //If came to screen - create items for dragging
        LaunchedEffect(Unit) {
            viewModel.initializeItemIdsIfNeeded(allItems.indices.toList())
        }
        //Clean Items in image when come back to screen (only 4 and 6 )
        LaunchedEffect(Unit) {
            if (pageNumber == 4 || pageNumber == 6) {
                viewModel.clearPlacedItems()
            }
        }
        //Switching Room
        LaunchedEffect(autoSwitchingRooms) {
            if (autoSwitchingRooms) {
                val delayBetween = 500L
                val switchOrder = listOf(Room.Bedroom, Room.LivingRoom, Room.Kitchen)

                for (room in switchOrder) {
                    selectedRoom = room
                    delay(delayBetween)
                    capturable?.capture?.invoke()// doing all times a screen
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
                    //call a raiting in the end
                    showDialog = true

                }

            }
        }


        //----------------Dialogs
        //If end time (4 minutes)
        if (showDialogEndTime) {
            CustomDialog(
                icon = Resources.Icon.clockIcon,
                title = stringResource(Resources.String.time_ended),
                description = stringResource(Resources.String.time_ended_for_this_question_memory),
                buttons = listOf(
                    stringResource(Resources.String.next) to {
                        showDialogEndTime = false
                        autoSwitchingRooms = true

                    }
                ),
                onDismiss = {
                    showDialogEndTime = true
                }
            )
        }
        //Dialog for inactives user
        if (showInactivityDialog) {
            CustomDialog(
                icon = Resources.Icon.clockIcon,
                title = stringResource(Resources.String.time),
                description = if (inactivityCount < 2) {
                    stringResource(Resources.String.first_one_minute_end_body_memory)
                } else {
                    stringResource(Resources.String.second_one_minute_end_body_memory)
                },
                buttons = listOf(
                    stringResource(Resources.String.yes) to {
                        showInactivityDialog = false
                        lastInteractionTime = Clock.System.now()
                        inactivityCount++
                        viewModel.recordInactivity()
                        if (inactivityCount < 3) {
                            timeLeft = 4 * 60
                        } else {
                            autoSwitchingRooms = true
                        }

                    }
                ),
                onDismiss = {
                    showInactivityDialog = true
                }
            )
        }

        //Rating dialog (in the end)
        if (showDialog) {
            RatingDialog(
                rating = rating,
                onRatingChanged = { newRating -> rating = newRating },
                onDismiss = {
                    showDialog = true
                },
                onSubmit = {
                    viewModel.rawUserRating = rating
                    showDialog = false
                    navigator.push(FinalScreenMemoryTest())

                }
            )
        }




        fun formatTime(seconds: Int): String {
            val minutesPart = (seconds / 60).toString().padStart(2, '0')
            val secondsPart = (seconds % 60).toString().padStart(2, '0')
            return "$minutesPart:$secondsPart"
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseScreen(
            title =stringResource(Resources.String.room_title),
            topRightText = "$pageNumber/6",
            config = ScreenConfig.TabletConfig,
            modifier = Modifier.Companion.fillMaxSize().background(color = backgroundColor).pointerInput(Unit) {
                while (true) {
                    awaitPointerEventScope {
                        awaitPointerEvent() // await for some event (drag, touch)
                        lastInteractionTime = Clock.System.now()// the last time of interaction (for dialog interaction)
                    }
                }

            },
            content =
         {
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
                        .padding(paddingMd)
                ) {

                    //-------------------Text of instruction
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(paddingSm))
                            .border(elevationSm, Color.Black, RoundedCornerShape(paddingSm))
                            .padding(vertical =paddingSm, horizontal =paddingSm)
                    ) {
                        Text(
                            //Change  a text because it depend of number of page
                            text = if(pageNumber==2){stringResource(Resources.String.drag_and_place_instruction)}else{stringResource(Resources.String.drag_and_place_instruction)},
                            fontSize = MEDIUM,
                            textAlign = TextAlign.Companion.Center,
                            fontWeight = FontWeight.Companion.Bold,
                            color = primaryColor,
                            modifier = Modifier.Companion.padding(bottom = paddingMd)
                        )
                    }

                    //--------------------Buttons of changing rooms
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth().padding(top = paddingMd),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {//Change room and color of Button (if choose)
                        roomButtons.forEach { room ->
                            val isSelected = selectedRoom == room
                            Button(
                                onClick = { selectedRoom = room },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Companion.Gray else primaryColor
                                ),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(30),
                                modifier = Modifier.Companion.height(buttonHeightMd).width(widthMd_Lg)
                            ) {
                                Text(
                                    text = stringResource(room.displayName),
                                    color = Color.Companion.White,
                                    fontSize =EXTRA_REGULAR
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.Companion.height(20.dp))
                    //If use page number 2 ->only 8 Items , in other case - all items (12)
                    if (pageNumber == 2 ){
                        if( viewModel.placedItems.size == 7) {
                            allItemsPlaced = true
                        }
                    }
                    else{
                        allItemsPlaced = true
                    }

                    //----------------Place Items in Box (2 rows or 3 rows)
                    Row(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .background(Color.White)
                                .zIndex(1f)
                                .border(elevationSm, Color.Black)
                                .fillMaxWidth()
                                .height(height7Xl)
                        ) {
                            val itemCountPerRow = 4
                            val itemSpacing = 16.dp
                            val itemSize = (maxWidth - itemSpacing * (itemCountPerRow + 1)) / itemCountPerRow

                            Column(
                                modifier = Modifier.Companion
                                    .padding(8.dp)
                                    .zIndex(1f)
                            ) {
                                itemsToShowWithIds.chunked(4).forEachIndexed { rowIndex, rowItems -> //seporate items to 2 rows (4 items in row) - question 2
                                    Row(
                                        modifier = Modifier.Companion
                                            .padding(8.dp)
                                            .zIndex(1f)
                                    ) {
                                        rowItems.forEach { (itemId, res) ->

                                            //if page= 2 - we don't use it (only 8 items) - 4 is invisible
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
                                                        //where need be  Item  = globalOffset - roomPosition(from left top corner)
                                                        val relativeOffset = globalOffset - roomPosition
                                                        val targetSize = roomSize.width.toFloat() to roomSize.height.toFloat()
                                                        if (isObjectInsideTargetArea(
                                                                targetPosition = roomPosition,
                                                                draggablePosition = globalOffset,
                                                                targetSize = targetSize,
                                                                draggableSize = draggableSize,
                                                                isCircle = false,
                                                                threshold = 50f
                                                            )
                                                        ) {
                                                            //check the room and create item for save in viewModel
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

                                                        } else { // it is not in the room (in a box )
                                                            viewModel.removeItem(id)
                                                        }
                                                    },
                                                    selectedRoom = selectedRoom,
                                                    placedItems = viewModel.placedItems,
                                                    size = calculateItemSize(roomSize)
                                                )
                                            } else {
                                                Spacer(modifier = Modifier.Companion.size(spacing6Xl))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.Companion.height(spacingMd))

                    //--------------------Timer and button to next page
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
                                .padding(top = spacingMd),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    autoSwitchingRooms = true//in the end switch room and photo
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                shape = RoundedCornerShape(30),
                                modifier = Modifier
                                    .defaultMinSize(minWidth = heightXl)
                                    .width(widthXl)
                                    .height(heightMd) ,
                                enabled = allItemsPlaced, //if all items placed -> (we see a button) - only for page 2

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
                //-----------------------Image of Rooms and Items on Images
                Column(
                    modifier = Modifier.Companion
                        .weight(0.6f)
                        .fillMaxHeight()
                ) {
                    //Area of screenshot of the picture
                    capturable = platformCapturable(
                        modifier = Modifier.weight(1f),
                        onCaptured = { imageBitmap ->
                            //Save image in viewModel and all we need for Image (depends of page number)
                            val timestamp = getCurrentFormattedDateTime()
                            when (pageNumber) {
                                2 -> {
                                    viewModel.image1.value = viewModel.image1.value.plus(imageBitmap)
                                    viewModel.timeForImage1.value = timestamp
                                    viewModel.pageNumForImage1.value =  2
                                    println("Image captured: $imageBitmap")
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
                                    println("Image captured: $imageBitmap")

                                }
                            }
                        }
                    )
                    {
                        Box(
                            modifier = Modifier.Companion
                                .weight(0.7f)
                                .onGloballyPositioned { coordinates -> //global size and pixel
                                    roomPosition = coordinates.localToScreen(Offset.Companion.Zero)
                                    roomSize = coordinates.size
                                }
                        ) {
                            //Big Image-Room
                            Image(
                                painter = painterResource(selectedRoom.imageRes),
                                contentDescription = null,
                                modifier = Modifier.Companion
                                    .fillMaxSize()
                                    .zIndex(0f),
                                contentScale = ContentScale.Companion.Crop
                            )
                            //Placed only Items in Image that now in specific room
                            viewModel.placedItems.filter { it.room == selectedRoom }
                                .forEach { item ->
                                    DraggableItem(
                                        id = item.id,
                                        size = calculateItemSize(roomSize),
                                        imageRes = item.resId,
                                        onDrop = { id, globalOffset ->
                                            val relativeOffset = globalOffset - roomPosition
                                            val targetSize = roomSize.width.toFloat() to roomSize.height.toFloat()

                                            if (isObjectInsideTargetArea(
                                                    targetPosition = roomPosition,
                                                    draggablePosition = globalOffset,
                                                    targetSize = targetSize,
                                                    draggableSize = draggableSize,
                                                    isCircle = false,
                                                    threshold = 50f
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
                                }   }
                        }
                    }
                }
            })
        }

        //For unposible go to back screen
        RegisterBackHandler(this)
        {
            navigator.popUntilRoot()
        }
    }



    fun isObjectInsideTargetArea(
        targetPosition: Offset,
        draggablePosition: Offset,
        targetSize: Pair<Float, Float>,
        draggableSize: Pair<Float, Float> = 0f to 0f,
        isCircle: Boolean,
        threshold: Float = 0f
    ): Boolean {
        val (targetWidth, targetHeight) = targetSize
        val (draggableWidth, draggableHeight) = draggableSize

        val targetLeft = targetPosition.x - threshold
        val targetTop = targetPosition.y - threshold
        val targetRight = targetPosition.x + targetWidth + threshold
        val targetBottom = targetPosition.y + targetHeight + threshold

        val draggableLeft: Float
        val draggableTop: Float
        val draggableRight: Float
        val draggableBottom: Float

        if (isCircle) {
            val draggableRadius = draggableWidth / 2
            draggableLeft = draggablePosition.x - draggableRadius
            draggableTop = draggablePosition.y - draggableRadius
            draggableRight = draggablePosition.x + draggableRadius
            draggableBottom = draggablePosition.y + draggableRadius
        } else {
            draggableLeft = draggablePosition.x
            draggableTop = draggablePosition.y
            draggableRight = draggableLeft + draggableWidth
            draggableBottom = draggableTop + draggableHeight
        }

        val isInside = draggableLeft >= targetLeft && draggableRight <= targetRight &&
                draggableTop >= targetTop && draggableBottom <= targetBottom

        return isInside
    }

    @Composable
    fun rememberItemSizePx(): Pair<Float, Float> {
        val density = LocalDensity.current
        val sizePx = with(density) { 40.dp.toPx() }
        return sizePx to sizePx
    }

    @Composable
    fun calculateItemSize(roomSize: IntSize): Dp {
        val minDimension = minOf(roomSize.width, roomSize.height)
        val itemPx = minDimension / 6
        return with(LocalDensity.current) { itemPx.toDp() }
    }

}