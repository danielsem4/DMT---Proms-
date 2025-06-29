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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.no
import dmt_proms.ui.core.generated.resources.yes
import org.example.hit.heal.core.presentation.Red
import org.example.hit.heal.core.presentation.White
import org.example.hit.heal.core.presentation.primaryColor
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
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = title,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    color = primaryColor,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = message,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
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