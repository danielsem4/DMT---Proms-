package com.example.new_memory_test.presentation.screens.ScheduleScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.dialogs.CustomDialog
import com.example.new_memory_test.presentation.screens.BaseTabletScreen
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import com.example.new_memory_test.presentation.screens.ScheduleScreen.components.DraggableCirclesPalet
import com.example.new_memory_test.presentation.screens.ScheduleScreen.components.TimeSlotBox
import com.example.new_memory_test.presentation.screens.ScheduleScreen.data.DraggableCircle
import com.example.new_memory_test.primaryColor
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import dmt_proms.new_memory_test.generated.resources.Res
import dmt_proms.new_memory_test.generated.resources.book_circle
import dmt_proms.new_memory_test.generated.resources.book_circle_text
import dmt_proms.new_memory_test.generated.resources.book_icon
import dmt_proms.new_memory_test.generated.resources.build_schedule
import dmt_proms.new_memory_test.generated.resources.build_schedule_continuation
import dmt_proms.new_memory_test.generated.resources.coffee_circle
import dmt_proms.new_memory_test.generated.resources.coffee_circle_text
import dmt_proms.new_memory_test.generated.resources.coffeee_icon
import dmt_proms.new_memory_test.generated.resources.continue_button
import dmt_proms.new_memory_test.generated.resources.day_monday
import dmt_proms.new_memory_test.generated.resources.day_sunday
import dmt_proms.new_memory_test.generated.resources.day_thursday
import dmt_proms.new_memory_test.generated.resources.day_tuesday
import dmt_proms.new_memory_test.generated.resources.day_wednesday
import dmt_proms.new_memory_test.generated.resources.dumbbell_circle
import dmt_proms.new_memory_test.generated.resources.dumbbell_circle_text
import dmt_proms.new_memory_test.generated.resources.dumbbell_icon
import dmt_proms.new_memory_test.generated.resources.hour_09_00
import dmt_proms.new_memory_test.generated.resources.hour_10_00
import dmt_proms.new_memory_test.generated.resources.hour_11_00
import dmt_proms.new_memory_test.generated.resources.hour_12_00
import dmt_proms.new_memory_test.generated.resources.hour_13_00
import dmt_proms.new_memory_test.generated.resources.hour_14_00
import dmt_proms.new_memory_test.generated.resources.hour_15_00
import dmt_proms.new_memory_test.generated.resources.hour_16_00
import dmt_proms.new_memory_test.generated.resources.instruction_text_agenda_continuation
import dmt_proms.new_memory_test.generated.resources.lecturer_circle
import dmt_proms.new_memory_test.generated.resources.lecturer_circle_text
import dmt_proms.new_memory_test.generated.resources.move_circle
import dmt_proms.new_memory_test.generated.resources.move_circle_text
import dmt_proms.new_memory_test.generated.resources.move_icon
import dmt_proms.new_memory_test.generated.resources.next
import dmt_proms.new_memory_test.generated.resources.place_activities
import dmt_proms.new_memory_test.generated.resources.stethoscope_circle
import dmt_proms.new_memory_test.generated.resources.stethoscope_circle_text
import dmt_proms.new_memory_test.generated.resources.stethoscope_icon
import dmt_proms.new_memory_test.generated.resources.teach_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ScheduleScreen(val pageNumber: Int ) : Screen {
    @Composable
    override fun Content() {

        val viewModel: ViewModelMemoryTest = viewModel()
        val days = listOf(
            stringResource(Res.string.day_sunday),
            stringResource(Res.string.day_monday),
            stringResource(Res.string.day_tuesday),
            stringResource(Res.string.day_wednesday),
            stringResource(Res.string.day_thursday)
        )

        val hours = listOf(
            stringResource(Res.string.hour_09_00),
            stringResource(Res.string.hour_10_00),
            stringResource(Res.string.hour_11_00),
            stringResource(Res.string.hour_12_00),
            stringResource(Res.string.hour_13_00),
            stringResource(Res.string.hour_14_00),
            stringResource(Res.string.hour_15_00),
            stringResource(Res.string.hour_16_00)
        )
        val circles = stringResource(Res.string.book_circle )


        val droppedState = remember {
            mutableStateOf<Map<String, DraggableCircle>>(emptyMap())
        }

        val circlesPalletFirst = listOf(
            DraggableCircle(
                stringResource(Res.string.book_circle),
                painterResource(Res.drawable.book_icon)
            ),
            DraggableCircle(
                stringResource(Res.string.dumbbell_circle),
                painterResource(Res.drawable.dumbbell_icon)),

            DraggableCircle(
                stringResource( Res.string.move_circle),
                painterResource(Res.drawable.move_icon))

        )
        val circlesPalletSecond = listOf(
            DraggableCircle(
                stringResource(Res.string.lecturer_circle),
                painterResource(Res.drawable.teach_icon)
            ),
            DraggableCircle(
                stringResource(Res.string.coffee_circle),
                painterResource(Res.drawable.coffeee_icon)
            ),
            DraggableCircle(
                stringResource(Res.string.stethoscope_circle),
                painterResource(Res.drawable.stethoscope_icon)
            )
        )
        val idToTextMap = mapOf(
            stringResource(Res.string.book_circle) to stringResource(Res.string.book_circle_text) ,
            stringResource(Res.string.dumbbell_circle) to stringResource(Res.string.dumbbell_circle_text),
            stringResource( Res.string.move_circle) to stringResource(Res.string.move_circle_text),
            stringResource(Res.string.lecturer_circle) to stringResource(Res.string.lecturer_circle_text),
            stringResource(Res.string.coffee_circle)  to stringResource(Res.string.coffee_circle_text),
            stringResource(Res.string.stethoscope_circle) to stringResource(Res.string.stethoscope_circle_text)
        )
        val dragAndDropState = rememberDragAndDropState<DraggableCircle>()
        val navigator = LocalNavigator.currentOrThrow
        val usedCircleIds = remember { mutableStateListOf<String>() }


        BaseTabletScreen(  "Text ", page = pageNumber, totalPages = 6) {
            DragAndDropContainer(state = dragAndDropState) {
                Column {
                    Row(
                        modifier = Modifier
                            .weight(0.07f)
                            .padding(horizontal = 5.dp)
                            .background(Color.Transparent),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(0.6f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .background(Color.White),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            days.reversed().forEach { day ->
                                Box(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .padding(5.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 36.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(0.4f))
                    }
                    Row(
                        modifier = Modifier
                            .weight(0.7f)
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(horizontal = 5.dp, vertical = 5.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                    .fillMaxSize()
                                    .padding(vertical = 15.dp)
                            ) {
                                Column {
                                    for (hour in listOf("09", "10", "11", "12", "13", "14", "15", "16")) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(70.dp),
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            for (day in 1..5) {
                                                TimeSlotBox(
                                                    slotId = "day_${day}_hour_$hour",
                                                    droppedState = droppedState,
                                                    dragAndDropState = dragAndDropState,
                                                    usedCircleIds = usedCircleIds,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxHeight()
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        Spacer(modifier = Modifier.size(7.dp))

                        // Часы сбоку
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
                                            fontSize = 32.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.3f)

                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Text",
                                fontSize = 25.sp,
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            val selectedItemText = remember { mutableStateOf<String?>(null) }
                            LaunchedEffect(dragAndDropState.draggedItem) {
                                val item = dragAndDropState.draggedItem?.data
                                selectedItemText.value = item?.let { idToTextMap[it.id] }
                            }
                            // Первый ряд
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                DraggableCirclesPalet(
                                    circles = circlesPalletFirst,
                                    dragAndDropState = dragAndDropState,
                                    onCircleClicked = { circle ->
                                        selectedItemText.value = idToTextMap[circle.id]
                                    }
                                )
                            }
                            // Второй ряд
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                DraggableCirclesPalet(
                                    circles = circlesPalletSecond,
                                    dragAndDropState = dragAndDropState,
                                    onCircleClicked = { circle ->
                                        selectedItemText.value = idToTextMap[circle.id]
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            selectedItemText.value?.let { text ->
                                Text(
                                    text = text,
                                    fontSize = 25.sp,
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            var showAcceptDialog by remember { mutableStateOf(false) }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 16.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = {
                                        val allCircleIds = (circlesPalletFirst + circlesPalletSecond).map { it.id }
                                        val unused = allCircleIds.filterNot { it in usedCircleIds }
                                        if (unused.isNotEmpty()) {
                                            showAcceptDialog = true
                                        } else {
                                            val agenda = droppedState.value.mapValues { it.value.id }
                                            viewModel.agendaMap.value = agenda
                                            viewModel.setPage(viewModel.txtMemoryPage + 1)
                                            navigator.push(RoomsScreens(pageNumber = 6))
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text ="המשך",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                            if (showAcceptDialog) {
                                CustomDialog(
                                    onDismiss = { showAcceptDialog = false },
                                    icon = {
                                        Icon(
                                            Icons.Default.ThumbUp,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    },
                                    title ="build_schedule",
                                    description = "build_schedule",
                                    buttons = listOf(
                                        stringResource(Res.string.next) to {
                                            showAcceptDialog = false
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


