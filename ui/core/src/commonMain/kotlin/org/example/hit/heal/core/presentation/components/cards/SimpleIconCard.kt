package org.example.hit.heal.core.presentation.components.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.OffWhite
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.heightTiny
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Simple card with icon & title
 */

@Composable
fun SimpleIconCard(
    title: String,
    cardColor: Color = Color.White,
    icon: DrawableResource? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(spacingSm),
        shape = RoundedCornerShape(radiusLg),
        elevation = elevationMd,
        backgroundColor = cardColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(spacingMd)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(heightTiny)
                        .padding(end = spacingSm)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.body1
            )
        }
    }
}