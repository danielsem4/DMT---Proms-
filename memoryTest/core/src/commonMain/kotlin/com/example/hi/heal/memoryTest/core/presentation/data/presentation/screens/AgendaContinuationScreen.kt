package com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.ViewModelMemoryTest.ViewModelMemoryTest
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.dialogs.CustomDialog
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.RoomScreen.RoomsScreens
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.book_icon
import dmt_proms.memorytest.core.generated.resources.coffee_icon
import dmt_proms.memorytest.core.generated.resources.delete_icon
import dmt_proms.memorytest.core.generated.resources.dumbbell_icon
import dmt_proms.memorytest.core.generated.resources.lecturer_icon
import dmt_proms.memorytest.core.generated.resources.move_icon
import dmt_proms.memorytest.core.generated.resources.stethoscope_icon

import org.jetbrains.compose.resources.painterResource
import kotlin.collections.plus


data class DraggableCircle(
    val id: String,
    val painter: Painter
)

class AgendaContinuationScreen(val pageNumber: Int ) : Screen {
    @Composable
    override fun Content() {

        val viewModel: ViewModelMemoryTest = viewModel()
        val days = listOf("ראשון", "שני", "שלישי", "רביעי", "חמישי")
        val hours = listOf("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")

        val day_1_hour_09 = "day_1_hour_09"
        val day_2_hour_09 = "day_2_hour_09"
        val day_3_hour_09 = "day_3_hour_09"
        val day_4_hour_09 = "day_4_hour_09"
        val day_5_hour_09 = "day_5_hour_09"

        val day_1_hour_10 = "day_1_hour_10"
        val day_2_hour_10 = "day_2_hour_10"
        val day_3_hour_10 = "day_3_hour_10"
        val day_4_hour_10 = "day_4_hour_10"
        val day_5_hour_10 = "day_5_hour_10"

        val day_1_hour_11 = "day_1_hour_11"
        val day_2_hour_11 = "day_2_hour_11"
        val day_3_hour_11 = "day_3_hour_11"
        val day_4_hour_11 = "day_4_hour_11"
        val day_5_hour_11 = "day_5_hour_11"

        val day_1_hour_12 = "day_1_hour_12"
        val day_2_hour_12 = "day_2_hour_12"
        val day_3_hour_12 = "day_3_hour_12"
        val day_4_hour_12 = "day_4_hour_12"
        val day_5_hour_12 = "day_5_hour_12"

        val day_1_hour_13 = "day_1_hour_13"
        val day_2_hour_13 = "day_2_hour_13"
        val day_3_hour_13 = "day_3_hour_13"
        val day_4_hour_13 = "day_4_hour_13"
        val day_5_hour_13 = "day_5_hour_13"

        val day_1_hour_14 = "day_1_hour_14"
        val day_2_hour_14 = "day_2_hour_14"
        val day_3_hour_14 = "day_3_hour_14"
        val day_4_hour_14 = "day_4_hour_14"
        val day_5_hour_14 = "day_5_hour_14"

        val day_1_hour_15 = "day_1_hour_15"
        val day_2_hour_15 = "day_2_hour_15"
        val day_3_hour_15 = "day_3_hour_15"
        val day_4_hour_15 = "day_4_hour_15"
        val day_5_hour_15 = "day_5_hour_15"

        val day_1_hour_16 = "day_1_hour_16"
        val day_2_hour_16 = "day_2_hour_16"
        val day_3_hour_16 = "day_3_hour_16"
        val day_4_hour_16 = "day_4_hour_16"
        val day_5_hour_16 = "day_5_hour_16"
        val droppedState = remember { mutableStateOf<Map<String, Pair<Color, Painter>>>(emptyMap()) }

        val circlesPalletFirst = listOf(
            DraggableCircle("book_circle", painterResource(Res.drawable.book_icon)),
            DraggableCircle("dumbbell_circle", painterResource(Res.drawable.dumbbell_icon)),
            DraggableCircle("move_circle", painterResource(Res.drawable.move_icon)),
        )
        val circlesPalletSecond = listOf(
            DraggableCircle( "lecturer_circle", painterResource(Res.drawable.lecturer_icon)),
            DraggableCircle("coffee_circle", painterResource(Res.drawable.coffee_icon)),
            DraggableCircle("stethoscope_circle", painterResource(Res.drawable.stethoscope_icon))
        )
        val idToTextMap = mapOf(
            "book_circle" to " לקחת את הנכדים לשעת סיפור בספרייה ביום רביעי בשעה  16:00" ,
            "dumbbell_circle" to " חוג התעלצות במתנס בימי ראשון בשעה 11:00 למשך שעה",
            "move_circle" to "לצאת להליכת בוקר פעמיים בשבועה למשך שעה לפחות",
            "lecturer_circle" to "לשמוע הרצאה ביום חמישי בשעה 15:00 במשך שעה ",
            "coffee_circle" to "מפגש חברתי בבית הקפה בימי שלישי בשעה 11:00 למשך שעתיים",
            "stethoscope_circle" to "תור לרופא עיניים לפני יום שלישי בין השעות 12:00-9:00 במשך שעה"
        )
        val dragAndDropState = rememberDragAndDropState<DraggableCircle>()
        val navigator = LocalNavigator.currentOrThrow
        val usedCircleIds = remember { mutableStateListOf<String>() }




        BaseTabletScreen(title = "בניית סדר יום המשח", page = pageNumber, totalPages = 6) {
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
                                            Column(modifier = Modifier.fillMaxSize()) {
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(vertical = 1.dp, horizontal = 3.dp)
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(droppedState.value[day_1_hour_09]?.first ?: Color.Gray)
                                                            .dropTarget(
                                                                key = day_1_hour_09,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->

                                                                    droppedState.value = droppedState.value + (day_1_hour_09 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon = droppedState.value[day_1_hour_09]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .size(40.dp)
                                                                    .align(Alignment.Center)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    val removedId = droppedState.value[day_1_hour_09]?.second
                                                                    droppedState.value = droppedState.value - day_1_hour_09
                                                                    val idToRemove = usedCircleIds.find { id ->
                                                                        val painter = (circlesPalletFirst + circlesPalletSecond).find { it.id == id }?.painter
                                                                        painter == removedId
                                                                    }
                                                                    idToRemove?.let { id ->
                                                                        val stillUsed = droppedState.value.any { it.value.second == removedId }
                                                                        if (!stillUsed) {
                                                                            usedCircleIds.remove(id)
                                                                        }
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_09
                                                            }

                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_09]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_09,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_09 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_09]?.second
                                                        currentIcon?.let {
                                                            Image(painter = it, contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_09
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()

                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_09
                                                            }

                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_09]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_09,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_09 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_09]?.second
                                                        currentIcon?.let {
                                                            Image(painter = it, contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_09
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_09
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_09]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_09,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_09 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_09]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_09
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_09
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_09]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_09,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_09 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_09]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_09
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_10
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_10]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_10,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_10 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_10]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_10
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_10
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_10]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_10,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_10 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_10]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_10
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_10
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_10]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_10,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_10 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_10]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_10
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_10
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_10]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_10,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_10 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_10]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_10
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_10
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_10]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_10,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_10 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_10]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_10
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_11
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_11]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_11,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_11 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_11]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_11
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_11
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_11]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_11,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_11 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_11]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_11
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_11
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_11]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_11,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_11 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )

                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_11]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_11
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_11
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_11]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_11,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_11 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_11]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_11
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_11
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_11]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_11,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_11 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_11]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_11
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_12
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_12]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_12,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_12 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_12]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_12
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_12
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_12]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_12,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_12 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_12]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_12
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_12
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_12]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_12,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_12 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_12]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_12
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_12
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_12]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_12,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_12 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_12]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_12
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_12
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_12]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_12,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_12 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_12]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_12
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_13
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_13]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_13,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_13 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_13]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_13
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_13
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_13]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_13,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_13 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_13]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_13
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_13
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_13]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_13,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_13 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_13]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_13
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_13
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_13]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_13,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_13 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_13]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_13
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_13
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_13]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_13,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_13 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_13]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_13
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_14
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_14]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_14,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_14 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_14]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_14
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_14
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_14]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_14,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_14 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_14]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_14
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_14
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_14]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_14,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_14 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_14]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_14
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_14
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_14]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_14,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_14 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_14]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_14
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_14
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_14]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_14,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_14 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_14]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_14
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_15
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_15]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_15,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_15 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_15]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_15
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_15
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_15]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_15,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_15 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_15]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_15
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_3_hour_15
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_15]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_15,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_3_hour_15 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_3_hour_15]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_3_hour_15
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_4_hour_15
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_15]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_15,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_4_hour_15 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_4_hour_15]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_4_hour_15
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_15
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_15]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_15,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_5_hour_15 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_5_hour_15]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_5_hour_15
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .fillMaxWidth()
                                                ) {

                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .height(70.dp)
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_1_hour_16
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_1_hour_16]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_1_hour_16,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_1_hour_16 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_1_hour_16]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_1_hour_16
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .height(70.dp)
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_2_hour_16
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_2_hour_16]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_2_hour_16,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (day_2_hour_16 to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[day_2_hour_16]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - day_2_hour_16
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .height(70.dp)
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {

                                                                droppedState.value = droppedState.value - day_3_hour_16
                                                             }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_3_hour_16]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_3_hour_16,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (key to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[key]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - key
                                                                }
                                                            )
                                                        }
                                                    }
                                                    Box(contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(70.dp)
                                                            .fillMaxHeight()
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - key
                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_4_hour_16]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_4_hour_16,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value =
                                                                        droppedState.value + (key to (primaryColor to draggedItemState.data.painter))
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[key]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - key
                                                                }
                                                            )

                                                        }
                                                    }
                                                    Box( contentAlignment = Alignment.Center,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .fillMaxHeight()
                                                            .height(70.dp)
                                                            .padding(
                                                                vertical = 1.dp,
                                                                horizontal = 3.dp
                                                            )
                                                            .clickable {
                                                                droppedState.value = droppedState.value - day_5_hour_16

                                                            }
                                                            .clip(RoundedCornerShape(30.dp))
                                                            .background(
                                                                droppedState.value[day_5_hour_16]?.first
                                                                    ?: Color.Gray
                                                            )
                                                            .dropTarget(
                                                                key = day_5_hour_16,
                                                                state = dragAndDropState,
                                                                onDrop = { draggedItemState ->
                                                                    droppedState.value = droppedState.value + (
                                                                            key to (primaryColor to draggedItemState.data.painter)
                                                                            )
                                                                    val id = draggedItemState.data.id
                                                                    if (id !in usedCircleIds) {
                                                                        usedCircleIds.add(id)
                                                                    }
                                                                }
                                                            )
                                                    ) {
                                                        val currentIcon =
                                                            droppedState.value[key]?.second
                                                        currentIcon?.let {
                                                            Image(
                                                                painter = it,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(40.dp)
                                                            )
                                                            DeleteIcon(
                                                                modifier = Modifier.align(Alignment.TopEnd),
                                                                onClick = {
                                                                    droppedState.value = droppedState.value - key
                                                                }
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
                                    text = "עליך להכניס למערכת השעות את הפגישות לפי ההוראות. לחיצה על הפגישה תציג את הזמן.",
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
                                            text = "המשך",
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
                                            Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                                        },
                                        title = "בניית סדר יום",
                                        description =  "יש למקם את כל הפעילויות ביומן לפני שממשיכים",
                                        buttons = listOf(
                                            "הבא" to {
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

@Composable
fun DeleteIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Res.drawable.delete_icon),
        contentDescription = "Delete",
        modifier = modifier
            .size(30.dp)
            .padding(10.dp)
            .clickable(onClick = onClick)
    )
}
@Composable
fun DraggableCirclesPalet(
    circles: List<DraggableCircle>,
    dragAndDropState: DragAndDropState<DraggableCircle>,
    onCircleClicked: (DraggableCircle) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        circles.forEach { circle ->
            DraggableItem(
                state = dragAndDropState,
                key = circle.id,
                data = circle
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            onCircleClicked(circle)
                        }
                ) {
                    Image(
                        painter = circle.painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
