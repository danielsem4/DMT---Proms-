package org.example.hit.heal.core.presentation.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.primaryColor

data class SlotState(
    val id: String,
    var word: String? = null,
    val painter: Painter? = null
)

@Composable
fun GenericSlotBox(
    slotId: String,
    droppedState: Map<String, SlotState>,
    onUpdateDroppedState: (String, SlotState?) -> Unit,
    dragAndDropState: DragAndDropState<SlotState>,
    usedDraggableIds: SnapshotStateList<String>,
    modifier: Modifier = Modifier,
    borderColor: Color? = null,
    defaultBackgroundColor: Color = Color.Gray,
    activeBackgroundColor: Color = primaryColor,
) {
    val currentItem = droppedState[slotId]
    val backgroundColor = if (currentItem != null) activeBackgroundColor else defaultBackgroundColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))

            .border(
                width = 2.dp,
                color = borderColor?:backgroundColor,
            )
            .clickable {
                onUpdateDroppedState(slotId, null)
            }
            .dropTarget(
                key = slotId,
                state = dragAndDropState,
                onDrop = { draggedItemState ->
                    onUpdateDroppedState(slotId, draggedItemState.data)
                    val id = draggedItemState.data.id
                    if (id !in usedDraggableIds) usedDraggableIds.add(id)
                }
            )
    ) {
        currentItem?.let { item ->
            item.painter?.let { painter ->
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(5.dp)
                )
            }
            item.word?.let { word ->
                Text(
                    text = word,
                    color = Color.Black,
                    fontSize = EXTRA_MEDIUM,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            DeleteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = {
                    onUpdateDroppedState(slotId, null)
                }
            )
        }
    }
}
