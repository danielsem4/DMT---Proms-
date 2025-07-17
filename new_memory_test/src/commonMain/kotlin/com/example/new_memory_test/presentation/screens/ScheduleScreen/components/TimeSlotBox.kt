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
import kotlin.collections.plus

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
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable {
                droppedState.value = droppedState.value - slotId
            }
            .dropTarget(
                key = slotId,
                state = dragAndDropState,
                onDrop = { draggedItemState ->
                    droppedState.value = droppedState.value + (slotId to draggedItemState.data)
                    val id = draggedItemState.data.id
                    if (id !in usedCircleIds) usedCircleIds.add(id)
                }
            )
    ) {
        currentItem?.let {
            Image(
                painter = it.painter,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            DeleteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = {
                    droppedState.value = droppedState.value - slotId
                }
            )
        }
    }
}