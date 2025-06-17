package org.example.hit.heal.hitber.presentation.timeAndPlace.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.drop_down
import dmt_proms.hitber.generated.resources.drop_up
import dmt_proms.hitber.generated.resources.first_question_hitbear_Drop_down_drop_up_Icon
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class DropDownItem(val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeAndPlaceQuestion(
    question: String,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(  modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(16.dp)){
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,focusedLabelColor = primaryColor),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(question) },
            trailingIcon = {
                Image(
                    painter = painterResource(
                        if (expanded) Res.drawable.drop_up else Res.drawable.drop_down
                    ),
                    contentDescription = stringResource(Res.string.first_question_hitbear_Drop_down_drop_up_Icon),
                    modifier = Modifier.size(20.dp)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = item.text
                        onItemClick(item)
                        expanded = false
                    }
                ) {
                    Text(text = item.text)
                }
            }
        }}
    }
}

