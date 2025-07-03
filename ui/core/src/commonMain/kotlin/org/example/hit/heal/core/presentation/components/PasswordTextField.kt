package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordTextField(
    password: String, onValueChange: (String) -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    RoundedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text(stringResource(Resources.String.password)) },
        leadingIcon = Resources.Icon.lock,
        iconModifier = Modifier.size(24.dp),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Resources.Icon.visible else Resources.Icon.invisible,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
}