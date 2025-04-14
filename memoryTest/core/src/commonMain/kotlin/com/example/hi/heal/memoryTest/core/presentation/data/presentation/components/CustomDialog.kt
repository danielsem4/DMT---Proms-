package com.example.hi.heal.memoryTest.core.presentation.data.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
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
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.components.effects.RipplePulseEffect
import com.example.hi.heal.memoryTest.core.presentation.data.primaryColor
import dmt_proms.memorytest.core.generated.resources.Res
import dmt_proms.memorytest.core.generated.resources.call_accept
import dmt_proms.memorytest.core.generated.resources.call_remove
import org.jetbrains.compose.resources.painterResource

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
                .padding(horizontal = 32.dp)
                .wrapContentHeight()
        ) {
            // Card Body
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        top = 56.dp,
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 24.dp
                    )
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF4DD0A6),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF4DD0A6),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        buttons.forEach { (text, action) ->
                            Button(
                                onClick = action,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF4DD0A6),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = text, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            // Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4DD0A6)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        }
    }
}