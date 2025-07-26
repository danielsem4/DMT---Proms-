package com.example.new_memory_test.presentation.screens.ScheduleScreen.screen
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import core.utils.CapturableWrapper
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import core.utils.platformCapturable
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import com.example.new_memory_test.presentation.components.dialogs.CustomDialog
import com.example.new_memory_test.presentation.screens.ScheduleScreen.components.DraggableSlotPalet
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.GenericSlotBox
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.components.SlotState
import kotlin.collections.mapValues
class ScheduleScreen(val pageNumber: Int ) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()
        viewModel.txtMemoryPage = pageNumber
        //Schedule
        val days = listOf(
            stringResource(Resources.String.day_sunday),
            stringResource(Resources.String.day_monday),
            stringResource(Resources.String.day_tuesday),
            stringResource(Resources.String.day_wednesday),
            stringResource(Resources.String.day_thursday)
        )
        val hours = listOf(
            stringResource(Resources.String.hour_09_00),
            stringResource(Resources.String.hour_10_00),
            stringResource(Resources.String.hour_11_00),
            stringResource(Resources.String.hour_12_00),
            stringResource(Resources.String.hour_13_00),
            stringResource(Resources.String.hour_14_00),
            stringResource(Resources.String.hour_15_00),
            stringResource(Resources.String.hour_16_00)
        )

        val dragAndDropState = rememberDragAndDropState<SlotState>()
        val droppedState by viewModel.droppedState.collectAsState()

        val circlesPalletFirst = listOf(
            SlotState(
                id =stringResource(Resources.String.book_circle),
               painter=  painterResource(Resources.Icon.bookIcon)
            ),
            SlotState(
                id =stringResource(Resources.String.dumbbell_circle),
                painter=painterResource(Resources.Icon.dumblertIcon)),

            SlotState(
                id =stringResource(Resources.String.move_circle),
                painter=painterResource(Resources.Icon.moveIcon))

        )
        val circlesPalletSecond = listOf(
            SlotState(
                id= stringResource(Resources.String.lecturer_circle),
                painter=painterResource(Resources.Icon.teachIcon)
            ),
            SlotState(
                id =stringResource(Resources.String.coffee_circle),
                painter=painterResource(Resources.Icon.coffeIcon)
            ),
            SlotState(
                id =stringResource(Resources.String.stethoscope_circle),
                painter=painterResource(Resources.Icon.stethoscopeImage)
            )
        )
        val idToTextMap = mapOf(
            stringResource(Resources.String.book_circle) to stringResource(Resources.String.book_circle_text) ,
            stringResource(Resources.String.dumbbell_circle) to stringResource(Resources.String.dumbbell_circle_text),
            stringResource(Resources.String.move_circle) to stringResource(Resources.String.move_circle_text),
            stringResource(Resources.String.lecturer_circle) to stringResource(Resources.String.lecturer_circle_text),
            stringResource(Resources.String.coffee_circle)  to stringResource(Resources.String.coffee_circle_text),
            stringResource(Resources.String.stethoscope_circle) to stringResource(Resources.String.stethoscope_circle_text)
        ) // information depend of witch circle use
        val usedSlotIds = remember { mutableStateListOf<String>() }
        val selectedItemText = remember { mutableStateOf<String?>(null) }

        //Create Image
        var capturable by remember { mutableStateOf<CapturableWrapper?>(null) }

        //Update selected item if used
        LaunchedEffect(dragAndDropState.draggedItem) {
            val item = dragAndDropState.draggedItem?.data
            selectedItemText.value = item?.let { idToTextMap[it.id] }
        }

        //Dialog of place all Items
        var showAcceptDialog by remember { mutableStateOf(false) }
        if (showAcceptDialog) {
            CustomDialog(
                onDismiss = { showAcceptDialog = false },
                icon = {
                   // Icon(
                   //     Icons.Default.ThumbUp,
                   //     contentDescription = null,
                   //     tint = Color.White,
                   //     modifier = Modifier.size(40.dp)
                   // )
                },
                title = stringResource(Resources.String.build_schedule),
                description = stringResource(Resources.String.please_place_all_activities_memory),
                buttons = listOf(
                    stringResource(Resources.String.next) to {
                        showAcceptDialog = false
                    }
                )
            )
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
            BaseScreen(
                title =stringResource(Resources.String.build_schedule),
                topRightText = "$pageNumber/6",
                config = ScreenConfig.TabletConfig,
                modifier = Modifier.Companion.fillMaxSize().background(color = backgroundColor),
                content = {
                DragAndDropContainer(state = dragAndDropState) {
                    capturable = platformCapturable(
                        //Save Screen like Image
                        onCaptured = { imageBitmap ->
                            val timestamp = getCurrentFormattedDateTime()
                            viewModel.imageUrl.value = viewModel.imageUrl.value.plus(imageBitmap)
                            viewModel.timeUrl.value = timestamp
                            viewModel.pageNumForUrl.value =pageNumber
                        }
                    )
                    {

                        Column {

                            //-------------------Days in the Top
                            Row(
                                modifier = Modifier
                                    .weight(0.08f)
                                    .padding(horizontal = 5.dp)
                                    .background(Color.Transparent),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                        .background(Color.White),
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {

                                    days.reversed().forEach { day ->
                                        Box(
                                            modifier = Modifier
                                                .weight(0.6f)
                                                .padding(3.dp)
                                                .fillMaxHeight()
                                                .clip(RoundedCornerShape(15.dp))
                                                .background(Color.Gray),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = day,
                                                color = Color.White,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = EXTRA_MEDIUM
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.weight(0.4f))
                            }

                            //-------------------Schedule box with 40 Items
                            Row(
                                modifier = Modifier
                                    .weight(0.7f)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .padding(horizontal = 5.dp, vertical = 5.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.Transparent)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .background(Color.White, RoundedCornerShape(8.dp))
                                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                            .fillMaxSize()
                                            .padding(vertical = 10.dp)
                                    ) {
                                        Column( modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                            )
                                             {
                                            //Create Id for box , that depends on day and hour (for 40 boxes)
                                            for (hour in listOf(
                                                stringResource(Resources.String.hour_09_00),
                                                stringResource(Resources.String.hour_10_00),
                                                stringResource(Resources.String.hour_11_00),
                                                stringResource(Resources.String.hour_12_00),
                                                stringResource(Resources.String.hour_13_00),
                                                stringResource(Resources.String.hour_14_00),
                                                stringResource(Resources.String.hour_15_00),
                                                stringResource(Resources.String.hour_16_00),
                                            )) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f)
                                                        .heightIn(min = 30.dp)
                                                        .padding(4.dp),

                                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                ) {
                                                    for (day in 1..5) {
                                                        GenericSlotBox(
                                                            slotId = "day_${day}_hour_$hour",
                                                            droppedState = droppedState,
                                                            dragAndDropState = dragAndDropState,
                                                            usedDraggableIds = usedSlotIds,
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .fillMaxHeight(),
                                                            onUpdateDroppedState = { slotId, slotState ->
                                                                viewModel.updateDroppedState(slotId,slotState)},
                                                            defaultBackgroundColor = Color.Gray,
                                                            activeBackgroundColor = primaryColor,
                                                            borderColor = Color.Transparent

                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                Spacer(modifier = Modifier.size(7.dp))

                                //--------------------- Instructions of exam
                                Column(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxHeight()
                                        .background(Color.White, RoundedCornerShape(8.dp))
                                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 5.dp, vertical = 5.dp)
                                ) {
                                    hours.forEach { hour ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(70.dp)
                                                .padding(vertical = 15.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(vertical = 1.dp, horizontal = 3.dp)
                                                    .fillMaxHeight()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(Color.White),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = hour,
                                                    color = primaryColor,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = LARGE,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                                //
                                Column(
                                    modifier = Modifier
                                        .weight(0.3f)

                                        .padding(10.dp)
                                ) {

                                    Box(
                                        modifier = Modifier

                                            .background(Color.White, RoundedCornerShape(8.dp))
                                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                            .padding(vertical = 7.dp, horizontal = 7.dp)
                                    ) {

                                        Text(
                                            text = stringResource(Resources.String.place_instructions_in_calendar_memory),
                                            fontSize = 25.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            color = primaryColor,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )
                                    }

                                    //-----------------Instructions only for item
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (selectedItemText.value.isNullOrBlank()) Color.Transparent else Color.White,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .border(
                                                1.dp,
                                                if (selectedItemText.value.isNullOrBlank()) Color.Transparent else Color.Black,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(vertical = 7.dp, horizontal = 7.dp)
                                            .fillMaxWidth()
                                            .height(110.dp)
                                    ) {
                                        if (!selectedItemText.value.isNullOrBlank()) {
                                            Text(
                                                text = selectedItemText.value!!,
                                                fontSize = EXTRA_MEDIUM,
                                                color = primaryColor,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                            )
                                        }
                                    }

                                    //-----------------Two rows of draggable circle (palet)
                                    Row(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp, top = 10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                                    ) {
                                        DraggableSlotPalet(
                                            slot = circlesPalletFirst,
                                            dragAndDropState = dragAndDropState,
                                            onCircleClicked = { slot ->
                                                selectedItemText.value = idToTextMap[slot.id]
                                            }
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                                    ) {
                                        DraggableSlotPalet(
                                            slot = circlesPalletSecond,
                                            dragAndDropState = dragAndDropState,
                                            onCircleClicked = { slot ->
                                                selectedItemText.value = idToTextMap[slot.id]
                                            }
                                        )
                                    }

                                    //-----------------Button next
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 16.dp),
                                        verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Button(
                                            onClick = {
                                                val allCircleIds =
                                                    (circlesPalletFirst + circlesPalletSecond).map { it.id }
                                                val unused = allCircleIds.filterNot { it in usedSlotIds }
                                                if (unused.isNotEmpty()) { //if there is unused circles
                                                    showAcceptDialog = true
                                                } else {
                                                    //Screenshot and save in view model
                                                    capturable?.capture?.invoke()
                                                    val agenda =
                                                        droppedState.mapValues { it.value.id }
                                                    viewModel.agendaMap.value = agenda
                                                    viewModel.setPage(viewModel.txtMemoryPage + 1)
                                                    navigator.push(RoomsScreens(pageNumber = 6))
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                            shape = RoundedCornerShape(30),
                                            modifier = Modifier
                                                .fillMaxWidth(0.7f)
                                                .defaultMinSize(minWidth = 100.dp)
                                                .width(250.dp)
                                                .height(50.dp)
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
                        }
                    }
                }
            })
        }

        RegisterBackHandler(this)
        {   viewModel.reset()
            navigator.popUntilRoot()
        }
    }

}
