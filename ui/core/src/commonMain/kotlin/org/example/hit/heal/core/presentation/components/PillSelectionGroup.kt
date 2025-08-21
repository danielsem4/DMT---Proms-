package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.GrayLighter
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.spacingXxl
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class SelectionMode {
    SINGLE, MULTIPLE
}

enum class OptionStyle {
    PLAIN, STYLED
}

@Composable
fun PillSelectionGroup(
    options: List<String>,
    selectedValues: List<String>,
    onSelectionChanged: (List<String>) -> Unit,
    selectionMode: SelectionMode = SelectionMode.MULTIPLE,
    style: OptionStyle = OptionStyle.PLAIN,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .then(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
    ) {
        options.forEach { option ->
            val isSelected = selectedValues.contains(option)
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingMd, vertical = paddingSm)
                    .clickable {
                        val updated = selectedValues.toMutableList()
                        if (selectionMode == SelectionMode.MULTIPLE) {
                            if (isSelected) updated.remove(option) else updated.add(option)
                        } else { // SINGLE
                            updated.clear()
                            updated.add(option)
                        }
                        onSelectionChanged(updated)
                    },
                colors = CardDefaults.cardColors(containerColor = if (isSelected) primaryColor else White),
                shape = RoundedCornerShape(if (style == OptionStyle.STYLED) spacingXxl else spacingSm),
                border = BorderStroke(1.dp, primaryColor),
                elevation = CardDefaults.cardElevation(elevationMd)
            ){
                Row(
                    modifier = Modifier
                    .fillMaxSize()
                        .padding(paddingMd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (selectionMode == SelectionMode.MULTIPLE) {
                        //  Multi: pill with square checkbox
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    if (isSelected) GrayLighter else Color.Transparent,
                                    RoundedCornerShape(4.dp)
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) GrayLighter else primaryColor,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = primaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = option,
                            fontSize = 18.sp,
                            color = if (isSelected) GrayLighter else Color.Black,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                    } else {
                        // Single: round styled like multi when selected
                        if (style == OptionStyle.STYLED) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color.White, CircleShape)
                                    .border(
                                        width = 2.dp,
                                        color = if (isSelected) GrayLighter else primaryColor,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = primaryColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        Text(
                            text = option,
                            fontSize = 18.sp,
                            color = if (isSelected) GrayLighter else Color.Black,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun Preview_Multi_Plain() {
    val selected = remember { mutableStateOf(listOf("Option 1")) }
    PillSelectionGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedValues = selected.value,
        onSelectionChanged = { selected.value = it },
        selectionMode = SelectionMode.MULTIPLE,
        style = OptionStyle.PLAIN
    )
}

@Preview()
@Composable
fun Preview_Multi_Styled() {
    val selected = remember { mutableStateOf(listOf("Option 1")) }
    PillSelectionGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedValues = selected.value,
        onSelectionChanged = { selected.value = it },
        selectionMode = SelectionMode.MULTIPLE,
        style = OptionStyle.STYLED
    )
}

@Preview()
@Composable
fun Preview_Single_Plain() {
    val selected = remember { mutableStateOf(listOf("Option 1")) }
    PillSelectionGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedValues = selected.value,
        onSelectionChanged = { selected.value = it },
        selectionMode = SelectionMode.SINGLE,
        style = OptionStyle.PLAIN
    )
}

@Preview()
@Composable
fun Preview_Single_Styled() {
    val selected = remember { mutableStateOf(listOf("Option 1")) }
    PillSelectionGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedValues = selected.value,
        onSelectionChanged = { selected.value = it },
        selectionMode = SelectionMode.SINGLE,
        style = OptionStyle.STYLED
    )
}