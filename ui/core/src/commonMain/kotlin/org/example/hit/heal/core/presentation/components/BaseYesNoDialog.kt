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
import org.example.hit.heal.core.presentation.GrayLighter
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.Resources.Icon.email
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
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(White)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF66CC99),
                    modifier = Modifier.size(64.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = title,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF66CC99),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // “No” — grey gradient
                    RoundedGradientButton(
                        text = dismissButtonText,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(dismissButtonColor, GrayLighter)
                        ),
                        onClick = onDismissButtonClick,
                        modifier= Modifier.weight(1f)
                    )

                    // “Yes” — green gradient
                    RoundedGradientButton(
                        text = confirmButtonText,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(confirmButtonColor, GrayLighter)
                        ),
                        onClick = onConfirm,
                        modifier= Modifier.weight(1f)
                    )
                }
            }
        }
    }
}