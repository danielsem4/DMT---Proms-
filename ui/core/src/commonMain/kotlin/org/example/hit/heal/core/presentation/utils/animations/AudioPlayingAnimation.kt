package org.example.hit.heal.core.presentation.utils.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.example.hit.heal.core.presentation.Resources.Icon.dialogSpeakerIcon
import org.example.hit.heal.core.presentation.Resources.String.dialogSpeaker
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AudioPlayingAnimation(
    isPlaying: Boolean,
    size1: Float = 50f,
    size2: Float = 90f,
    size3: Float = 130f,
    imageSize: Dp = 70.dp,
    strokeWidth: Float = 5f,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier.size(imageSize)
    ) {
        if (layoutDirection == LayoutDirection.Rtl && isPlaying) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Canvas(modifier = Modifier.wrapContentSize()) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    drawArc(
                        color = primaryColor.copy(alpha = firstCircleAlpha),
                        startAngle = 270f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(
                            centerX - (size1 / 2),
                            centerY - (size1 / 2) + adjustedOffsetY
                        ),
                        size = Size(size1, size1),
                        style = Stroke(width = strokeWidth)
                    )

                    drawArc(
                        color = primaryColor.copy(alpha = secondCircleAlpha),
                        startAngle = 270f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(
                            centerX - (size2 / 2),
                            centerY - (size2 / 2) + adjustedOffsetY
                        ),
                        size = Size(size2, size2),
                        style = Stroke(width = strokeWidth)
                    )

                    drawArc(
                        color = primaryColor.copy(alpha = thirdCircleAlpha),
                        startAngle = 270f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(
                            centerX - (size3 / 2),
                            centerY - (size3 / 2) + adjustedOffsetY
                        ),
                        size = Size(size3, size3),
                        style = Stroke(width = strokeWidth)
                    )
                }

                Image(
                    painter = painterResource(dialogSpeakerIcon),
                    contentDescription = stringResource(dialogSpeaker),
                    modifier = Modifier
                        .size(imageSize)
                        .offset(y = offsetY.dp)
                )
            }
        }
    }
}
