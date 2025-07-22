package com.example.new_memory_test.presentation.components.dialogs

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.FontSize.REGULAR
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.Sizes.radiusXl
import org.example.hit.heal.core.presentation.Sizes.spacing3Xl
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.spacingXl

import org.example.hit.heal.core.presentation.Sizes.widthSm
import org.example.hit.heal.core.presentation.primaryColor

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    icon: @Composable () -> Unit,
    title: String,
    description: String,
    buttons: List<Pair<String, () -> Unit>> // Pair of button text and action
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(horizontal = spacingXl )
                .wrapContentHeight()
        ) {
            // Card Body
            Box(
                modifier = Modifier
                    .padding(heightSm)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(radiusXl))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        top = spacing3Xl,
                        start = spacingLg,
                        end = spacingLg,
                        bottom = spacingLg
                    )
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = EXTRA_MEDIUM,
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(spacingSm))

                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        color = primaryColor,
                        fontSize = EXTRA_REGULAR
                    )

                    Spacer(modifier = Modifier.height(spacingLg))

                    // Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(radiusMd2),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        buttons.forEach { (text, action) ->
                            Button(
                                onClick = action,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primaryColor,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(radiusMd2),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = text, fontSize = REGULAR, fontWeight = FontWeight.Bold, color = Color.White,textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }

            // Icon
            Box(
                modifier = Modifier
                    .size(widthSm)
                    .zIndex(1f)
                    .clip(CircleShape)
                    .background(primaryColor),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        }
    }
}