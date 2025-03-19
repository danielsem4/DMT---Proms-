package org.example.hit.heal.presentaion.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.hit.heal.presentaion.primaryColor

@Composable
fun CustomOutlinedTextDataField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value, TextRange(value.length))) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    fun formatDateInput(input: String): TextFieldValue {
        val digits = input.filter { it.isDigit() }
        val formatted = StringBuilder()

        for (i in digits.indices) {
            formatted.append(digits[i])
            if ((i == 1 || i == 3) && i < digits.length - 1) {
                formatted.append("/")
            }
        }

        val newText = formatted.toString()
        val newCursorPosition = newText.length

        return TextFieldValue(newText, TextRange(newCursorPosition))
    }

    fun validateDate(date: String){
        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        try {
            val parts = date.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()

                val parsedDate = LocalDate(year, month, day)

                if (parsedDate < today) {
                    isError = true
                    errorMessage = "The date cannot be in the past"


                } else {
                    isError = false
                    errorMessage = ""
                }
            } else {
                throw IllegalArgumentException()
            }
        } catch (e: Exception) {
            isError = true
            errorMessage = "Invalid date format"
        }
        return
    }


    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val formattedValue = formatDateInput(newValue.text)
            if (formattedValue.text.length <= 10) {
                textFieldValue = formattedValue
                onValueChange(formattedValue.text)
            }
            if (formattedValue.text.length == 10) {
                validateDate(formattedValue.text)
            } else {
                isError = false
                errorMessage = ""
            }
        },
        readOnly = readOnly,
        label = { Text(label) },
        trailingIcon = trailingIcon,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color.Red else primaryColor,
            unfocusedBorderColor = if (isError) Color.Red else Color.Black,
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Black,
            disabledPlaceholderColor = Color.Black,
            disabledBorderColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = Color.Black,
            textColor = Color.Black,
            backgroundColor = Color.White,

            errorCursorColor =  Color.Red,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red,

            placeholderColor = Color.Black

        ),
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),



    )
}