package com.example.new_memory_test.presentation.screens.ScheduleScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.new_memory_test.presentation.screens.ScheduleScreen.data.DraggableCircle
import org.example.hit.heal.core.presentation.primaryColor
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import org.example.hit.heal.core.presentation.Sizes.paddingXs
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.Sizes.widthTiny
import kotlin.collections.plus

//Boxes for schedules
@Composable
fun TimeSlotBox(
    slotId: String,
    droppedState: MutableState<Map<String, DraggableCircle>>,
    dragAndDropState: DragAndDropState<DraggableCircle>,
    usedCircleIds: SnapshotStateList<String>,
    modifier: Modifier = Modifier
) {
    val currentItem = droppedState.value[slotId]
    val backgroundColor = if (currentItem != null) primaryColor else Color.Gray

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(paddingXs)
            .clip(RoundedCornerShape(radiusMd2))
            .background(backgroundColor)
            .clickable {
                droppedState.value = droppedState.value - slotId //remove from dropped
            }
            .dropTarget(
                key = slotId,
                state = dragAndDropState,
                onDrop = { draggedItemState ->
                    droppedState.value = droppedState.value + (slotId to draggedItemState.data) //save new boxes
                    val id = draggedItemState.data.id
                    if (id !in usedCircleIds) usedCircleIds.add(id)// add circle that used (for check in ScheduleScreen -> button next)
                }
            )
    ) {
        currentItem?.let {
            Image( // Image of circle
                painter = it.painter,
                contentDescription = null,
                modifier = Modifier.size(widthTiny)
            )
            DeleteIcon( //Image of delete - small X
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(paddingXs),
                onClick = {
                    droppedState.value = droppedState.value - slotId
                }
            )
        }
    }
}