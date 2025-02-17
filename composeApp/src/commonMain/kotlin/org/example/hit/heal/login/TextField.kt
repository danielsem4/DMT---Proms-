package org.example.hit.heal.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dmt_proms.composeapp.generated.resources.Res
import org.example.hit.heal.bgGreen
import org.example.hit.heal.lightWhite
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp)
        .background(
            lightWhite, RoundedCornerShape(33.dp)
        ),
    label: @Composable (() -> Unit)? = null,
    leadingIcon: DrawableResource,
    iconModifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    trailingIcon: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = null,
                modifier = iconModifier,
                tint = bgGreen
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(33.dp)
    )
}

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp)
        .background(
            lightWhite, RoundedCornerShape(33.dp)
        ),
    label: @Composable (() -> Unit)? = null,
    leadingIcon: ImageVector,
    iconModifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    trailingIcon: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = iconModifier,
                tint = bgGreen
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(33.dp)
    )
}
@Composable
fun PasswordTextField(
    password: String, onValueChange: (String) -> Unit
) {

    var passwordVisible by remember { mutableStateOf(false) }

    RoundedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Password") },
        leadingIcon = Res.drawable.lock,
        iconModifier = Modifier.size(24.dp),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(if (passwordVisible) Res.drawable.eye else Res.drawable.closed_eye),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = bgGreen
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
}
@Composable
fun EmailTextField(email: String, onValueChange: (String) -> Unit) = RoundedTextField(
    value = email,
    onValueChange = onValueChange,
    label = { Text("Email") },
    leadingIcon = Icons.Default.Email,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
)