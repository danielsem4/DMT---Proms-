package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Resources.Icon.dropDown
import org.example.hit.heal.core.presentation.Resources.Icon.dropUp
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberDropDownDropUpIcon
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.DpOffset
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm

data class DropDownItem(val text: String)

@Composable
fun DropDownQuestionField(
    question: String,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    val shouldFloatLabel = expanded || selectedText.isNotEmpty()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .height(85.dp)
            .clickable { expanded = !expanded }
            .padding(horizontal = paddingMd)
    ) {
        if (shouldFloatLabel) {
            Text(
                text = question,
                color = if (expanded) primaryColor else Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding( start = paddingSm, bottom = paddingSm),
                style = MaterialTheme.typography.caption
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(1.dp, if (expanded) primaryColor else Color.Gray, RoundedCornerShape(8.dp))
                .align(Alignment.Center)
                .padding(horizontal = paddingMd)
        ) {
            Text(
                text = if (selectedText.isNotEmpty() || shouldFloatLabel.not()) selectedText.ifEmpty { question } else "",
                color = if (selectedText.isNotEmpty() || shouldFloatLabel) Color.Black else Color.Gray,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(if (expanded) dropUp else dropDown),
                contentDescription = stringResource(firstQuestionHitberDropDownDropUpIcon),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
            offset = DpOffset(x = 0.dp, y = (-40).dp)
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedText = item.text
                    onItemClick(item)
                    expanded = false
                }) {
                    Text(item.text)
                }
            }
        }
    }
}

