package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailTextField(email: String, onValueChange: (String) -> Unit) = RoundedTextField(
    value = email,
    onValueChange = onValueChange,
    label = { Text(stringResource(Resources.String.email)) },
    leadingIcon = Resources.Icon.email,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
)