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
import androidx.compose.ui.unit.dp
import com.example.new_memory_test.presentation.screens.ScheduleScreen.data.DraggableCircle
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import org.example.hit.heal.core.presentation.Sizes.buttonHeightLg

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
        //circle that need to dragable
        circles.forEach { circle ->
            DraggableItem(
                state = dragAndDropState,
                key = circle.id,
                data = circle
            ) {
                Box(
                    modifier = Modifier
                        .size(buttonHeightLg)
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
