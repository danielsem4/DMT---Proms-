package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun Loading() {

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Resources.String.loading),
            fontSize = FontSize.EXTRA_LARGE,
            color = primaryColor
        )
        Spacer(modifier = Modifier.width(spacingMd))
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            color = primaryColor,
            modifier = Modifier.size(32.dp)
        )
    }
}