package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Resources.Icon.deleteIcon),
        contentDescription = stringResource(Resources.String.delete),
        modifier = modifier
            .size(30.dp)
            .padding(10.dp)
            .clickable(onClick = onClick)
    )
}
