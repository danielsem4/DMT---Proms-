package org.example.hit.heal.core.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.White

/**
 * Simple card with icon & text
 *
 */

@Composable
fun SimpleIconCard(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    cardColor: Color = White,
    icon: (@Composable () -> Unit)? = null,
    vertical: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(cardColor),
        elevation = CardDefaults.cardElevation(elevationMd)
    ) {
        val layoutMod = Modifier
            .padding(paddingMd)
            .fillMaxWidth()
            .wrapContentWidth()

        val content: @Composable () -> Unit = {
            icon?.invoke()
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (vertical) {
            Column(
                modifier = layoutMod,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        } else {
            Row(
                modifier = layoutMod,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}
