package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.example.hit.heal.core.presentation.GrayDarker
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusXl
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.primaryColor

/**
 * A simple input text field with rounded corners, label, and optional leading and trailing icons.
 */

@Composable
fun SimpleInputText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: Boolean = false,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = paddingMd),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(radiusXl),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = White,
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = GrayDarker,

        ),
        visualTransformation = if (visualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
    )
}