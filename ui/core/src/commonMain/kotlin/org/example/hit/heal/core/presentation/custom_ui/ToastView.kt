package org.example.hit.heal.core.presentation.custom_ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.MEDIUM
import org.example.hit.heal.core.presentation.Resources.Icon.checkIcon
import org.example.hit.heal.core.presentation.Resources.Icon.warningIcon
import org.example.hit.heal.core.presentation.Resources.Icon.xIcon
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.ToastType
import org.jetbrains.compose.resources.painterResource

/**
 *
 */

@Composable
fun ToastView(
    message: String,
    type: ToastType = ToastType.Normal,
    backgroundColor: Color? = null,
) {
    val resolvedColor = backgroundColor ?: when (type) {
        ToastType.Normal -> Color.DarkGray
        ToastType.Success -> Color(0xFF4CAF50)
        ToastType.Info -> Color(0xFF2196F3)
        ToastType.Warning -> Color(0xFFFFC107)
        ToastType.Error -> Color(0xFFF44336)
    }

    val resolvedIcon = when (type) {
        ToastType.Success -> checkIcon
        ToastType.Info -> warningIcon
        ToastType.Warning -> warningIcon
        ToastType.Error -> xIcon
        else -> checkIcon
    }

    Surface(
        shape = RoundedCornerShape(radiusLg),
        color = resolvedColor,
        modifier = Modifier
            .padding(paddingMd)
            .wrapContentHeight()
            .defaultMinSize(minWidth = 120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                painter = painterResource(resolvedIcon),
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .size(iconSizeLg)
                    .padding(paddingSm)
            )

            Text(
                text = message,
                color = White,
                fontSize = MEDIUM,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}