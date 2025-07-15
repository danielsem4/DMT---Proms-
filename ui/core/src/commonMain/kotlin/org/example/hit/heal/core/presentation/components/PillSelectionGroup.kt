package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.primaryColor

enum class SelectionMode {
    SINGLE, MULTI
}

@Composable
fun PillSelectionGroup(
    options: List<String>,
    selectedValues: List<String>,
    onSelectionChanged: (List<String>) -> Unit,
    selectionMode: SelectionMode = SelectionMode.MULTI,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color =
                            if (selectionMode == SelectionMode.MULTI && isSelected) primaryColor
                            else if (selectionMode == SelectionMode.MULTI) Color(0xFFF2FDFB)
                            else Color.White,
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        val updated = selectedValues.toMutableList()
                        if (selectionMode == SelectionMode.MULTI) {
                            if (isSelected) updated.remove(option) else updated.add(option)
                        } else { // SINGLE
                            updated.clear()
                            updated.add(option)
                        }
                        onSelectionChanged(updated)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectionMode == SelectionMode.MULTI) {
                    // ✅ Multi: pill with checkbox
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color.White else Color.Transparent,
                                CircleShape
                            )
                            .border(
                                width = 2.dp,
                                color = if (isSelected) Color.White else primaryColor,
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

                    Text(
                        text = option,
                        fontSize = 18.sp,
                        color = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                } else {
                    // 🔷 Single: radio style
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(primaryColor)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = option,
                        fontSize = 18.sp,
                        color = if (isSelected) primaryColor else Color.Black,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}
