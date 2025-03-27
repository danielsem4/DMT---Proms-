package org.example.hit.heal.hitber.presentation.understanding.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.dialog_speaker
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.painterResource


@Composable
fun AudioPlayingDialog() {
    val transition = rememberInfiniteTransition()

    val currentStep by transition.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Dialog(onDismissRequest = {}) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, shape = RoundedCornerShape(4.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.dialog_speaker),
                contentDescription = "Speaker",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(4.dp)) // רווח קטן בין הרמקול לעיגולים

            // ציור כל שלושת חצאי העיגולים בתוך Canvas אחד עם סדר משתנה
            Canvas(modifier = Modifier.size(80.dp)) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val step = currentStep.toInt() // עיגול ערך לשלב שלם

                // פונקציה שמחשבת שקיפות לפי השלב
                fun alphaForStep(stepIndex: Int) = if (step == stepIndex) 1f else 0f

                // עיגול ראשון (קטן)
                drawArc(
                    color = primaryColor.copy(alpha = alphaForStep(0)),
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 10, centerY - 10),
                    size = Size(30f, 30f),
                    style = Stroke(width = 3f)
                )

                // עיגול שני (בינוני)
                drawArc(
                    color = primaryColor.copy(alpha = alphaForStep(1)),
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 20, centerY - 20),
                    size = Size(60f, 60f),
                    style = Stroke(width = 3f)
                )

                // עיגול שלישי (גדול)
                drawArc(
                    color = primaryColor.copy(alpha = alphaForStep(2)),
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 30, centerY - 30),
                    size = Size(90f, 90f),
                    style = Stroke(width = 3f)
                )
            }
        }
    }
}

