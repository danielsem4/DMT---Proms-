package org.example.hit.heal.core.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Sizes.iconSizeXl
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingXl
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Resources.Icon.warningIcon
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A base dialog component that displays a confirmation message with "Yes" and "No" options.
 */

@Composable
fun BaseYesNoDialog(
    onDismissRequest: () -> Unit,
    title: String,
    icon: DrawableResource = warningIcon,
    message: String,
    confirmButtonText: String,
    confirmButtonColor: Color = primaryColor,
    onConfirm: () -> Unit,
    dismissButtonText: String,
    dismissButtonColor: Color = Red,
    onDismissButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(iconSizeXl)
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(10.dp))

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
                    Text(
                        text = title,
                        fontSize = EXTRA_MEDIUM,
                        textAlign = TextAlign.Center,
                        color = primaryColor,
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

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacingMd)
                    ) {
                        // “No” — grey gradient
                        RoundedButton(
                            text = dismissButtonText,
                            onClick = onDismissButtonClick,
                            modifier = Modifier.weight(1f),
                            buttonColor = dismissButtonColor
                        )

                        // “Yes” — green gradient
                        RoundedButton(
                            text = confirmButtonText,
                            onClick = onConfirm,
                            modifier = Modifier.weight(1f),
                            buttonColor = confirmButtonColor
                        )
                    }
                }
            }
        }
    }
}