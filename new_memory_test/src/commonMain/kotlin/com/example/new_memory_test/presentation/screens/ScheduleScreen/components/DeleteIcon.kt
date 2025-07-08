package com.example.new_memory_test.presentation.screens.ScheduleScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmt_proms.new_memory_test.generated.resources.Res
import dmt_proms.new_memory_test.generated.resources.delete_icon
import org.example.hit.heal.core.presentation.Resources
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Res.drawable.delete_icon),
        contentDescription = stringResource(Resources.String.delete),
        modifier = modifier
            .size(30.dp)
            .padding(10.dp)
            .clickable(onClick = onClick)
    )
}
