package org.example.hit.heal.hitber.presentation.writing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.close_icon
import org.example.hit.heal.core.presentation.Resources.String.eighthQuestionHitberCloseIcon
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.model.WordSlotState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WordSlots(
    slots: List<WordSlotState>,
    eightQuestionViewModel: EightQuestionViewModel,
    density: Density
) {
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { newSize -> screenSize = newSize }.zIndex(-1f)
    ) {
        slots.forEachIndexed { _, slot ->
            WordSlot(
                slot,
                screenSize = screenSize,
                eightQuestionViewModel = eightQuestionViewModel,
                density = density
            )
        }
    }
}

@Composable
fun WordSlot(
    slot: WordSlotState,
    screenSize: IntSize,
    eightQuestionViewModel: EightQuestionViewModel, density: Density
) {
    val activeSlotIndex by eightQuestionViewModel.activeSlotIndex.collectAsState()
    val widthPx = (screenSize.width * 0.1f)
    val heightPx = (screenSize.width * 0.1f)
    LaunchedEffect(activeSlotIndex) {
        eightQuestionViewModel.updateSlotColor(activeSlotIndex ?: -1)
    }

    Box(
        modifier = Modifier.offset {
            IntOffset(
                (slot.initialOffset.x * screenSize.width).toInt(),
                (slot.initialOffset.y * screenSize.height).toInt()
            )
        }
            .width(with(density) { widthPx.toDp() })
            .height(with(density) { heightPx.toDp() })
            .background(slot.color, shape = RoundedCornerShape(10.dp))
            .border(color = Color.White, width = 5.dp)
            .zIndex(-1f)
    ) {
        slot.word?.let {
            Image(painter = painterResource(Res.drawable.close_icon),
                contentDescription = stringResource(eighthQuestionHitberCloseIcon),
                modifier = Modifier.size(20.dp).align(Alignment.TopStart).padding(5.dp)
                    .clickable {
                        eightQuestionViewModel.resetSlot(slot.id)
                    })
            Text(
                text = it,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}