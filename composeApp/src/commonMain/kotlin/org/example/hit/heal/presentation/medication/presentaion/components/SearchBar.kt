package org.example.hit.heal.presentation.medication.presentaion.components
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource


@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        shape = RoundedCornerShape(150.dp),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = Color.Black,
            cursorColor = primaryColor,
            unfocusedBorderColor = Color.Black
        ),
        placeholder = { Text(stringResource(Resources.String.searchHint)) },
        leadingIcon = {
           // Icon(
           //     imageVector =  Resources.Icon.clockIcon,
           //     contentDescription = "Search Icon",
           //     tint = Color.Black
            // )

        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onItemClicked()
            }
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotBlank(),
            ){
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    }
                ){
                   // Icon(
                   //     imageVector = Resources.Icon.searchIcon,
                   //     contentDescription = "Clear Icon",
                   //     tint = Color.Black
                   // )
                }
            }
        }
    )

}