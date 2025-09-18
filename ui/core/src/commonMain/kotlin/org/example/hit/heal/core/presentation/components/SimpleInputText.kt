package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.example.hit.heal.core.presentation.GrayDarker
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusXl
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.primaryColor

/**
 * Material 3 text field with:
 * - Next/Done IME behavior
 * - Optional password visual transformation
 * - Optional focus chaining via FocusRequester
 */

@Composable
fun SimpleInputText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,

    // Keep your existing boolean to toggle password masking
    visualTransformation: Boolean = false,

    // New: IME and focus chaining parameters
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    focusRequester: FocusRequester? = null,
    nextFocusRequester: FocusRequester? = null,

    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = paddingMd),
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        modifier = if (focusRequester != null) {
            modifier.focusRequester(focusRequester)
        } else {
            modifier
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                // If you provided a next requester, move focus; otherwise, dismiss keyboard
                if (nextFocusRequester != null) {
                    nextFocusRequester.requestFocus()
                } else {
                    focusManager.clearFocus()
                }
            },
            onDone = {
                // Always dismiss on Done
                focusManager.clearFocus()
            }
        ),
        shape = RoundedCornerShape(radiusXl),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = primaryColor,
            unfocusedIndicatorColor = GrayDarker,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            disabledContainerColor = White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = primaryColor
        ),
        visualTransformation = if (visualTransformation) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}
