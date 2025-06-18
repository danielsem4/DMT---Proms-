package org.example.hit.heal.hitber.presentation.naming.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberWhatInThePic
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.hitber.presentation.naming.FourthQuestionViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun NamingTextFields(
    answer1: String,
    answer2: String,
    viewModel: FourthQuestionViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
    ) {
        TextField(
            value = answer1,
            onValueChange = { viewModel.onAnswer1Changed(it) },
            label = {
                Text(
                    stringResource(fourthQuestionHitberWhatInThePic),
                    color = Color.Black
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor
            ),
            shape = RoundedCornerShape(0.dp)
        )

        TextField(
            value = answer2,
            onValueChange = { viewModel.onAnswer2Changed(it) },
            label = {
                Text(
                    stringResource(fourthQuestionHitberWhatInThePic),
                    color = Color.Black
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = primaryColor
            ),
            shape = RoundedCornerShape(0.dp)
        )
    }
}
