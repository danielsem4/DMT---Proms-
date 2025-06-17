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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import dmt_proms.hitber.generated.resources.sixth_question_hitbear_Speaker
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AudioPlayingDialog() {
    val transition = rememberInfiniteTransition()
    val offsetY by transition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val adjustedOffsetY = offsetY + 5f
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    fun fadeEffect(start: Float, peak: Float, end: Float): Float {
        return when {
            phase < start -> 0f
            phase < peak -> (phase - start) / (peak - start)
            phase < end -> 1f
            phase < end + 1 -> 1f - ((phase - end) / 1f)
            else -> 0f
        }
    }

    val firstCircleAlpha = fadeEffect(0f, 0.5f, 1f)
    val secondCircleAlpha = fadeEffect(1f, 1.5f, 2f)
    val thirdCircleAlpha = fadeEffect(2f, 2.5f, 3f)

    Dialog(onDismissRequest = {}) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.dialog_speaker),
                contentDescription = stringResource(Res.string.sixth_question_hitbear_Speaker),
                modifier = Modifier.size(70.dp).offset(y = offsetY.dp)
            )

            Canvas(modifier = Modifier) {
                val centerX = size.width / 2
                val centerY = size.height / 2

                drawArc(
                    color = primaryColor.copy(alpha = firstCircleAlpha),
                    startAngle = 270f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 25, centerY - 25 + adjustedOffsetY),
                    size = Size(50f, 50f),
                    style = Stroke(width = 5f)
                )

                drawArc(
                    color = primaryColor.copy(alpha = secondCircleAlpha),
                    startAngle = 270f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 45, centerY - 45 + adjustedOffsetY),
                    size = Size(90f, 90f),
                    style = Stroke(width = 5f)
                )

                drawArc(
                    color = primaryColor.copy(alpha = thirdCircleAlpha),
                    startAngle = 270f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(centerX - 65, centerY - 65 + adjustedOffsetY),
                    size = Size(130f, 130f),
                    style = Stroke(width = 5f)
                )
            }
        }
    }
}
