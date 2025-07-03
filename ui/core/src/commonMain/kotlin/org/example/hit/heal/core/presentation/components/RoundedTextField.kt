package org.example.hit.heal.core.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.LightWhite
import org.example.hit.heal.core.presentation.backgroundColor

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp)
        .background(
            LightWhite, RoundedCornerShape(33.dp)
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
                tint = backgroundColor
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(33.dp)
    )
}

