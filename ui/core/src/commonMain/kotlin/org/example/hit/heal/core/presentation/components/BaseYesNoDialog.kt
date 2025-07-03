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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.no
import dmt_proms.ui.core.generated.resources.yes
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
import org.example.hit.heal.core.presentation.Resources.Icon.warningIcon
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.components.widgets.RoundedGradientButton
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseYesNoDialog(
    onDismissRequest: () -> Unit,
    title: String,
    icon: ImageVector? = null,
    message: String,
    confirmButtonText: String = stringResource(Res.string.yes),
    confirmButtonColor: Color = primaryColor,
    onConfirm: () -> Unit,
    dismissButtonText: String = stringResource(Res.string.no),
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
                if (icon != null) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(iconSizeXl)
                    )
                }

                Spacer(Modifier.height(spacingMd))

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

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacingMd)) {
                    // “No”
                    RoundedButton(
                        text = dismissButtonText,
                        onClick = onDismissButtonClick,
                        modifier = Modifier.weight(1f),
                        buttonColor = dismissButtonColor
                    )

                    // “Yes”
                    RoundedButton(
                        text = confirmButtonText,
                        modifier = Modifier.weight(1f),
                        onClick = onConfirm,
                        buttonColor = confirmButtonColor
                    )
                }
            }
        }
    }
}