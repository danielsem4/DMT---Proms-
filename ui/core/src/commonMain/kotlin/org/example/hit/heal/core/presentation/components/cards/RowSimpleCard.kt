package org.example.hit.heal.core.presentation.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.Sizes.heightLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm

/**
 * Simple Card oriented in row
 * clickable
 */

@Composable
fun SimpleRowCard(
    onClick: () -> Unit,
    leading: Painter,
    title: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    // Layout & tokens
    horizontalPadding: androidx.compose.ui.unit.Dp = paddingMd,
    textPadding: androidx.compose.ui.unit.Dp = paddingSm,
    imageSize: androidx.compose.ui.unit.Dp = 70.dp,
    // Colors & style
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = TextStyle(fontSize = MEDIUM),
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(20),
    contentScale: ContentScale = ContentScale.Fit,
) {
    Card(
        onClick = onClick,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = leading,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(imageSize)
                    .padding(vertical = 3.dp),
                contentScale = contentScale
            )
            Text(
                text = title,
                style = textStyle,
                modifier = Modifier.padding(horizontal = textPadding)
            )
        }
    }
}