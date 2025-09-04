package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A highly customizable button component that supports optional icons, colors, and shapes.
 */

@Composable
fun GenericButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    fontSize: TextUnit = LARGE,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the icon if provided
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = text)
            }
            Text(
                text = text,
                fontSize = fontSize,

            )
        }
    }
}

@Preview
@Composable
fun PreviewGenericButton() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GenericButton(
            onClick = { /* Handle click */ },
            text = "Click Me"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        GenericButton(
            onClick = { /* Handle click */ },
            text = "Send Message",
            icon = Icons.Default.Send,
            backgroundColor = Color.Black,
            contentColor = Color.Yellow,
            shape = RoundedCornerShape(50)
        )
        Spacer(modifier = Modifier.height(16.dp))

        GenericButton(
            onClick = { /* Handle click */ },
            text = "Start",
            icon = Icons.Default.PlayArrow,
            backgroundColor = Color.Green.copy(alpha = 0.8f),
            contentColor = Color.White
        )
    }
}