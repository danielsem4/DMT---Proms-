package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.GrayLighter
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources.Icon.email
import org.example.hit.heal.core.presentation.Sizes.iconSizeXl
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.TextWhite
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.backgroundColor
import org.example.hit.heal.core.presentation.components.widgets.RoundedGradientButton
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource

/**
 *
 */

@Composable
fun BaseYesNoDialog(
    onDismissRequest: () -> Unit,
    title: String,
    icon: ImageVector = email,
    message: String,
    confirmButtonText: String,
    confirmButtonColor: Color = primaryColor,
    onConfirm: () -> Unit,
    dismissButtonText: String,
    dismissButtonColor: Color = Red,
    onDismissButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(radiusLg),
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingLg)
        ) {
            Column(
                modifier = Modifier
                    .background(White)
                    .padding(horizontal = paddingLg, vertical = paddingXl),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF66CC99),
                    modifier = Modifier.size(iconSizeXl)
                )

                Spacer(Modifier.height(spacingMd))

                Text(
                    text = title,
                    fontSize = EXTRA_MEDIUM,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF66CC99),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(spacingSm))

                Text(
                    text = message,
                    fontSize = EXTRA_REGULAR,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(spacingLg))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacingMd)) {
                    // “No” — grey gradient
                    RoundedGradientButton(
                        text = dismissButtonText,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(dismissButtonColor, dismissButtonColor)
                        ),
                        onClick = onDismissButtonClick,
                        modifier= Modifier.weight(1f)
                    )

                    // “Yes” — green gradient
                    RoundedGradientButton(
                        text = confirmButtonText,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(confirmButtonColor, confirmButtonColor)
                        ),
                        onClick = onConfirm,
                        modifier= Modifier.weight(1f)
                    )
                }
            }
        }
    }
}