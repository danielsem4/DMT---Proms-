package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Density
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.Resources.Icon.closeFridge
import org.example.hit.heal.core.presentation.Resources.Icon.openFridge
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberCloseFridge
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberItem
import org.example.hit.heal.core.presentation.Resources.String.sixthQuestionHitberOpenFridge
import org.example.hit.heal.hitber.presentation.understanding.SixthQuestionViewModel
import org.example.hit.heal.hitber.presentation.understanding.model.fridgeItems
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FridgeWithItemsBox(
    isFridgeOpen: Boolean,
    onFridgeToggle: () -> Unit,
    onFridgeSizeChanged: (Pair<Float, Float>) -> Unit,
    itemPositions: SnapshotStateList<Offset>,
    itemWidthPx: Float,
    itemHeightPx: Float,
    itemResourceId: DrawableResource?,
    density: Density,
    viewModel: SixthQuestionViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .clickable {
                // Toggle fridge open/close state on click and update user opened fridge
                onFridgeToggle()
                if (!viewModel.isFridgeOpened) {
                    viewModel.setFridgeOpened()
                }
            }
    ) {
        // Display different fridge images depending on open/close state
        Image(
            painter = if (isFridgeOpen) painterResource(openFridge)
            else painterResource(closeFridge),
            contentDescription = if (isFridgeOpen)
                stringResource(sixthQuestionHitberOpenFridge)
            else
                stringResource(sixthQuestionHitberCloseFridge),
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .onSizeChanged { size ->
                    if (isFridgeOpen) {
                        onFridgeSizeChanged(size.width.toFloat() to size.height.toFloat())
                    }
                },
            contentScale = ContentScale.FillHeight
        )

        // Show draggable items only when fridge is open
        if (isFridgeOpen) {
            fridgeItems.forEachIndexed { index, item ->
                val itemWidthDp = with(density) { itemWidthPx.toDp() }
                val itemHeightDp = with(density) { itemHeightPx.toDp() }
                val currentPosition = itemPositions[index]

                Box(
                    modifier = Modifier
                        .offset(
                            x = with(density) { currentPosition.x.toDp() },
                            y = with(density) { currentPosition.y.toDp() }
                        )
                        .size(itemWidthDp, itemHeightDp)
                        .pointerInput(Unit) {
                            // Handle drag gestures to update the position of fridge items
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                itemPositions[index] += dragAmount

                                // Notify if the dragged item is the selected one
                                if (item.image == itemResourceId && !viewModel.isItemMovedCorrectly) {
                                    viewModel.setItemMovedCorrectly()
                                }
                            }
                        }
                        .onGloballyPositioned { coordinates ->
                            viewModel.updateItemLastPosition(index, coordinates.positionInRoot())
                        }
                        .zIndex(1f)
                ) {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = stringResource(sixthQuestionHitberItem),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}
