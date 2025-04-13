package com.example.hi.heal.memoryTest.core.presentation.data.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.DraggableItem
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.Item
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.book
import dmt_proms.memorytest.core.generated.resources.dress
import dmt_proms.memorytest.core.generated.resources.glasses
import dmt_proms.memorytest.core.generated.resources.keys
import dmt_proms.memorytest.core.generated.resources.phone
import dmt_proms.memorytest.core.generated.resources.wallet
import org.jetbrains.compose.resources.DrawableResource



data class DraggableImage(
    val id: Int,
    val resId: Int, // Use Int for resource id
    var isPlaced: Boolean = false,
    var position: Offset = Offset.Zero
)

@Composable
fun DraggableImage(
    imageList: List<DraggableImage>,
    imageRes: DrawableResource,
    onDrop: (Offset) -> Unit
) {
    var droppedItems by remember { mutableStateOf(mutableListOf<Item>()) }
    val items = listOf(
        Res.drawable.glasses, Res.drawable.book, Res.drawable.dress,
        Res.drawable.phone, Res.drawable.keys, Res.drawable.wallet
    )
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left: items list
        Box(
            modifier = Modifier
                .weight(0.5f)
                .padding(8.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items.size) { index ->
                    DraggableItem(
                        imageRes = items[index],
                        onDrop = { offset ->
                            droppedItems.add(
                                Item(id = index, resId = items[index], isPlaced = true, position = offset)
                            )
                        }
                    )
                }
            }
        }
    }
}

