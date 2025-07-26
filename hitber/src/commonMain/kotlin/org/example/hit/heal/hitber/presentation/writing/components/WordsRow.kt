package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.components.SlotState
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WordsRow(
    words: List<StringResource>,
    dragAndDropState: DragAndDropState<SlotState>,
) {
    BoxWithConstraints {
        val boxWidth = maxWidth / 8
        val boxHeight = maxHeight / 8

        Row(
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(spacingLg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            words.forEach { wordState ->
                val word = stringResource(wordState)
                DraggableItem(
                    state = dragAndDropState,
                    key = word,
                    data = SlotState(
                        id = word,
                        word = word,
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(boxWidth, boxHeight)
                            .background(primaryColor, RoundedCornerShape(50.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = word,
                            color = Color.White,
                            fontSize = EXTRA_MEDIUM,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
