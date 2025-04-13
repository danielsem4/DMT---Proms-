package org.example.hit.heal.presentaion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.presentaion.primaryColor


@OptIn(ExperimentalMaterialApi::class)
@Composable
 fun CustomDropdownMenu(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },

        modifier = modifier
            .background(color = Color.White, RoundedCornerShape(10.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),

    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                backgroundColor = Color.White,
                textColor = Color.Black
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp)

                    ){
                        Text(text = option, color = Color.Black)
                    }

                }
            }
        }
    }
}