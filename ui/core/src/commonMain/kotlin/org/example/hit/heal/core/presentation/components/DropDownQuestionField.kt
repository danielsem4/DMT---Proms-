package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Resources.Icon.dropDown
import org.example.hit.heal.core.presentation.Resources.Icon.dropUp
import org.example.hit.heal.core.presentation.Resources.String.firstQuestionHitberDropDownDropUpIcon
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class DropDownItem(val text: String)

@Composable
fun DropDownQuestionField(
    question: String?,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    val shouldFloatLabel = expanded || selectedText.isNotEmpty()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .height(85.dp)
            .clickable { expanded = !expanded }
            .padding(horizontal = paddingMd)
    ) {
        val dropdownWidth = maxWidth

        if (shouldFloatLabel) {
            if (question != null) {
                Text(
                    text = question,
                    color = if (expanded) primaryColor else Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = paddingSm, bottom = paddingSm),
                    style = MaterialTheme.typography.caption
                )
            }
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
                text = if (selectedText.isNotEmpty() || shouldFloatLabel.not()) selectedText.ifEmpty { question.orEmpty() } else "",
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
            modifier = Modifier.width(dropdownWidth),
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

@Preview
@Composable
fun PreviewDropDownQuestionField() {
    val question = "What is your favorite color?"
    val items = listOf(
        DropDownItem("Red"),
        DropDownItem("Green"),
        DropDownItem("Blue"),
        DropDownItem("Yellow")
    )

    DropDownQuestionField(
        question = question,
        dropDownItems = items,
        onItemClick = { selected ->
            println("Selected item: ${selected.text}")
        }
    )
}
