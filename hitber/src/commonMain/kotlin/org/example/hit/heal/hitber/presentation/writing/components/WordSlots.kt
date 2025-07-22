package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.compose.dnd.DragAndDropState
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.GenericSlotBox
import org.example.hit.heal.core.presentation.components.SlotState
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel

@Composable
fun WordSlots(
    slots: List<SlotState>,
    droppedState: Map<String, SlotState>,
    dragAndDropState: DragAndDropState<SlotState>,
    usedDraggableIds: SnapshotStateList<String>,
    eightQuestionViewModel: EightQuestionViewModel
) {

    Row(
        modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        slots.forEach { slot ->
            GenericSlotBox(
                slotId = slot.id,
                droppedState = droppedState,
                onUpdateDroppedState = { slotId, slotState ->
                    eightQuestionViewModel.updateDroppedState(slotId, slotState)
                },
                dragAndDropState = dragAndDropState,
                usedDraggableIds = usedDraggableIds,
                modifier = Modifier
                    .size(150.dp),
                borderColor = Color.White,
                activeBackgroundColor = backgroundColor
            )
        }
    }
}
