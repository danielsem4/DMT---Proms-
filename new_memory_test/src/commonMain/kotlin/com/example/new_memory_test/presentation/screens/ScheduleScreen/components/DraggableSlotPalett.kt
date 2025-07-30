package com.example.new_memory_test.presentation.screens.ScheduleScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import org.example.hit.heal.core.presentation.Sizes.buttonHeightLg
import org.example.hit.heal.core.presentation.components.SlotState

@Composable
fun DraggableSlotPalet(
    slot: List<SlotState>,
    dragAndDropState: DragAndDropState<SlotState>,
    onCircleClicked: (SlotState) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        slot.forEach { slot ->
            DraggableItem(
                state = dragAndDropState,
                key = slot.id,
                data = slot
            ) {
                Box(
                    modifier = Modifier
                        .size(buttonHeightLg)
                        .clickable { onCircleClicked(slot) }
                ) {
                    slot.painter?.let { painter ->
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}