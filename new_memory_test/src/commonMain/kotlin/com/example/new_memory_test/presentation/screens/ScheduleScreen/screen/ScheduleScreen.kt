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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest

import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsScreens
import com.example.new_memory_test.presentation.screens.ScheduleScreen.components.DraggableCirclesPalet
import com.example.new_memory_test.presentation.screens.ScheduleScreen.components.TimeSlotBox
import com.example.new_memory_test.presentation.screens.ScheduleScreen.data.DraggableCircle
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState
import core.utils.CapturableWrapper
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import core.utils.platformCapturable
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.elevationSm
import org.example.hit.heal.core.presentation.Sizes.heightLg
import org.example.hit.heal.core.presentation.Sizes.heightMd
import org.example.hit.heal.core.presentation.Sizes.heightXl
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.paddingXs
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.Sizes.spacing8Xl
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.widthXl
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.CustomDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class ScheduleScreen(val pageNumber: Int ) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()
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

        val dragAndDropState = rememberDragAndDropState<DraggableCircle>()
        val droppedState = remember {
            mutableStateOf<Map<String, DraggableCircle>>(emptyMap())
        }
        val circlesPalletFirst = listOf(
            DraggableCircle(
                stringResource(Resources.String.book_circle),
                painterResource(Resources.Icon.bookIcon)
            ),
            DraggableCircle(
                stringResource(Resources.String.dumbbell_circle),
                painterResource(Resources.Icon.dumblertIcon)),

            DraggableCircle(
                stringResource(Resources.String.move_circle),
                painterResource(Resources.Icon.moveIcon))

        )
        val circlesPalletSecond = listOf(
            DraggableCircle(
                stringResource(Resources.String.lecturer_circle),
                painterResource(Resources.Icon.teachIcon)
            ),
            DraggableCircle(
                stringResource(Resources.String.coffee_circle),
                painterResource(Resources.Icon.coffeIcon)
            ),
            DraggableCircle(
                stringResource(Resources.String.stethoscope_circle),
                painterResource(Resources.Icon.stethoscopeImage)
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
        val usedCircleIds = remember { mutableStateListOf<String>() }
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
                icon = Resources.Icon.warningIcon,
                title = stringResource(Resources.String.build_schedule),
                text = stringResource(Resources.String.please_place_all_activities_memory),
                onDismiss = { showAcceptDialog = false },
                buttons = listOf(
                    stringResource(Resources.String.next) to { showAcceptDialog = false }
                )
            )
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        BaseScreen( stringResource(Resources.String.build_schedule),  topRightText = "$pageNumber/6",  config = ScreenConfig.TabletConfig, content = {
            DragAndDropContainer(state = dragAndDropState) {
                capturable = platformCapturable(
                    //Save Screen like Image
                    onCaptured = { imageBitmap ->
                        val timestamp = getCurrentFormattedDateTime()
                        viewModel.imageUrl.value = viewModel.imageUrl.value.plus(imageBitmap)
                        viewModel.timeUrl.value = timestamp
                        viewModel.pageNumForUrl.value = pageNumber
                    }
                )
                {

                    Column {

                        //-------------------Days in the Top
                        Row(
                            modifier = Modifier
                                .weight(0.08f)
                                .padding(horizontal = paddingXs)
                                .background(Color.Transparent),
                            horizontalArrangement = Arrangement.spacedBy(paddingSm)
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .clip(RoundedCornerShape(paddingSm))
                                    .border(elevationSm, Color.Black, RoundedCornerShape(paddingSm))
                                    .background(Color.White),
                                horizontalArrangement = Arrangement.spacedBy(paddingXs)
                            ) {

                                days.reversed().forEach { day ->
                                    Box(
                                        modifier = Modifier
                                            .weight(0.6f)
                                            .padding(paddingXs)
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(radiusLg))
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
                                    .padding(horizontal = paddingXs, vertical = paddingXs)
                                    .clip(RoundedCornerShape(radiusMd2))
                                    .background(Color.Transparent)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(radiusMd))
                                        .border(
                                            elevationSm,
                                            Color.Black,
                                            RoundedCornerShape(radiusMd)
                                        )
                                        .fillMaxSize()
                                        .padding(vertical = paddingMd)
                                ) {
                                    Column {
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
                                                    .height(70.dp),
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    elevationSm
                                                )
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
                            Spacer(modifier = Modifier.size(spacingSm))

                            //--------------------- Instructions of exam
                            Column(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .fillMaxHeight()
                                    .background(Color.White, RoundedCornerShape(radiusMd))
                                    .border(elevationSm, Color.Black, RoundedCornerShape(radiusMd))
                                    .padding(horizontal = paddingXs, vertical = paddingXs)
                            ) {
                                hours.forEach { hour ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(heightLg)
                                            .padding(vertical = paddingMd)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(
                                                    vertical = elevationSm,
                                                    horizontal = elevationMd
                                                )
                                                .fillMaxHeight()
                                                .clip(RoundedCornerShape(radiusMd))
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
                                Spacer(modifier = Modifier.size(spacingSm))
                            }
                            //
                            Column(
                                modifier = Modifier
                                    .weight(0.3f)

                                    .padding(spacingSm)
                            ) {

                                Box(
                                    modifier = Modifier

                                        .background(Color.White, RoundedCornerShape(radiusMd))
                                        .border(
                                            elevationSm,
                                            Color.Black,
                                            RoundedCornerShape(radiusMd)
                                        )
                                        .padding(vertical = spacingSm, horizontal = spacingSm)
                                ) {

                                    Text(
                                        text = stringResource(Resources.String.place_instructions_in_calendar_memory),
                                        fontSize = EXTRA_MEDIUM_LARGE,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryColor,
                                        modifier = Modifier.padding(bottom = spacingMd)
                                    )
                                }

                                //-----------------Instructions only for item
                                Spacer(modifier = Modifier.height(spacingLg))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (selectedItemText.value.isNullOrBlank()) Color.Transparent else Color.White,
                                            RoundedCornerShape(radiusMd)
                                        )
                                        .border(
                                            1.dp,
                                            if (selectedItemText.value.isNullOrBlank()) Color.Transparent else Color.Black,
                                            RoundedCornerShape(radiusMd)
                                        )
                                        .padding(vertical = paddingSm, horizontal = paddingSm)
                                        .fillMaxWidth()
                                        .height(heightXl)///
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
                                        .padding(bottom = paddingSm, top = paddingSm),
                                    horizontalArrangement = Arrangement.spacedBy(spacingLg)
                                ) {
                                    DraggableCirclesPalet(
                                        circles = circlesPalletFirst,
                                        dragAndDropState = dragAndDropState,
                                        onCircleClicked = { circle ->
                                            selectedItemText.value = idToTextMap[circle.id]
                                        }
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth()
                                        .padding(bottom = paddingSm),
                                    horizontalArrangement = Arrangement.spacedBy(spacingLg)
                                ) {
                                    DraggableCirclesPalet(
                                        circles = circlesPalletSecond,
                                        dragAndDropState = dragAndDropState,
                                        onCircleClicked = { circle ->
                                            selectedItemText.value = idToTextMap[circle.id]
                                        }
                                    )
                                }

                                //-----------------Button next
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = paddingMd),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Button(
                                        onClick = {
                                            val allCircleIds =
                                                (circlesPalletFirst + circlesPalletSecond).map { it.id }
                                            val unused =
                                                allCircleIds.filterNot { it in usedCircleIds }
                                            if (unused.isNotEmpty()) { //if there is unused circles
                                                showAcceptDialog = true
                                            } else {
                                                //Screenshot and save in view model
                                                capturable?.capture?.invoke()
                                                val agenda =
                                                    droppedState.value.mapValues { it.value.id }
                                                viewModel.agendaMap.value = agenda
                                                viewModel.setPage(viewModel.txtMemoryPage + 1)
                                                navigator.push(RoomsScreens(pageNumber = 6))
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                        shape = RoundedCornerShape(30),
                                        modifier = Modifier
                                            .fillMaxWidth(0.7f)
                                            .defaultMinSize(minWidth = spacing8Xl)
                                            .width(widthXl)
                                            .height(heightMd)
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
        },)
            RegisterBackHandler(this)
            {
                navigator?.popUntilRoot()
            }

        }

    }
}


