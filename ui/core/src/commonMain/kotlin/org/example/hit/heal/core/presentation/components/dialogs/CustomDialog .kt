package org.example.hit.heal.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.FontSize.EXTRA_LARGE
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogIcon
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberUnderstand
import org.example.hit.heal.core.presentation.Sizes.elevationLg
import org.example.hit.heal.core.presentation.Sizes.iconSizeXl
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.radiusMd
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun CustomDialog(
    icon: DrawableResource,
    title: String,
    text: String,
    description: String = "",
    onSubmit: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    buttons: List<Pair<String, () -> Unit>> = emptyList()
){
    Dialog(onDismissRequest = onDismiss) {
        Dialog(onDismissRequest =  onDismiss) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = stringResource(secondQuestionHitberDialogIcon),
                    modifier = Modifier
                        .size(iconSizeXl)
                        .align(Alignment.TopCenter)
                        .zIndex(1f)
                )
                Card(
                    shape = RoundedCornerShape(radiusLg),
                    elevation = elevationLg,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = paddingLg)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(paddingMd)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            fontSize = EXTRA_LARGE,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(paddingMd))
                        Text(
                            text = text,
                            fontSize = LARGE,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 50.sp
                        )
                        Spacer(modifier = Modifier.height(paddingLg))
                        if (buttons.isEmpty()) {
                            Button(
                                onClick = onDismiss,
                                shape = RoundedCornerShape(radiusMd),
                                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                            ) {
                                Text(stringResource(secondQuestionHitberUnderstand), color = Color.White, fontSize = EXTRA_MEDIUM)
                            }
                        } else {
                            buttons.forEach { (label, action) ->
                                Button(
                                    onClick = action,
                                    shape = RoundedCornerShape(radiusMd),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(text = label, color = Color.White, fontSize = EXTRA_MEDIUM)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
