package com.example.new_memory_test.presentation.screens.RoomScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.new_memory_test.presentation.screens.RoomScreen.components.enum_room.Room
import com.example.new_memory_test.presentation.screens.RoomScreen.data.DataItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun DraggableItem(
    id: Int,
    imageRes: DrawableResource,
    onDrop: (Int, Offset) -> Unit,
    selectedRoom: Room,
    placedItems: List<DataItem>,
    isOnRoom: Boolean = false,
    roomPosition: Offset = Offset.Zero
) {
    var offset by remember { mutableStateOf(Offset.Zero) }// drag in position 0
    var dragging by remember { mutableStateOf(false) }
    var itemPosition by remember { mutableStateOf(Offset.Zero) }

    //If the item is not on the room, but already placed, do not place it again
    if (!isOnRoom && placedItems.any { it.id == id }) return


    //if item in room - it need a position
    if (isOnRoom) {
        val placedItem = placedItems.find { it.id == id }
        if (placedItem != null) {
            offset = placedItem.position
        }
    }

    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates -> //box has a logic of dragging
                itemPosition = coordinates.localToWindow(Offset.Zero)
            }
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .size(80.dp)
            .pointerInput(Unit) {
                detectDragGestures( //understand what is drag (and that i am dragging )
                    onDragStart = { dragging = true },
                    onDragEnd = {
                        dragging = false
                        //Change a "location of item
                        val globalOffset = Offset(
                            itemPosition.x + offset.x,
                            itemPosition.y + offset.y
                        )
                        onDrop(id, globalOffset)
                    },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        offset += dragAmount
                    }
                )
            }
            .zIndex(if (isOnRoom) 2f else 1f) // Need that  sometimies items will be invisible (in changing room  - for this 2f)
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Transparent)
        )
    }
}