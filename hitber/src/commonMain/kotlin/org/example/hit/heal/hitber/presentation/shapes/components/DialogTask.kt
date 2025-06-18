package org.example.hit.heal.hitber.presentation.shapes.components

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
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogIcon
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberUnderstand
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DialogTask(icon: DrawableResource, title: String,text: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest =  onDismiss) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(icon),
                contentDescription = stringResource(secondQuestionHitberDialogIcon),
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            )
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 25.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick =onDismiss,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),

                        ) {
                        Text(stringResource(secondQuestionHitberUnderstand), color = Color.White)
                    }
                }
            }
        }
}}